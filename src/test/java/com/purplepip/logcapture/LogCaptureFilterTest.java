/*
 * Copyright (c) 2017 the original author or authors. All Rights Reserved
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.purplepip.logcapture;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LogCaptureFilterTest {
  @ParameterizedTest
  @MethodSource("createCaptors")
  void withCaptors(LogCapture capture, int expected) {
    try (LogCaptor captor = capture.start()) {
      new Foo().all();
      new Bar().all();
      assertEquals(expected, captor.size());
    }
  }

  private static Stream<Arguments> createCaptors() {
    return Stream.of(
        Arguments.of(new LogCapture().trace(), 10),
        Arguments.of(new LogCapture().debug(), 8),
        Arguments.of(new LogCapture().info(), 6),
        Arguments.of(new LogCapture().warn(), 4),
        Arguments.of(new LogCapture().error(), 2),
        Arguments.of(new LogCapture().error().from(Bar.class), 1),
        Arguments.of(new LogCapture().error().from("com.purplepip.logcapture"), 2),
        Arguments.of(new LogCapture().error().from("com.purplepip.logcapture.Bar"), 1),
        Arguments.of(new LogCapture().trace().from(Bar.class), 5));
  }
}
