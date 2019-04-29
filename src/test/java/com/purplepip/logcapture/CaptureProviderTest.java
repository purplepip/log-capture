package com.purplepip.logcapture;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class CaptureProviderTest {
  @Test
  void shouldThrowException() {
    CaptureProvider factory = new CaptureProvider();
    assertThrows(LogCaptorException.class, () -> factory.create(Object.class));
  }
}
