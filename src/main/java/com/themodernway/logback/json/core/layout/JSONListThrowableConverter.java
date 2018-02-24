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

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.StackTraceElementProxy;

public class JSONListThrowableConverter implements JSONThrowableConverter, IJSONCommon
{
    private int                 m_maxdeep;

    private final AtomicBoolean m_started = new AtomicBoolean(false);

    public JSONListThrowableConverter()
    {
        this(Integer.MAX_VALUE - 1);
    }

    public JSONListThrowableConverter(final int maxdeep)
    {
        setMaxDepth(maxdeep);
    }

    public void setMaxDepth(final int maxdeep)
    {
        m_maxdeep = maxdeep;
    }

    public int getMaxDepth()
    {
        return m_maxdeep;
    }

    @Override
    public Supplier<?> supplier(final ILoggingEvent event)
    {
        if (false == isStarted())
        {
            return nothing();
        }
        final IThrowableProxy tp = event.getThrowableProxy();

        if (null == tp)
        {
            return nothing();
        }
        final ArrayList<Object> list = new ArrayList<Object>();

        recursive(list, tp, 0);

        if (list.isEmpty())
        {
            return nothing();
        }
        return () -> list;
    }

    public void recursive(final ArrayList<Object> list, final IThrowableProxy tp, final int deep)
    {
        if ((null == tp) || (deep >= getMaxDepth()))
        {
            return;
        }
        if (null != tp.getCause())
        {
            recursive(list, tp.getCause(), deep + 1);
        }
        list.add(tp.getClassName() + ": " + tp.getMessage());

        stack(list, tp, deep + 1);

        final IThrowableProxy[] supp = tp.getSuppressed();

        if (null != supp)
        {
            for (final IThrowableProxy sp : supp)
            {
                recursive(list, sp, deep + 1);
            }
        }
    }

    public void stack(final ArrayList<Object> list, final IThrowableProxy tp, final int deep)
    {
        final StackTraceElementProxy[] elements = tp.getStackTraceElementProxyArray();

        if (null != elements)
        {
            final int size = elements.length - tp.getCommonFrames();

            for (int i = 0; i < size; i++)
            {
                list.add(elements[i].getStackTraceElement().toString());
            }
        }
    }

    @Override
    public void start()
    {
        m_started.set(true);
    }

    @Override
    public void stop()
    {
        m_started.set(false);
    }

    @Override
    public boolean isStarted()
    {
        return m_started.get();
    }
}
