package com.purplepip.logcapture;

class LogCaptorException extends RuntimeException {
  LogCaptorException(String message, Throwable t) {
    super(message, t);
  }

  LogCaptorException(String message) {
    super(message);
  }
}
