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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import ch.qos.logback.core.CoreConstants;

public interface IJSONCommon
{
    public static final String   CONTENTTYPE_JSON                = "application/json";

    public static final String   NEWLINE_STRING                  = CoreConstants.LINE_SEPARATOR;

    public static final String   EMPTY_STRING                    = CoreConstants.EMPTY_STRING;

    public static final String   ISO8601_PATTERNZ                = "yyyy-MM-dd HH:mm:ss,SSS z";

    public static final TimeZone DEFAULT_TIMEZONE                = TimeZone.getTimeZone("UTC");

    public final static String   DEFAULT_MDC_LABEL               = "mdc";

    public final static String   DEFAULT_UNIQUE_ID_LABEL         = "uuid";

    public final static String   DEFAULT_LOG_LEVEL_LABEL         = "level";

    public final static String   DEFAULT_ARGUMENTS_LABEL         = "arguments";

    public final static String   DEFAULT_EXCEPTION_LABEL         = "exception";

    public final static String   DEFAULT_TIMESTAMP_LABEL         = "timestamp";

    public final static String   DEFAULT_THREAD_NAME_LABEL       = "thread";

    public final static String   DEFAULT_LOGGER_NAME_LABEL       = "logger";

    public final static String   DEFAULT_RAW_MESSAGE_LABEL       = "raw_message";

    public final static String   DEFAULT_CONTEXT_NAME_LABEL      = "context";

    public final static String   DEFAULT_FORMATTED_MESSAGE_LABEL = "message";

    default public Supplier<?> nothing()
    {
        return () -> null;
    }

    default public String uuid()
    {
        return UUID.randomUUID().toString();
    }

    default public List<?> toList(final Object[] args)
    {
        if (null == args)
        {
            return Collections.emptyList();
        }
        return Arrays.asList(args);
    }

    default public String toString(final Object valu)
    {
        return (null != valu ? valu.toString() : "null");
    }

    default public String toStringOrNull(final Object valu)
    {
        return (null != valu ? valu.toString() : null);
    }

    default public String toStringOrElse(final Object valu, final String otherwise)
    {
        return (null != valu ? valu.toString() : otherwise);
    }

    default public String toStringOrElse(final Object valu, final Supplier<String> otherwise)
    {
        return (null != valu ? valu.toString() : otherwise.get());
    }

    default public String toTrimOrNull(String string)
    {
        if ((null == string) || (string.isEmpty()))
        {
            return null;
        }
        if ((string = string.trim()).isEmpty())
        {
            return null;
        }
        return string;
    }

    default public String toTrimOrElse(final String string, final String otherwise)
    {
        return requireNonNullOrElse(toTrimOrNull(string), otherwise);
    }

    default public String toTrimOrElse(final String string, final Supplier<String> otherwise)
    {
        return requireNonNullOrElse(toTrimOrNull(string), otherwise);
    }

    default public <T> T requireNonNullOrElse(final T valu, final T otherwise)
    {
        return (null != valu ? valu : otherwise);
    }

    default public <T> T requireNonNullOrElse(final T valu, final Supplier<T> otherwise)
    {
        return (null != valu ? valu : otherwise.get());
    }

    default public void append(final Map<String, Object> target, final String name, final boolean flag, final Supplier<?> supplier)
    {
        if (flag)
        {
            final Object valu = supplier.get();

            if (null != valu)
            {
                target.put(name, valu);
            }
        }
    }

    default public void append(final Map<String, Object> target, final String name, final BooleanSupplier flag, final Supplier<?> supplier)
    {
        append(target, name, flag.getAsBoolean(), supplier);
    }
}
