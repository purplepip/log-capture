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

/** Log capture tool. */
public class LogCaptor implements AutoCloseable {
  private LogCaptureConfiguration configuration;
  private final LogCaptorContext context = new LogCaptureContextFactory().create();

  LogCaptor(LogCaptureConfiguration configuration) {
    this.configuration = configuration;
    start();
  }

  private void start() {
    if (!configuration.getPassThrough()) {
      context.removeAllAppenders();
    }
    context.capture(configuration.getCategory(), configuration.getLevel(), configuration.isAllThreads());
  }

  @Override
  public void close() {
    context.detachCapturingAppender(configuration.getCategory());

    if (!configuration.getPassThrough()) {
      context.restoreAppenders();
    }
  }

  public int size() {
    return context.size();
  }

  public String getMessage(int index) {
    return context.getMessage(index);
  }

  public boolean hasMessages() {
    return context.hasMessages();
  }

  @Override
  public String toString() {
    return context.toString();
  }

}
