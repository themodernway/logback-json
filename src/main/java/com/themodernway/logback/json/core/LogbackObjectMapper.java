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

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

/**
 * A {@code LogbackObjectMapper} interface formats an object into a JSON string.
 *
 * @author Dean S. Jones
 * @since 0.0.1-SNAPSHOT
 */

public class LogbackObjectMapper extends ObjectMapper implements JSONFormatter
{
    private static final long         serialVersionUID = 1L;

    private static final List<Module> MAPPER_MODULES   = Arrays.asList(new ParameterNamesModule(), new Jdk8Module(), new JavaTimeModule());

    private static final Version      MAPPER_VERSION   = VersionUtil.parseVersion("0.0.1-SNAPSHOT", "com.themodernway", "logback-json-core");

    public LogbackObjectMapper()
    {
        registerModules(MAPPER_MODULES).enable(JsonGenerator.Feature.ESCAPE_NON_ASCII);
    }

    protected LogbackObjectMapper(final LogbackObjectMapper parent)
    {
        super(parent);
    }

    @Override
    public Version version()
    {
        return MAPPER_VERSION;
    }

    @Override
    public LogbackObjectMapper copy()
    {
        _checkInvalidCopy(LogbackObjectMapper.class);

        return new LogbackObjectMapper(this);
    }

    @Override
    public String toJSONString(final Object target) throws Exception
    {
        return writeValueAsString(target);
    }
}
