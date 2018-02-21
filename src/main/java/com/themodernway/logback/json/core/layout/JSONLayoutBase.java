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

import com.themodernway.logback.json.core.LogbackJSONFormatter;
import com.themodernway.logback.json.core.LogbackObjectMapper;

import ch.qos.logback.core.LayoutBase;

public abstract class JSONLayoutBase<E> extends LayoutBase<E> implements IJSONCommon
{
    private LogbackJSONFormatter            m_formatter;

    public static final LogbackObjectMapper JSON_MAPPER = new LogbackObjectMapper();

    protected JSONLayoutBase()
    {
    }

    public LogbackJSONFormatter getLogbackJSONFormatter()
    {
        return m_formatter;
    }

    public void setLogbackJSONFormatter(final LogbackJSONFormatter formatter)
    {
        m_formatter = formatter;
    }

    @Override
    public String getContentType()
    {
        return CONTENTTYPE_JSON;
    }

    @Override
    public String doLayout(final E event)
    {
        if (false == isStarted())
        {
            return getLayoutLineSeparator();
        }
        final Map<String, Object> target = convertEvent(event);

        if ((null == target) || (target.isEmpty()))
        {
            return null;
        }
        try
        {
            return requireNonNullOrElse(getLogbackJSONFormatter(), JSON_MAPPER).toJSONString(target) + getLayoutLineSeparator();
        }
        catch (final Exception e)
        {
            addError("error converting to JSON.", e);

            return null;
        }
    }

    public String getLayoutLineSeparator()
    {
        return LINE_FEED_STRING;
    }

    protected abstract Map<String, Object> convertEvent(final E event);
}
