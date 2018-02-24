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

import ch.qos.logback.classic.spi.ILoggingEvent;

public class JSONLayout extends JSONLayoutBase<ILoggingEvent>
{
    private String                                  m_dpattern                = ISO8601_PATTERNZ;

    private TimeZone                                m_timezone                = DEFAULT_TIMEZONE;

    private JSONLayoutEnhancer                      m_enhancer;

    private boolean                                 m_show_mdc                = true;

    private boolean                                 m_show_unique_id          = true;

    private boolean                                 m_show_timestamp          = true;

    private boolean                                 m_show_exception          = true;

    private boolean                                 m_show_arguments          = true;

    private boolean                                 m_show_log_level          = true;

    private boolean                                 m_show_raw_message        = true;

    private boolean                                 m_show_thread_name        = true;

    private boolean                                 m_show_logger_name        = true;

    private boolean                                 m_show_context_name       = true;

    private boolean                                 m_show_formatted_message  = true;

    private String                                  m_mdc_label               = DEFAULT_MDC_LABEL;

    private String                                  m_unique_id_label         = DEFAULT_UNIQUE_ID_LABEL;

    private String                                  m_timestamp_label         = DEFAULT_TIMESTAMP_LABEL;

    private String                                  m_exception_label         = DEFAULT_EXCEPTION_LABEL;

    public String                                   m_arguments_label         = DEFAULT_ARGUMENTS_LABEL;

    private String                                  m_log_level_label         = DEFAULT_LOG_LEVEL_LABEL;

    private String                                  m_raw_message_label       = DEFAULT_RAW_MESSAGE_LABEL;

    private String                                  m_thread_name_label       = DEFAULT_THREAD_NAME_LABEL;

    private String                                  m_logger_name_label       = DEFAULT_LOGGER_NAME_LABEL;

    private String                                  m_context_name_label      = DEFAULT_CONTEXT_NAME_LABEL;

    private String                                  m_formatted_message_label = DEFAULT_FORMATTED_MESSAGE_LABEL;

    private JSONThrowableConverter                  m_jsonthrowable_converter = new JSONListThrowableConverter();

    private final ThreadLocal<JSONDateFormatCached> m_threadlocal_date_format = ThreadLocal.withInitial(() -> new JSONDateFormatCached(getDatePattern(), getTimeZone()));

    public JSONLayout()
    {
    }

    @Override
    public void start()
    {
        m_jsonthrowable_converter.start();

        super.start();
    }

    @Override
    public void stop()
    {
        super.stop();

        m_jsonthrowable_converter.stop();
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

        append(target, toTrimOrElse(getUniqueIdLabel(), DEFAULT_UNIQUE_ID_LABEL), getShowUniqueId(), () -> uuid());

        append(target, toTrimOrElse(getLogLevelLabel(), DEFAULT_LOG_LEVEL_LABEL), getShowLogLevel(), () -> toString(event.getLevel()));

        append(target, toTrimOrElse(getThreadNameLabel(), DEFAULT_THREAD_NAME_LABEL), getShowThreadName(), () -> event.getThreadName());

        append(target, toTrimOrElse(getLoggerNameLabel(), DEFAULT_LOGGER_NAME_LABEL), getShowLoggerName(), () -> event.getLoggerName());

        append(target, toTrimOrElse(getFormattedMessageLabel(), DEFAULT_FORMATTED_MESSAGE_LABEL), getShowFormattedMessage(), () -> event.getFormattedMessage());

        append(target, toTrimOrElse(getRawMessageLabel(), DEFAULT_RAW_MESSAGE_LABEL), getShowRawMessage(), () -> event.getMessage());

        append(target, toTrimOrElse(getArgumentsLabel(), DEFAULT_ARGUMENTS_LABEL), getShowArguments(), () -> toList(event.getArgumentArray()));

        append(target, toTrimOrElse(getContextNameLabel(), DEFAULT_CONTEXT_NAME_LABEL), getShowContextName(), () -> event.getLoggerContextVO().getName());

        append(target, toTrimOrElse(getMDCLabel(), DEFAULT_MDC_LABEL), getShowMDC(), () -> event.getMDCPropertyMap());

        append(target, toTrimOrElse(getExceptionLabel(), DEFAULT_EXCEPTION_LABEL), getShowException() && (null != event.getThrowableProxy()), m_jsonthrowable_converter.supplier(event));

        if (null != enhance)
        {
            enhance.finish(target, event);
        }
        return target;
    }

    public void setRawMessageLabel(final String label)
    {
        m_raw_message_label = label;
    }

    public String getRawMessageLabel()
    {
        return m_raw_message_label;
    }

    public void setArgumentsLabel(final String label)
    {
        m_arguments_label = label;
    }

    public String getArgumentsLabel()
    {
        return m_arguments_label;
    }

    public void setExceptionLabel(final String label)
    {
        m_exception_label = label;
    }

    public String getExceptionLabel()
    {
        return m_exception_label;
    }

    public void setMDCLabel(final String label)
    {
        m_mdc_label = label;
    }

    public String getMDCLabel()
    {
        return m_mdc_label;
    }

    public void setContextNameLabel(final String label)
    {
        m_context_name_label = label;
    }

    public String getContextNameLabel()
    {
        return m_context_name_label;
    }

    public void setFormattedMessageLabel(final String label)
    {
        m_formatted_message_label = label;
    }

    public String getFormattedMessageLabel()
    {
        return m_formatted_message_label;
    }

    public void setTimeStampLabel(final String label)
    {
        m_timestamp_label = label;
    }

    public String getTimeStampLabel()
    {
        return m_timestamp_label;
    }

    public void setUniqueIdLabel(final String label)
    {
        m_unique_id_label = label;
    }

    public String getUniqueIdLabel()
    {
        return m_unique_id_label;
    }

    public void setLogLevelLabel(final String label)
    {
        m_log_level_label = label;
    }

    public String getLogLevelLabel()
    {
        return m_log_level_label;
    }

    public void setThreadNameLabel(final String label)
    {
        m_thread_name_label = label;
    }

    public String getThreadNameLabel()
    {
        return m_thread_name_label;
    }

    public void setLoggerNameLabel(final String label)
    {
        m_logger_name_label = label;
    }

    public String getLoggerNameLabel()
    {
        return m_logger_name_label;
    }

    public void setShowRawMessage(final boolean show)
    {
        m_show_raw_message = show;
    }

    public boolean getShowRawMessage()
    {
        return m_show_raw_message;
    }

    public void setShowArguments(final boolean show)
    {
        m_show_arguments = show;
    }

    public boolean getShowArguments()
    {
        return m_show_arguments;
    }

    public void setShowUniqueId(final boolean show)
    {
        m_show_unique_id = show;
    }

    public boolean getShowUniqueId()
    {
        return m_show_unique_id;
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

    public void setThrowableConverter(final JSONThrowableConverter converter)
    {
        m_jsonthrowable_converter = requireNonNullOrElse(converter, () -> new JSONListThrowableConverter());
    }

    public JSONThrowableConverter getThrowableConverter()
    {
        return m_jsonthrowable_converter;
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
