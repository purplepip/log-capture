# Introduction

Capture logback log messages, primarily for test assertions.

# Usage

```
try (LogCaptor captor = new LogCapture().start()) {
  ... do something that logs
  assertEquals(1, captor.size());
}
```

See LogCaptureTest and other unit tests for more usage examples.

# Build

    mvn clean install

# Support

If you want support for other log frameworks, just ask and support will be added.