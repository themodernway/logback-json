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

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import ch.qos.logback.classic.spi.ILoggingEvent;

public class JSONLayout extends JSONLayoutBase<ILoggingEvent>
{
    public JSONLayout()
    {
    }

    @Override
    protected Map<String, Object> convertEvent(final ILoggingEvent event)
    {
        final Map<String, Object> target = new LinkedHashMap<String, Object>();

        add(target, "time", true, () -> new Date(event.getTimeStamp()).toString());

        add(target, "level", true, () -> String.valueOf(event.getLevel()));

        add(target, "logger", true, () -> event.getLoggerName());

        add(target, "message", true, () -> event.getFormattedMessage());

        add(target, "state", true, map(() -> event.getMDCPropertyMap()));

        return target;
    }
}
