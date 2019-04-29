package com.purplepip.logcapture.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.purplepip.logcapture.LogCaptorEvent;
import java.util.List;

public class LogbackListAppender extends AppenderBase<ILoggingEvent> {
  private List<LogCaptorEvent> events;

  LogbackListAppender(List<LogCaptorEvent> events) {
    this.events = events;
  }

  @Override
  protected void append(ILoggingEvent e) {
    events.add(
        new LogCaptorEvent(e.getFormattedMessage(), e.getLevel().toString(), e.getThreadName()));
  }
}
