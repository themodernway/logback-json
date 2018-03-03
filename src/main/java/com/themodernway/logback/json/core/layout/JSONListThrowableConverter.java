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
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

import com.themodernway.logback.json.core.IJSONCommon;
import com.themodernway.logback.json.core.IJSONThrowableConverter;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.StackTraceElementProxy;

public class JSONListThrowableConverter implements IJSONThrowableConverter, IJSONCommon
{
    private int                             m_maxdeep;

    private final AtomicBoolean             m_started = new AtomicBoolean(false);

    private final ThreadLocal<List<String>> m_getlist = ThreadLocal.withInitial(() -> new ArrayList<>(32));

    public JSONListThrowableConverter()
    {
        m_maxdeep = (Integer.MAX_VALUE - 1);
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
    public Supplier<Object> supplier(final ILoggingEvent event)
    {
        return () -> nullOrOtherwise(event.getThrowableProxy(), tp -> toListOrNull(recursive(m_getlist.get(), tp, 0)));
    }

    protected List<String> recursive(final List<String> list, final IThrowableProxy tp, final int deep)
    {
        if ((null == tp) || (deep >= getMaxDepth()))
        {
            return list;
        }
        if (null != tp.getCause())
        {
            recursive(list, tp.getCause(), deep + 1);
        }
        final String mess = tp.getMessage();

        if (null == mess)
        {
            list.add(tp.getClassName());
        }
        else
        {
            list.add(tp.getClassName() + "(" + mess + ")");
        }
        stack(list, tp);

        final IThrowableProxy[] supp = tp.getSuppressed();

        if (null != supp)
        {
            for (final IThrowableProxy sp : supp)
            {
                recursive(list, sp, deep + 1);
            }
        }
        return list;
    }

    protected void stack(final List<String> list, final IThrowableProxy tp)
    {
        final StackTraceElementProxy[] elements = tp.getStackTraceElementProxyArray();

        if (null != elements)
        {
            final int size = elements.length - tp.getCommonFrames();

            for (int i = 0; i < size; i++)
            {
                list.add(toString(elements[i].getStackTraceElement()));
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
