# Introduction

Capture log messages, primarily for test assertions

# Usage


```
try (LogCaptor captor = new LogCapture().start()) {
  ... do something that logs
  assertEquals(1, captor.size());
}
```

# Build

    mvn clean install