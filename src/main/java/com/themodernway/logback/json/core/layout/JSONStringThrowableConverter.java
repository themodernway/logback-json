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

package com.themodernway.logback.json.core.layout;

import java.util.function.Supplier;

import com.themodernway.logback.json.core.IJSONCommon;
import com.themodernway.logback.json.core.IJSONThrowableConverter;

import ch.qos.logback.classic.pattern.RootCauseFirstThrowableProxyConverter;
import ch.qos.logback.classic.pattern.ThrowableHandlingConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class JSONStringThrowableConverter implements IJSONThrowableConverter, IJSONCommon
{
    private final ThrowableHandlingConverter m_proxy_handler_converter = new RootCauseFirstThrowableProxyConverter();

    public JSONStringThrowableConverter()
    {
    }

    @Override
    public Supplier<?> supplier(final ILoggingEvent event)
    {
        return () -> toTrimOrNull(m_proxy_handler_converter.convert(event));
    }

    @Override
    public void start()
    {
        m_proxy_handler_converter.start();
    }

    @Override
    public void stop()
    {
        m_proxy_handler_converter.stop();
    }

    @Override
    public boolean isStarted()
    {
        return m_proxy_handler_converter.isStarted();
    }
}
