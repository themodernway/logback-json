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

import com.themodernway.logback.json.core.IPrettyPrintCapable;
import com.themodernway.logback.json.core.LogbackJSONFormatter;
import com.themodernway.logback.json.core.LogbackObjectMapper;

import ch.qos.logback.core.LayoutBase;

public abstract class JSONLayoutBase<E> extends LayoutBase<E> implements IJSONCommon, IPrettyPrintCapable
{
    private boolean                  m_is_pretty;

    private String                   m_line_feed;

    private LogbackJSONFormatter     m_formatter;

    public final LogbackObjectMapper m_objmapper = new LogbackObjectMapper();

    protected JSONLayoutBase()
    {
        setLineFeed(NEWLINE_STRING);
    }

    @Override
    public void start()
    {
        requireNonNullOrElse(getLogbackJSONFormatter(), m_objmapper).setPretty(isPretty());

        super.start();
    }

    @Override
    public void stop()
    {
        super.stop();
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
    public void setPretty(final boolean pretty)
    {
        m_is_pretty = pretty;
    }

    @Override
    public boolean isPretty()
    {
        return m_is_pretty;
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
            return EMPTY_STRING;
        }
        final Map<String, Object> target = convertEvent(event);

        if ((null == target) || (target.isEmpty()))
        {
            return EMPTY_STRING;
        }
        try
        {
            return requireNonNullOrElse(getLogbackJSONFormatter(), m_objmapper).toJSONString(target) + requireNonNullOrElse(getLineFeed(), NEWLINE_STRING);
        }
        catch (final Exception e)
        {
            addError("error converting to JSON.", e);

            return EMPTY_STRING;
        }
    }

    public void setLineFeed(final String line)
    {
        m_line_feed = line;
    }

    public String getLineFeed()
    {
        return m_line_feed;
    }

    protected abstract Map<String, Object> convertEvent(final E event);
}
