/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fasterxml.jackson.datatype.jsonlib;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.fasterxml.jackson.databind.module.SimpleModule;

public class JsonLibModule extends SimpleModule
{
    private static final long serialVersionUID = 1;

    private final static String NAME = "JsonLibModule";

    /**
     * Constructor.
     * @see com.fasterxml.jackson.databind.module.SimpleModule#SimpleModule()
     */
    public JsonLibModule()
    {
        super(NAME, PackageVersion.VERSION);
        addDeserializer(JSONArray.class, JSONArrayDeserializer.instance);
        addDeserializer(JSONObject.class, JSONObjectDeserializer.instance);
        addSerializer(JSONArraySerializer.instance);
        addSerializer(JSONObjectSerializer.instance);
    }
}