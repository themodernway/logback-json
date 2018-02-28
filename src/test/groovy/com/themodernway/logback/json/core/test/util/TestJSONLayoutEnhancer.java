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

package com.themodernway.logback.json.core.test.util;

import java.net.InetAddress;
import java.util.Map;

import com.themodernway.logback.json.core.IJSONCommon;
import com.themodernway.logback.json.core.layout.IJSONLayout;
import com.themodernway.logback.json.core.layout.JSONLayoutEnhancer;

import ch.qos.logback.classic.spi.ILoggingEvent;

public class TestJSONLayoutEnhancer implements JSONLayoutEnhancer, IJSONCommon
{
    public static final String COMPUTE_HOSTNAME       = localhost();

    public static final String DEFAULT_HOSTNAME       = "localhost";

    public static final String DEFAULT_HOSTNAME_LABEL = "host_name";

    private boolean            m_show_host_name       = true;

    private String             m_host_name            = COMPUTE_HOSTNAME;

    private String             m_host_name_label      = DEFAULT_HOSTNAME_LABEL;

    private static final String localhost()
    {
        try
        {
            return InetAddress.getLocalHost().getHostName();
        }
        catch (final Exception e)
        {
            return DEFAULT_HOSTNAME;
        }
    }

    public TestJSONLayoutEnhancer()
    {
    }

    public void setShowHostName(final boolean show)
    {
        m_show_host_name = show;
    }

    public boolean getShowHostName()
    {
        return m_show_host_name;
    }

    public void setHostNameLabel(final String label)
    {
        m_host_name_label = label;
    }

    public String getHostNameLabel()
    {
        return m_host_name_label;
    }

    public void setHostName(final String host)
    {
        m_host_name = host;
    }

    public String getHostName()
    {
        return m_host_name;
    }

    @Override
    public void before(final Map<String, Object> target, final IJSONLayout<ILoggingEvent> layout, final ILoggingEvent event)
    {
        append(target, () -> "beg", true, () -> layout.getJSONDateFormatCached().format(getTimeNow()));

        append(target, () -> toTrimOrElse(getHostNameLabel(), DEFAULT_HOSTNAME_LABEL), getShowHostName(), () -> toTrimOrElse(getHostName(), COMPUTE_HOSTNAME));
    }

    @Override
    public void finish(final Map<String, Object> target, final IJSONLayout<ILoggingEvent> layout, final ILoggingEvent event)
    {
        append(target, () -> "end", true, () -> layout.getJSONDateFormatCached().format(getTimeNow()));
    }
}
