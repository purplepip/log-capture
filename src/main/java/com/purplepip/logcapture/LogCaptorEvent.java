package com.purplepip.logcapture;

public class LogCaptorEvent {
  private String level;
  private String message;
  private String threadName;

  public LogCaptorEvent(String message, String level, String threadName) {
    this.level = level;
    this.message = message;
    this.threadName = threadName;
  }

  public String getLevel() {
    return level;
  }

  public String getThreadName() {
    return threadName;
  }

  public String getMessage() {
    return message;
  }
}
