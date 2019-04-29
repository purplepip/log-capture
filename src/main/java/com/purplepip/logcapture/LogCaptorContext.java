package com.purplepip.logcapture;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.event.Level;

public interface LogCaptorContext {
  Logger getLogger(String name);

  void removeAllAppenders();

  void restoreAppenders();

  void detachCapturingAppender(String name);

  void capture(List<LogCaptorEvent> events, String category, Level level, boolean allThreads);
}
