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

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

import ch.qos.logback.classic.pattern.RootCauseFirstThrowableProxyConverter;
import ch.qos.logback.classic.pattern.ThrowableHandlingConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class JSONLayout extends JSONLayoutBase<ILoggingEvent>
{
    private String                                  m_dpattern                = ISO8601_PATTERNZ;

    private TimeZone                                m_timezone                = DEFAULT_TIMEZONE;

    private JSONLayoutEnhancer                      m_enhancer;

    private boolean                                 m_show_mdc                = true;

    private boolean                                 m_show_uuid               = true;

    private boolean                                 m_show_timestamp          = true;

    private boolean                                 m_show_exception          = true;

    private boolean                                 m_show_log_level          = true;

    private boolean                                 m_show_thread_name        = true;

    private boolean                                 m_show_logger_name        = true;

    private boolean                                 m_show_context_name       = true;

    private boolean                                 m_show_formatted_message  = true;

    private String                                  m_timestamp_label         = DEFAULT_TIMESTAMP_LABEL;

    private final ThrowableHandlingConverter        m_proxy_handle_converter  = new RootCauseFirstThrowableProxyConverter();

    private final ThreadLocal<JSONDateFormatCached> m_threadlocal_date_format = ThreadLocal.withInitial(() -> new JSONDateFormatCached(getDatePattern(), getTimeZone()));

    public JSONLayout()
    {
    }

    @Override
    public void start()
    {
        m_proxy_handle_converter.start();

        super.start();
    }

    @Override
    public void stop()
    {
        super.stop();

        m_proxy_handle_converter.stop();
    }

    @Override
    protected Map<String, Object> convertEvent(final ILoggingEvent event)
    {
        final JSONLayoutEnhancer enhance = getEnhancer();

        final Map<String, Object> target = new LinkedHashMap<String, Object>();

        if (null != enhance)
        {
            enhance.before(target, event);
        }
        append(target, toTrimOrElse(getTimeStampLabel(), DEFAULT_TIMESTAMP_LABEL), getShowTimeStamp(), () -> m_threadlocal_date_format.get().format(event.getTimeStamp()));

        append(target, "uuid", getShowUUID(), () -> UUID.randomUUID().toString());

        append(target, "level", getShowLogLevel(), () -> String.valueOf(event.getLevel()));

        append(target, "thread", getShowThreadName(), () -> event.getThreadName());

        append(target, "logger", getShowLoggerName(), () -> event.getLoggerName());

        append(target, "message", getShowFormattedMessage(), () -> event.getFormattedMessage());

        append(target, "context", getShowContextName(), () -> event.getLoggerContextVO().getName());

        append(target, "mdc", getShowMDC(), () -> event.getMDCPropertyMap());

        append(target, "exception", getShowException() && (null != event.getThrowableProxy()), () -> toTrimOrNull(m_proxy_handle_converter.convert(event)));

        if (null != enhance)
        {
            enhance.finish(target, event);
        }
        return target;
    }

    public String setTimeStampLabel(final String label)
    {
        return m_timestamp_label = label;
    }

    public String getTimeStampLabel()
    {
        return m_timestamp_label;
    }

    public void setShowUUID(final boolean show)
    {
        m_show_uuid = show;
    }

    public boolean getShowUUID()
    {
        return m_show_uuid;
    }

    public void setShowException(final boolean show)
    {
        m_show_exception = show;
    }

    public boolean getShowException()
    {
        return m_show_exception;
    }

    public void setShowFormattedMessage(final boolean show)
    {
        m_show_formatted_message = show;
    }

    public boolean getShowFormattedMessage()
    {
        return m_show_formatted_message;
    }

    public void setShowMDC(final boolean show)
    {
        m_show_mdc = show;
    }

    public boolean getShowMDC()
    {
        return m_show_mdc;
    }

    public void setShowTimeStamp(final boolean show)
    {
        m_show_timestamp = show;
    }

    public boolean getShowTimeStamp()
    {
        return m_show_timestamp;
    }

    public void setShowLogLevel(final boolean show)
    {
        m_show_log_level = show;
    }

    public boolean getShowLogLevel()
    {
        return m_show_log_level;
    }

    public void setShowLoggerName(final boolean show)
    {
        m_show_logger_name = show;
    }

    public boolean getShowLoggerName()
    {
        return m_show_logger_name;
    }

    public void setShowContextName(final boolean show)
    {
        m_show_context_name = show;
    }

    public boolean getShowContextName()
    {
        return m_show_context_name;
    }

    public void setShowThreadName(final boolean show)
    {
        m_show_thread_name = show;
    }

    public boolean getShowThreadName()
    {
        return m_show_thread_name;
    }

    public void setDatePattern(final String dpattern)
    {
        m_dpattern = dpattern;
    }

    public String getDatePattern()
    {
        return requireNonNullOrElse(m_dpattern, ISO8601_PATTERNZ);
    }

    public void setTimeZone(final String tz)
    {
        m_timezone = TimeZone.getTimeZone(tz);
    }

    public TimeZone getTimeZone()
    {
        return requireNonNullOrElse(m_timezone, DEFAULT_TIMEZONE);
    }

    public void setEnhancer(final JSONLayoutEnhancer enhancer)
    {
        m_enhancer = enhancer;
    }

    public JSONLayoutEnhancer getEnhancer()
    {
        return m_enhancer;
    }
}
