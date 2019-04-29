package com.purplepip.logcapture;

import java.util.ServiceLoader;
import java.util.stream.StreamSupport;
import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.SubstituteLoggerFactory;

class CaptureProvider {
  private static final int LOGGER_FACTORY_MAX_WAIT = 500;

  CaptureService create() {
    return create(findActiveLoggerFactory());
  }

  CaptureService create(Object logManager) {
    ServiceLoader<CaptureService> loader = ServiceLoader.load(CaptureService.class);
    CaptureService service =
        StreamSupport.stream(loader.spliterator(), false)
            .filter(s -> s.supports(logManager))
            .findFirst()
            .orElseThrow(() -> new LogCaptorException("Cannot find service for " + logManager));
    service.setLogManager(logManager);
    return service;
  }

  private ILoggerFactory findActiveLoggerFactory() {
    ILoggerFactory activeLoggerFactory = LoggerFactory.getILoggerFactory();
    if (activeLoggerFactory instanceof SubstituteLoggerFactory) {
      /*
       * SL4J is in the process of spinning up, wait a little wile
       */
      long start = System.currentTimeMillis();
      try {
        while (System.currentTimeMillis() - start < LOGGER_FACTORY_MAX_WAIT) {
          activeLoggerFactory = LoggerFactory.getILoggerFactory();
          Thread.sleep(10);
          if (!(activeLoggerFactory instanceof SubstituteLoggerFactory)) {
            break;
          }
        }
      } catch (InterruptedException e) {
        throw new LogCaptorException("Interruption whilst waiting for active logger factory");
      }
      if (activeLoggerFactory instanceof SubstituteLoggerFactory) {
        throw new LogCaptorException(
            "Logger factory still initialising after " + LOGGER_FACTORY_MAX_WAIT);
      }
    }
    return activeLoggerFactory;
  }
}
