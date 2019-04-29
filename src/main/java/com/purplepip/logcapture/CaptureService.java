package com.purplepip.logcapture;

import java.util.List;
import org.slf4j.Logger;

public interface CaptureService {
  boolean supports(Class<?> clazz);

  Logger getLogger(String name);

  void removeAllAppenders();

  void restoreAppenders();

  void detachCapturingAppender(String name);

  void capture(List<LogCaptorEvent> events, String category, Level level, boolean allThreads);
}
