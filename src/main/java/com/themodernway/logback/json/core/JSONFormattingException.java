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
 * A {@code JSONFormattingException} is a subclass of {@link Exception}.
 *
 * @author Dean S. Jones
 * @since 2.0.0-RELEASE
 */

public class JSONFormattingException extends Exception
{
    private static final long serialVersionUID = 2113014474531442350L;

    public JSONFormattingException()
    {
    }

    public JSONFormattingException(final String message)
    {
        super(message);
    }

    public JSONFormattingException(final Throwable cause)
    {
        super(cause);
    }

    public JSONFormattingException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
}
