package com.purplepip.logcapture.mocklog;

import static org.junit.jupiter.api.Assertions.assertFalse;

import com.purplepip.logcapture.LogCaptor;
import com.purplepip.logcapture.LogCapture;
import org.junit.jupiter.api.Test;

class LogCaptureMockLogTest {
  @Test
  void testCaptureDefault() {
    try (LogCaptor captor = new LogCapture().withLogManager(new MockLogManager()).start()) {
      assertFalse(captor.hasMessages());
    }
  }
}
