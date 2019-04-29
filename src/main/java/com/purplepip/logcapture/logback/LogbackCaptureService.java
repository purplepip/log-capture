package com.purplepip.logcapture.logback;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import com.purplepip.logcapture.CaptureService;
import com.purplepip.logcapture.Level;
import com.purplepip.logcapture.LogCaptorEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class LogbackCaptureService implements CaptureService {
  private ch.qos.logback.classic.Level originalLevel;
  private LogbackListAppender capturingAppender;

  private LoggerContext logManager;
  private final Map<String, Appender<ILoggingEvent>> removedAppenders = new HashMap<>();

  @Override
  public void setLogManager(Object logManager) {
    this.logManager = (LoggerContext) logManager;
  }

  @Override
  public boolean supports(Object logManager) {
    return LoggerContext.class.isAssignableFrom(logManager.getClass());
  }

  public Logger getLogger(String name) {
    return logManager.getLogger(name);
  }

  public void removeAllAppenders() {
    /*
     * Remove all appenders.
     */
    for (Logger logger : logManager.getLoggerList()) {
      List<Appender<ILoggingEvent>> appenders = new ArrayList<>();
      Iterator<Appender<ILoggingEvent>> iterator = logger.iteratorForAppenders();
      while (iterator.hasNext()) {
        appenders.add(iterator.next());
      }
      for (Appender<ILoggingEvent> appender : appenders) {
        logger.detachAppender(appender);
        removedAppenders.put(logger.getName(), appender);
      }
    }
  }

  private void setLevel(String name, Level level) {
    capturingAppender.setContext(logManager);
    Logger logger = logManager.getLogger(name);
    logger.addAppender(capturingAppender);
    originalLevel = getLogger(name).getLevel();
    logger.setLevel(ch.qos.logback.classic.Level.toLevel(level.toString()));
  }

  /*
   * Restore previous appenders.
   */
  @Override
  public void restoreAppenders() {
    for (Map.Entry<String, Appender<ILoggingEvent>> entry : removedAppenders.entrySet()) {
      Logger logger = logManager.getLogger(entry.getKey());
      logger.addAppender(entry.getValue());
    }
  }

  /*
   * Remove capturing appender.
   */
  @Override
  public void detachCapturingAppender(String name) {
    Logger logger = logManager.getLogger(name);
    capturingAppender.stop();
    logger.detachAppender(capturingAppender);
    logger.setLevel(originalLevel);
  }

  @Override
  public void capture(
      List<LogCaptorEvent> events, String category, Level level, boolean allThreads) {
    /*
     * Set up the capturing appender.
     */
    capturingAppender =
        allThreads
            ? new LogbackListAppender(events)
            : new SpecificThreadListAppender(events, Thread.currentThread().getName());
    setLevel(category, level);
    capturingAppender.start();
  }
}
