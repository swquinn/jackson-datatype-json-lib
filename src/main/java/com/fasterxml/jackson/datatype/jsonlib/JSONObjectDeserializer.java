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

import java.io.IOException;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.deser.std.StdDeserializer;

public class JSONObjectDeserializer extends StdDeserializer<JSONObject>
{
    private static final long serialVersionUID = 1L;

    public final static JSONObjectDeserializer instance = new JSONObjectDeserializer();

    public JSONObjectDeserializer()
    {
        super(JSONObject.class);
    }

    @Override
    public JSONObject deserialize(JsonParser jp, DeserializationContext ctxt)
        throws IOException, JsonProcessingException
    {
        JSONObject ob = new JSONObject();
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.START_OBJECT) {
            t = jp.nextToken();
        }
        for (; t == JsonToken.FIELD_NAME; t = jp.nextToken()) {
            String fieldName = jp.getCurrentName();
            t = jp.nextToken();
            try {
                switch (t) {
                case START_ARRAY:
                    ob.put(fieldName, JSONArrayDeserializer.instance.deserialize(jp, ctxt));
                    continue;
                case START_OBJECT:
                    ob.put(fieldName, deserialize(jp, ctxt));
                    continue;
                case VALUE_STRING:
                    ob.put(fieldName, jp.getText());
                    continue;
                case VALUE_NULL:
                    ob.put(fieldName, new JSONObject(true));
                    continue;
                case VALUE_TRUE:
                    ob.put(fieldName, Boolean.TRUE);
                    continue;
                case VALUE_FALSE:
                    ob.put(fieldName, Boolean.FALSE);
                    continue;
                case VALUE_NUMBER_INT:
                    ob.put(fieldName, jp.getNumberValue());
                    continue;
                case VALUE_NUMBER_FLOAT:
                    ob.put(fieldName, jp.getNumberValue());
                    continue;
                case VALUE_EMBEDDED_OBJECT:
                    ob.put(fieldName, jp.getEmbeddedObject());
                    continue;
                default:
                }
            } catch (JSONException e) {
                throw ctxt.mappingException("Failed to construct JSONObject: "+e.getMessage());
            }
            throw ctxt.mappingException("Urecognized or unsupported JsonToken type: "+t);
        }
        return ob;
    }    
}
