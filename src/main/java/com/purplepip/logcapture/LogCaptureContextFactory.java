package com.purplepip.logcapture;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class LogCaptureContextFactory {
  private static final Logger LOG = LoggerFactory.getLogger(LogCaptureContextFactory.class);
  private static final Map<String, Class<LogCaptorContext>> LOG_CAPTURE_CONTEXT_CLASSES;

  static {
    LOG_CAPTURE_CONTEXT_CLASSES = new HashMap<>();

    String loggerFactoryClassName = "ch.qos.logback.classic.LoggerContext";
    String logCaptureClassName = "com.purplepip.logcapture.LogbackLogCaptureContext";
    try {
      Class<LogCaptorContext> logCaptureClazz = (Class<LogCaptorContext>) Class.forName(logCaptureClassName);
      LOG_CAPTURE_CONTEXT_CLASSES.put(loggerFactoryClassName, logCaptureClazz);
    } catch (ClassNotFoundException e) {
      LOG.warn("Cannot find class {} for logger factory {}", logCaptureClassName, loggerFactoryClassName);
    }
  }

  LogCaptorContext create() {
    String loggerFactoryClassName = LoggerFactory.getILoggerFactory().getClass().getName();
    Class<LogCaptorContext> logCaptureClazz = LOG_CAPTURE_CONTEXT_CLASSES.get(loggerFactoryClassName);
    if (logCaptureClazz == null) {
      throw new LogCaptorException(
              "No log capture context registered for " + loggerFactoryClassName);
    }
    try {
      return logCaptureClazz.newInstance();
    } catch (InstantiationException | IllegalAccessException e) {
      throw new LogCaptorException(
              "Cannot create log captor class " + logCaptureClazz + " for logger factory " + loggerFactoryClassName,
              e);
    }
  }
}
