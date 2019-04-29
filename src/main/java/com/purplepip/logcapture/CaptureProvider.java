package com.purplepip.logcapture;

import java.util.ServiceLoader;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class CaptureProvider {
  private static final Logger LOG = LoggerFactory.getLogger(CaptureProvider.class);

  CaptureService create() {
    return create(LoggerFactory.getILoggerFactory().getClass());
  }

  CaptureService create(Class<?> loggerFactory) {
    ServiceLoader<CaptureService> loader = ServiceLoader.load(CaptureService.class);
    return StreamSupport.stream(loader.spliterator(), false)
        .filter(service -> service.supports(loggerFactory))
        .findFirst()
        .orElseThrow(() -> new LogCaptorException("Cannot find service for " + loggerFactory));
  }
}
