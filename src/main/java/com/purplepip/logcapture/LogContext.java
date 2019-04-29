package com.purplepip.logcapture;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.event.Level;

public interface LogContext {
  Logger getLogger(String name);

  void removeAllAppenders();

  void restoreAppenders();

  void detachCapturingAppender(String name);

  void capture(String category, Level level, boolean allThreads);

  int size();

  String getMessage(int index);

  boolean hasMessages();
}
