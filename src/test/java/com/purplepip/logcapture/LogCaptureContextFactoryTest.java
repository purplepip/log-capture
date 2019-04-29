package com.purplepip.logcapture;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class LogCaptureContextFactoryTest {
  @Test
  void shouldThrowException() {
    LogCaptureContextFactory factory = new LogCaptureContextFactory();
    assertThrows(LogCaptorException.class, () -> factory.create("invalid-class-name"));
  }
}
