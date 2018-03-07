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

import java.util.Map;

/**
 * A {@code IJSONFormatter} interface formats an object into a JSON string.
 *
 * @author Dean S. Jones
 * @since 2.0.6-SNAPSHOT
 */

public interface IJSONFormatter extends IJSONPrettyPrinter
{
    /**
     * Converts the specified target into a JSON string.
     *
     * @param target the Map to be converted.
     * @param newline to be added at the end.
     * @return a JSON String representation of the target.
     * @throws JSONFormattingException if there was an error converting the target to a String.
     */

    public String toJSONString(Map<String, Object> target) throws JSONFormattingException;
}
