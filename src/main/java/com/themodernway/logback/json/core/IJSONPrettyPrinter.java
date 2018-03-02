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

/**
 * A {@code IJSONPrettyPrinter} interface, which defines if pretty printing is on or off.
 *
 * @author Dean S. Jones
 * @since 2.0.6-SNAPSHOT
 */

public interface IJSONPrettyPrinter
{
    /**
     * Returns <tt>true</tt> if this IJSONPrettyPrinter is in pretty printing mode.
     *
     * @return <tt>true</tt> if this IJSONPrettyPrinter is in pretty printing mode.
     */

    public boolean isPretty();

    /**
     * Sets this IJSONPrettyPrinter pretty printing mode.
     *
     * @param pretty <tt>true</tt> or <tt>false</tt> for pretty printing mode.
     */

    public void setPretty(final boolean pretty);
}
