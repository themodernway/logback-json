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

import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A {@code IJSONCommon} interface that has common methods and constants.
 *
 * @author Dean S. Jones
 * @since 2.0.6-SNAPSHOT
 */

public interface IJSONCommon
{
    public static final String EMPTY_STRING                    = "";

    public static final String JSON_INDENT_VALUE               = "  ";

    public static final String ZONE_STRING_VALUE               = "UTC";

    public static final String NULL_STRING_VALUE               = "null";

    public static final String CONTENT_TYPE_JSON               = "application/json";

    public static final String ISO8601_PATTERNZ                = "yyyy-MM-dd HH:mm:ss,SSS z";

    public static final String DEFAULT_MDC_LABEL               = "mdc";

    public static final String DEFAULT_UNIQUE_ID_LABEL         = "uuid";

    public static final String DEFAULT_LOG_LEVEL_LABEL         = "level";

    public static final String DEFAULT_ARGUMENTS_LABEL         = "arguments";

    public static final String DEFAULT_EXCEPTION_LABEL         = "exception";

    public static final String DEFAULT_TIMESTAMP_LABEL         = "timestamp";

    public static final String DEFAULT_THREAD_NAME_LABEL       = "thread";

    public static final String DEFAULT_LOGGER_NAME_LABEL       = "logger";

    public static final String DEFAULT_RAW_MESSAGE_LABEL       = "raw_message";

    public static final String DEFAULT_CONTEXT_NAME_LABEL      = "context";

    public static final String DEFAULT_FORMATTED_MESSAGE_LABEL = "message";

    public static final String LINE_SEPARATOR_STRING           = System.lineSeparator();

    public static final ZoneId DEFAULT_TIMEZONE_VALUE          = ZoneId.of(ZONE_STRING_VALUE);

    default String uuid()
    {
        return UUID.randomUUID().toString();
    }

    default <T, R> R nullOrOtherwise(final T valu, final Function<T, R> otherwise)
    {
        return ((null == valu) ? null : otherwise.apply(valu));
    }

    default List<Object> asList(final boolean empty, final Object[] args)
    {
        return toList(empty, args);
    }

    @SuppressWarnings("unchecked")
    default <T> List<T> toList(final T... args)
    {
        return ((null == args) ? Collections.emptyList() : Arrays.asList(args));
    }

    @SuppressWarnings("unchecked")
    default <T> List<T> toList(final boolean empty, final T... args)
    {
        final List<T> list = toList(args);

        return (((false == empty) && (list.isEmpty())) ? null : list);
    }

    default <T> List<T> toListOrNull(final List<T> list)
    {
        return (((null == list) || (list.isEmpty())) ? null : list);
    }

    default String toString(final Object valu)
    {
        return ((null != valu) ? valu.toString() : NULL_STRING_VALUE);
    }

    default String toStringOrNull(final Object valu)
    {
        return ((null != valu) ? valu.toString() : null);
    }

    default String toStringOrElse(final Object valu, final String otherwise)
    {
        return ((null != valu) ? valu.toString() : otherwise);
    }

    default String toStringOrElse(final Object valu, final Supplier<String> otherwise)
    {
        return ((null != valu) ? valu.toString() : otherwise.get());
    }

    default String toTrimOrNull(String string)
    {
        if ((null == string) || (string.isEmpty()))
        {
            return null;
        }
        string = string.trim();

        if (string.isEmpty())
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
        return ((null != valu) ? valu : otherwise);
    }

    default <T> T requireNonNullOrElse(final T valu, final Supplier<T> otherwise)
    {
        return ((null != valu) ? valu : otherwise.get());
    }

    default void append(final Map<String, Object> target, final String name, final boolean flag, final Supplier<Object> supplier)
    {
        if (flag)
        {
            final String labl = toTrimOrNull(name);

            if (null != labl)
            {
                final Object valu = supplier.get();

                if (null != valu)
                {
                    target.put(labl, valu);
                }
            }
        }
    }
}
