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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Foo {
  private Logger log;

  Foo() {
    initialiseLogger();
  }

  protected void initialiseLogger() {
    initialiseLogger(LoggerFactory.getLogger(Foo.class));
  }

  protected void initialiseLogger(Logger log) {
    this.log = log;
  }

  Foo trace() {
    log.trace("trace message");
    return this;
  }

  Foo debug() {
    log.debug("debug message");
    return this;
  }

  Foo info() {
    log.info("Info message");
    return this;
  }

  Foo warn() {
    log.warn("warn message");
    return this;
  }

  Foo error() {
    log.error("error message");
    return this;
  }

  Foo all() {
    error().warn().info().debug().trace();
    return this;
  }
}
