package com.purplepip.logcapture.mocklog;

import com.purplepip.logcapture.CaptureService;
import com.purplepip.logcapture.Level;
import com.purplepip.logcapture.LogCaptorEvent;
import java.util.List;
import org.slf4j.Logger;

public class MockLogCaptureService implements CaptureService {
  @Override
  public boolean supports(Object logManager) {
    return MockLogManager.class.isAssignableFrom(logManager.getClass());
  }

  @Override
  public Logger getLogger(String name) {
    return null;
  }

  @Override
  public void removeAllAppenders() {}

  @Override
  public void restoreAppenders() {}

  @Override
  public void detachCapturingAppender(String name) {}

  @Override
  public void capture(
      List<LogCaptorEvent> events, String category, Level level, boolean allThreads) {}
}
