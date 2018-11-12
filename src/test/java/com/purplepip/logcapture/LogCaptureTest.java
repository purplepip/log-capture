package com.purplepip.logcapture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class LogCaptureTest {
  private static final Logger LOG = LoggerFactory.getLogger(LogCaptureTest.class);

  @Test
  void testCaptureDefault() {
    try (LogCaptor captor = new LogCapture().start()) {
      LOG.info("testCaptureDefault: Test info message");
      assertEquals(1, captor.size());
      LOG.debug("testCaptureDefault: Test debug message");
      assertEquals(1, captor.size());
    }
  }

  @Test
  void testCaptureWithPassThrough() {
    try (LogCaptor captor = new LogCapture().withPassThrough().start()) {
      LOG.info("testCaptureDefault: Test info message");
      assertEquals(1, captor.size());
      LOG.debug("testCaptureDefault: Test debug message");
      assertEquals(1, captor.size());
    }
  }

  @Test
  void testCapture() {
    try (LogCaptor captor = new LogCapture().debug().from(LogCaptureTest.class).start()) {
      LOG.info("testCapture : Test info message : {}", "parameter-value");
      assertEquals(1, captor.size());
      assertEquals("testCapture : Test info message : parameter-value", captor.getMessage(0));
      LOG.debug("testCapture : Test debug message");
      assertEquals(2, captor.size());
      String threadName = Thread.currentThread().getName();
      assertEquals(
          "[INFO] ("
              + threadName
              + ") testCapture : Test info message : parameter-value; "
              + "[DEBUG] ("
              + threadName
              + ") testCapture : Test debug message",
          captor.toString());
    }
  }

  @Test
  void testCaptureFromStringCategory() {
    try (LogCaptor captor = new LogCapture().debug().from(LogCaptureTest.class.getName()).start()) {
      LOG.info("testCapture : Test info message");
      assertEquals(1, captor.size());
      LOG.debug("testCapture : Test debug message");
      assertEquals(2, captor.size());
    }
  }

  @Test
  void testCaptureInfo() {
    try (LogCaptor captor = new LogCapture().from(LogCaptureTest.class).start()) {
      LOG.info("testCaptureInfo : Test info message");
      assertEquals(1, captor.size());
      LOG.debug("testCaptureInfo : Test debug message");
      assertEquals(1, captor.size());
    }
  }

  @Test
  void testCaptureAllThreads() throws InterruptedException {
    String info;
    try (LogCaptor captor = new LogCapture().from(LogCaptureTest.class).fromAllThreads().start()) {
      CountDownLatch latch = new CountDownLatch(2);
      new Thread(
              () -> {
                LOG.info("testCaptureInfo : Thread 1");
                latch.countDown();
              })
          .start();
      new Thread(
              () -> {
                LOG.info("testCaptureInfo : Thread 2");
                latch.countDown();
              })
          .start();
      LOG.info("testCaptureInfo : Main Thread");
      assertTrue(latch.await(100, TimeUnit.MILLISECONDS));
      assertEquals(3, captor.size(), "Log messages not correct " + captor);
      info = captor.toString();
    }
    LOG.info(info);
  }

  @Test
  void testCaptureError() {
    try (LogCaptor captor = new LogCapture().from(LogCaptureTest.class).start()) {
      LOG.error("testCaptureError : Test info message");
      assertEquals(1, captor.size());
      assertEquals("testCaptureError : Test info message", captor.getMessage(0));
    }
  }
}
