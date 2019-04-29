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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/** Log capture tool. */
public class LogCaptor implements AutoCloseable {
  private LogCaptureConfiguration configuration;
  private final LogCaptorContext context = new LogCaptureContextFactory().create();
  private List<LogCaptorEvent> events;

  LogCaptor(LogCaptureConfiguration configuration) {
    this.configuration = configuration;
    start();
  }

  private void start() {
    events = new ArrayList<>();
    if (!configuration.getPassThrough()) {
      context.removeAllAppenders();
    }
    context.capture(events, configuration.getCategory(), configuration.getLevel(), configuration.isAllThreads());
  }

  @Override
  public void close() {
    context.detachCapturingAppender(configuration.getCategory());

    if (!configuration.getPassThrough()) {
      context.restoreAppenders();
    }
  }

  public int size() {
    return events.size();
  }

  public String getMessage(int index) {
    return events.get(index).getMessage();
  }

  public boolean hasMessages() {
    return !events.isEmpty();
  }

  /**
   * To string.
   *
   * @return string
   */
  @Override
  public String toString() {
    return events
            .stream()
            .map(
                    e ->
                            "["
                                    + e.getLevel()
                                    + "] "
                                    + "("
                                    + e.getThreadName()
                                    + ") "
                                    + e.getMessage())
            .collect(Collectors.joining("; "));
  }
}
