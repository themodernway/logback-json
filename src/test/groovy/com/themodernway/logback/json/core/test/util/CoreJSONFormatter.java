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

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.themodernway.logback.json.core.IJSONCommon;
import com.themodernway.logback.json.core.IJSONFormatter;

/**
 * A {@code CoreJSONFormatter} interface formats an object into a JSON string.
 *
 * @author Dean S. Jones
 * @since 0.0.1-SNAPSHOT
 */

public class CoreJSONFormatter extends ObjectMapper implements IJSONFormatter, IJSONCommon
{
    private static final long                serialVersionUID = 1L;

    private static final List<Module>        MAPPER_MODULES   = Arrays.asList(new Jdk8Module(), new JavaTimeModule());

    private static final Version             MAPPER_VERSION   = VersionUtil.parseVersion("0.1.0-SNAPSHOT", "com.themodernway", "logback-json-core");

    public static final DefaultPrettyPrinter PRETTY           = PRETTY(JSON_INDENT_VALUE);

    public static final DefaultPrettyPrinter PRETTY(final String indent)
    {
        return new DefaultPrettyPrinter().withArrayIndenter(new DefaultIndenter().withIndent(indent)).withObjectIndenter(new DefaultIndenter().withIndent(indent));
    }

    public CoreJSONFormatter()
    {
        registerModules(MAPPER_MODULES).enable(JsonGenerator.Feature.ESCAPE_NON_ASCII).setDefaultPrettyPrinter(PRETTY);
    }

    protected CoreJSONFormatter(final CoreJSONFormatter parent)
    {
        super(parent);
    }

    @Override
    public void setPretty(final boolean pretty)
    {
        if (pretty != isPretty())
        {
            configure(SerializationFeature.INDENT_OUTPUT, pretty);
        }
    }

    @Override
    public boolean isPretty()
    {
        return isEnabled(SerializationFeature.INDENT_OUTPUT);
    }

    @Override
    public Version version()
    {
        return MAPPER_VERSION;
    }

    @Override
    public CoreJSONFormatter copy()
    {
        _checkInvalidCopy(CoreJSONFormatter.class);

        return new CoreJSONFormatter(this);
    }

    @Override
    public String toJSONString(final Map<String, Object> target) throws Exception
    {
        return writeValueAsString(target);
    }
}
