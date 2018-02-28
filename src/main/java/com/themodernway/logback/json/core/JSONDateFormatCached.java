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

package com.themodernway.logback.json.core;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class JSONDateFormatCached extends SimpleDateFormat
{
    private static final long serialVersionUID = -2292887716545717360L;

    private transient long    m_last;

    private transient String  m_save;

    public JSONDateFormatCached(final String pattern, final TimeZone zone)
    {
        super(pattern);

        setTimeZone(zone);
    }

    public String format(final long time)
    {
        if (time != m_last)
        {
            m_last = time;

            m_save = super.format(new Date(m_last));
        }
        return m_save;
    }

    @Override
    public int hashCode()
    {
        return super.hashCode();
    }

    @Override
    public boolean equals(final Object object)
    {
        if (this == object)
        {
            return true;
        }
        if ((null == object) || (getClass() != object.getClass()))
        {
            return false;
        }
        return super.equals(object);
    }
}