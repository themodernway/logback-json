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

import com.themodernway.logback.json.core.IJSONFormatter;
import com.themodernway.logback.json.core.JSONFormattingException;

import ch.qos.logback.core.LayoutBase;

public abstract class JSONLayoutBase<E> extends LayoutBase<E> implements IJSONLayout<E>
{
    private boolean        m_is_pretty;

    private String         m_line_feed;

    private IJSONFormatter m_formatter;

    protected JSONLayoutBase()
    {
        setLineFeed(NEWLINE_STRING);
    }

    @Override
    public void start()
    {
        if (null != getJSONFormatter())
        {
            getJSONFormatter().setPretty(isPretty());
        }
        super.start();
    }

    @Override
    public void stop()
    {
        super.stop();
    }

    @Override
    public IJSONFormatter getJSONFormatter()
    {
        return m_formatter;
    }

    public void setJSONFormatter(final IJSONFormatter formatter)
    {
        m_formatter = formatter;
    }

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
        return CONTENT_TYPE_JSON;
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
        if (null == getJSONFormatter())
        {
            return EMPTY_STRING;
        }
        try
        {
            return getJSONFormatter().toJSONString(target) + requireNonNullOrElse(getLineFeed(), NEWLINE_STRING);
        }
        catch (final JSONFormattingException e)
        {
            addError("error converting to JSON.", e);

            return EMPTY_STRING;
        }
    }

    public void setLineFeed(final String line)
    {
        m_line_feed = line;
    }

    @Override
    public String getLineFeed()
    {
        return m_line_feed;
    }
}
