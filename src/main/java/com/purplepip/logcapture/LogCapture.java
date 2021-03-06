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

/** Capture configuration and captor builder. */
public class LogCapture {
  private LogCaptureConfiguration configuration = new LogCaptureConfiguration();

  public LogCapture trace() {
    configuration.setLevel(Level.TRACE);
    return this;
  }

  public LogCapture debug() {
    configuration.setLevel(Level.DEBUG);
    return this;
  }

  public LogCapture info() {
    configuration.setLevel(Level.INFO);
    return this;
  }

  public LogCapture warn() {
    configuration.setLevel(Level.WARN);
    return this;
  }

  public LogCapture error() {
    configuration.setLevel(Level.ERROR);
    return this;
  }

  public LogCapture from(String category) {
    configuration.setCategory(category);
    return this;
  }

  public LogCapture from(Class clazz) {
    configuration.setCategory(clazz.getName());
    return this;
  }

  public LogCapture withPassThrough() {
    configuration.setPassThrough(true);
    return this;
  }

  public LogCapture fromAllThreads() {
    configuration.setAllThreads(true);
    return this;
  }

  public LogCapture withLogManager(Object logManager) {
    configuration.setService(new CaptureProvider().create(logManager));
    return this;
  }

  public LogCaptor start() {
    return new LogCaptor(configuration);
  }
}
