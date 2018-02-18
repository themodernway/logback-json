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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimeZone;

import ch.qos.logback.classic.spi.ILoggingEvent;

public class JSONLayout extends JSONLayoutBase<ILoggingEvent>
{
    private String                        m_pattern  = "yyyy-MM-dd HH:mm:ss,SSS z";

    private TimeZone                      m_timezone = TimeZone.getTimeZone("GMT");

    private final ThreadLocal<DateFormat> m_format   = ThreadLocal.withInitial(() -> new JSONDateFormat(getDateFormat(), getTimeZone()));

    public JSONLayout()
    {
    }

    @Override
    protected Map<String, Object> convertEvent(final ILoggingEvent event)
    {
        final Map<String, Object> target = new LinkedHashMap<String, Object>();

        add(target, "time", true, () -> m_format.get().format(new Date(event.getTimeStamp())));

        add(target, "level", true, () -> String.valueOf(event.getLevel()));

        add(target, "logger", true, () -> event.getLoggerName());

        add(target, "message", true, () -> event.getFormattedMessage());

        add(target, "state", true, map(() -> event.getMDCPropertyMap()));

        return target;
    }

    public void setDateFormat(final String format)
    {
        m_pattern = format;
    }

    public String getDateFormat()
    {
        return m_pattern;
    }

    public void setTimeZone(final String tz)
    {
        m_timezone = TimeZone.getTimeZone(tz);
    }

    public TimeZone getTimeZone()
    {
        return m_timezone;
    }

    protected static class JSONDateFormat extends SimpleDateFormat
    {
        private static final long serialVersionUID = 1L;

        public JSONDateFormat(final String pattern, final TimeZone zone)
        {
            super(pattern);

            setTimeZone(zone);
        }
    }
}
