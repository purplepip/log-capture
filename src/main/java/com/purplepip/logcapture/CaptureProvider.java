package com.purplepip.logcapture;

import java.util.ServiceLoader;
import java.util.stream.StreamSupport;
import org.slf4j.LoggerFactory;

class CaptureProvider {
  CaptureService create() {
    return create(LoggerFactory.getILoggerFactory());
  }

  CaptureService create(Object logManager) {
    ServiceLoader<CaptureService> loader = ServiceLoader.load(CaptureService.class);
    return StreamSupport.stream(loader.spliterator(), false)
        .filter(service -> service.supports(logManager))
        .findFirst()
        .orElseThrow(() -> new LogCaptorException("Cannot find service for " + logManager));
  }
}
