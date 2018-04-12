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

import java.util.List;
import java.util.function.Supplier;

import com.themodernway.logback.json.core.IJSONCommon;
import com.themodernway.logback.json.core.IJSONThrowableConverter;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.StackTraceElementProxy;

public class JSONThrowableListConverter implements IJSONThrowableConverter, IJSONCommon
{
    private int                             m_maxdeep;

    private boolean                         m_started;

    private final ThreadLocal<List<String>> m_getlist = ThreadLocal.withInitial(() -> new JSONThrowableList(32));

    public JSONThrowableListConverter()
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

    public List<String> getFreshBufferList()
    {
        final List<String> list = m_getlist.get();

        if (false == list.isEmpty())
        {
            list.clear();
        }
        return list;
    }

    @Override
    public Supplier<Object> supplier(final ILoggingEvent event)
    {
        return () -> nullOrOtherwise(event.getThrowableProxy(), tp -> toListOrNull(recursive(getFreshBufferList(), tp, 0)));
    }

    protected List<String> recursive(final List<String> list, final IThrowableProxy prox, final int deep)
    {
        if ((isNull(prox)) || (deep >= getMaxDepth()))
        {
            return list;
        }
        final IThrowableProxy caus = prox.getCause();

        if (noNull(caus))
        {
            recursive(list, caus, deep + 1);
        }
        final String mess = prox.getMessage();

        if (isNull(mess))
        {
            list.add(prox.getClassName());
        }
        else
        {
            list.add(prox.getClassName() + "(" + mess + ")");
        }
        stack(list, prox);

        final IThrowableProxy[] supp = prox.getSuppressed();

        if (noNull(supp))
        {
            final int size = supp.length;

            for (int i = 0; i < size; i++)
            {
                recursive(list, supp[i], deep + 1);
            }
        }
        return list;
    }

    protected void stack(final List<String> list, final IThrowableProxy prox)
    {
        final StackTraceElementProxy[] elements = prox.getStackTraceElementProxyArray();

        if (noNull(elements))
        {
            final int size = elements.length;

            for (int i = 0; i < size; i++)
            {
                list.add(toString(elements[i].getStackTraceElement()));
            }
        }
    }

    @Override
    public void start()
    {
        m_started = true;
    }

    @Override
    public void stop()
    {
        m_started = false;
    }

    @Override
    public boolean isStarted()
    {
        return m_started;
    }
}
