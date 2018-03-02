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

import java.util.Map;
import java.util.TimeZone;

import com.themodernway.logback.json.core.IJSONCommon;
import com.themodernway.logback.json.core.IJSONFormatter;
import com.themodernway.logback.json.core.IJSONPrettyPrinter;
import com.themodernway.logback.json.core.IJSONThrowableConverter;
import com.themodernway.logback.json.core.JSONDateFormatCached;

import ch.qos.logback.core.spi.LifeCycle;

public interface IJSONLayout<E> extends IJSONPrettyPrinter, IJSONCommon, LifeCycle
{
    public String getRawMessageLabel();

    public String getArgumentsLabel();

    public String getExceptionLabel();

    public String getMDCLabel();

    public String getContextNameLabel();

    public String getFormattedMessageLabel();

    public String getTimeStampLabel();

    public String getUniqueIdLabel();

    public String getLogLevelLabel();

    public String getThreadNameLabel();

    public String getLoggerNameLabel();

    public String getLineSeparator();

    public boolean getShowRawMessage();

    public boolean getShowArguments();

    public boolean getShowUniqueId();

    public boolean getShowException();

    public boolean getShowFormattedMessage();

    public boolean getShowMDC();

    public boolean getShowTimeStamp();

    public boolean getShowLogLevel();

    public boolean getShowLoggerName();

    public boolean getShowContextName();

    boolean getShowThreadName();

    public String getDatePattern();

    public TimeZone getTimeZone();

    public IJSONFormatter getJSONFormatter();

    public JSONLayoutEnhancer getJSONLayoutEnhancer();

    public IJSONThrowableConverter getJSONThrowableConverter();

    public JSONDateFormatCached getJSONDateFormatCached();

    public Map<String, Object> convertEvent(E event);
}