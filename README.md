# Introduction

Capture logback log messages for test scripts, including : 
 
* Swallow expected error messages from test executed so they don't pollute build logs
* Assert correct log messages are fired 

# Usage

Swallow expected error messages that are polluting build logs

```js
try (LogCaptor captor = new LogCapture().error().start()) {
  ... do something that generates expected errors
}
```

Capture info message and assert as expected

```js
try (LogCaptor captor = new LogCapture().info().start()) {
  ... do something that expects an info log message
  assertEquals(1, captor.size());
  assertEquals("expected message", captor.getMessage(0));
}
```

See LogCaptureTest and other unit tests for more usage examples.

# Build

    ./gradlew build