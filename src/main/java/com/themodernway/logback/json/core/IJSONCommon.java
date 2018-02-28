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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import java.util.function.Supplier;

import ch.qos.logback.core.CoreConstants;

public interface IJSONCommon
{
    public static final String   JSON_INDENT_VALUE               = "  ";

    public static final String   CONTENT_TYPE_JSON               = "application/json";

    public static final String   NEWLINE_STRING                  = CoreConstants.LINE_SEPARATOR;

    public static final String   EMPTY_STRING                    = CoreConstants.EMPTY_STRING;

    public static final String   ISO8601_PATTERNZ                = "yyyy-MM-dd HH:mm:ss,SSS z";

    public static final TimeZone DEFAULT_TIMEZONE                = TimeZone.getTimeZone("UTC");

    public static final String   DEFAULT_MDC_LABEL               = "mdc";

    public static final String   DEFAULT_UNIQUE_ID_LABEL         = "uuid";

    public static final String   DEFAULT_LOG_LEVEL_LABEL         = "level";

    public static final String   DEFAULT_ARGUMENTS_LABEL         = "arguments";

    public static final String   DEFAULT_EXCEPTION_LABEL         = "exception";

    public static final String   DEFAULT_TIMESTAMP_LABEL         = "timestamp";

    public static final String   DEFAULT_THREAD_NAME_LABEL       = "thread";

    public static final String   DEFAULT_LOGGER_NAME_LABEL       = "logger";

    public static final String   DEFAULT_RAW_MESSAGE_LABEL       = "raw_message";

    public static final String   DEFAULT_CONTEXT_NAME_LABEL      = "context";

    public static final String   DEFAULT_FORMATTED_MESSAGE_LABEL = "message";

    default long getTimeNow()
    {
        return System.currentTimeMillis();
    }

    default String uuid()
    {
        return UUID.randomUUID().toString();
    }

    default List<Object> asList(final boolean empty, final Object[] args)
    {
        return toList(empty, args);
    }

    @SuppressWarnings("unchecked")
    default <T> List<T> toList(final T... args)
    {
        return (null == args) ? Collections.emptyList() : Arrays.asList(args);
    }

    @SuppressWarnings("unchecked")
    default <T> List<T> toList(final boolean empty, final T... args)
    {
        final List<T> list = toList(args);

        if ((false == empty) && (list.isEmpty()))
        {
            return null;
        }
        return list;
    }

    default <T> List<T> toListOrNull(final List<T> list)
    {
        return ((null == list) || (list.isEmpty())) ? null : list;
    }

    default String toString(final Object valu)
    {
        return (null != valu ? valu.toString() : "null");
    }

    default String toStringOrNull(final Object valu)
    {
        return (null != valu ? valu.toString() : null);
    }

    default String toStringOrElse(final Object valu, final String otherwise)
    {
        return (null != valu ? valu.toString() : otherwise);
    }

    default String toStringOrElse(final Object valu, final Supplier<String> otherwise)
    {
        return (null != valu ? valu.toString() : otherwise.get());
    }

    default String toTrimOrNull(String string)
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

    default String toTrimOrElse(final String string, final String otherwise)
    {
        return requireNonNullOrElse(toTrimOrNull(string), otherwise);
    }

    default String toTrimOrElse(final String string, final Supplier<String> otherwise)
    {
        return requireNonNullOrElse(toTrimOrNull(string), otherwise);
    }

    default <T> T requireNonNullOrElse(final T valu, final T otherwise)
    {
        return (null != valu ? valu : otherwise);
    }

    default <T> T requireNonNullOrElse(final T valu, final Supplier<T> otherwise)
    {
        return (null != valu ? valu : otherwise.get());
    }

    default void append(final Map<String, Object> target, final Supplier<String> label, final boolean flag, final Supplier<Object> supplier)
    {
        if (flag)
        {
            final String name = toTrimOrNull(label.get());

            if (null != name)
            {
                final Object valu = supplier.get();

                if (null != valu)
                {
                    target.put(name, valu);
                }
            }
        }
    }
}
