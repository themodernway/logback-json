/*
 * Copyright (c) 2018, The Modern Way. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.themodernway.logback.json.core;

import java.util.function.Supplier;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.spi.LifeCycle;

/**
 * A {@code IJSONThrowableConverter} interface, which is a definition of classes that convert an {@link ILoggingEvent} to a {@link Supplier}.
 *
 * @author Dean S. Jones
 * @since 2.0.0-RELEASE
 */

public interface IJSONThrowableConverter extends LifeCycle
{
    /**
     * Converts the specified event into a Supplier.
     *
     * @param event the {@link ILoggingEvent} to be converted to a {@link Supplier}.
     * @return a {@link Supplier} from the {@link ILoggingEvent}.
     */

    public Supplier<Object> supplier(ILoggingEvent event);
}
