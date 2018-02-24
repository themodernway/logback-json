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

import java.io.InputStream;

import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;

public final class LoggingOps
{
    static
    {
        if (false == SLF4JBridgeHandler.isInstalled())
        {
            SLF4JBridgeHandler.install();
        }
    }

    private LoggingOps()
    {
    }

    public static final void load(final String location) throws Exception
    {
        load(LoggingOps.class, location);
    }

    public static final void load(final Class<?> claz, final String location) throws Exception
    {
        final InputStream url = claz.getResourceAsStream(location);

        final LoggerContext context = context();

        try
        {
            final JoranConfigurator configurator = new JoranConfigurator();

            configurator.setContext(context);

            context.reset();

            configurator.doConfigure(url);
        }
        catch (final JoranException e)
        {
            StatusPrinter.printInCaseOfErrorsOrWarnings(context);

            return;
        }
        StatusPrinter.printInCaseOfErrorsOrWarnings(context);
    }

    public static final LoggerContext context()
    {
        return (LoggerContext) LoggerFactory.getILoggerFactory();
    }
}
