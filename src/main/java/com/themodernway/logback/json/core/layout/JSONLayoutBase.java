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
import java.util.function.Supplier;

import com.themodernway.logback.json.core.LogbackJSONFormatter;
import com.themodernway.logback.json.core.LogbackObjectMapper;

import ch.qos.logback.core.LayoutBase;

public abstract class JSONLayoutBase<E> extends LayoutBase<E>
{
    private LogbackJSONFormatter             m_formatter;

    private static final LogbackObjectMapper JSON_MAPPER = new LogbackObjectMapper();

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

    protected final LogbackJSONFormatter formatter()
    {
        final LogbackJSONFormatter format = getLogbackJSONFormatter();

        return (null != format ? format : JSON_MAPPER);
    }

    protected void add(final Map<String, Object> target, final String name, final boolean flag, final Supplier<?> supplier)
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

    protected Supplier<?> map(final Supplier<Map<String, ?>> supplier)
    {
        return () -> map(supplier.get());
    }

    protected Map<String, ?> map(final Map<String, ?> target)
    {
        return ((null == target) || (target.isEmpty())) ? null : target;
    }

    protected void add(final Map<String, Object> target, final String name, final Supplier<?> supplier)
    {
        add(target, name, true, supplier);
    }

    @Override
    public String getContentType()
    {
        return "application/json";
    }

    @Override
    public String doLayout(final E event)
    {
        final Map<String, Object> target = convertEvent(event);

        if ((null == target) || (target.isEmpty()))
        {
            return null;
        }
        try
        {
            return formatter().toJSONString(target) + "\n";
        }
        catch (final Exception e)
        {
            return target.toString();
        }
    }

    protected abstract Map<String, Object> convertEvent(final E event);
}
