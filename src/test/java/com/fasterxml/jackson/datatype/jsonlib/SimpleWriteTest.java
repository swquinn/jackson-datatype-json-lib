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
import net.sf.json.util.JSONTokener;

import org.codehaus.jackson.map.ObjectMapper;

public class SimpleWriteTest extends TestBase
{
    public void testWriteObject() throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JsonLibModule());

        // Ok: let's create JSONObject from JSON text
        String JSON = "{\"a\":{\"b\":3}}";
        JSONTokener tok = new JSONTokener(JSON);
        JSONObject ob = (JSONObject) tok.nextValue();
        assertEquals(JSON, mapper.writeValueAsString(ob));
    }

    public void testWriteArray() throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JsonLibModule());

        // Ok: let's create JSONObject from JSON text
        String JSON = "[1,true,\"text\",[null,3],{\"a\":[1.25]}]";
        JSONTokener tok = new JSONTokener(JSON);
        JSONArray ob = (JSONArray) tok.nextValue();
        assertEquals(JSON, mapper.writeValueAsString(ob));
    }
}
