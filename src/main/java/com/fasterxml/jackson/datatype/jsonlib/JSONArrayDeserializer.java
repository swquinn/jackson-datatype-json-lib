package com.fasterxml.jackson.datatype.jsonlib;

import java.io.IOException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * A datatype deserializer for the JSON-Lib {@link JSONArray} object.
 * @author Sean.Quinn
 * @since 1.0
 */
public class JSONArrayDeserializer extends StdDeserializer<JSONArray>
{
    private static final long serialVersionUID = 1L;

    public final static JSONArrayDeserializer instance = new JSONArrayDeserializer();

    public JSONArrayDeserializer()
    {
        super(JSONArray.class);
    }
    
    @Override
    public JSONArray deserialize(JsonParser jp, DeserializationContext ctxt)
        throws IOException, JsonProcessingException
    {
        JSONArray array = new JSONArray();
        JsonToken t;
        while ((t = jp.nextToken()) != JsonToken.END_ARRAY) {
            switch (t) {
            case START_ARRAY:
                array.add(deserialize(jp, ctxt));
                continue;
            case START_OBJECT:
                array.add(JSONObjectDeserializer.instance.deserialize(jp, ctxt));
                continue;
            case VALUE_STRING:
                array.add(jp.getText());
                continue;
            case VALUE_NULL:
                array.add(new JSONObject(true));
                continue;
            case VALUE_TRUE:
                array.add(Boolean.TRUE);
                continue;
            case VALUE_FALSE:
                array.add(Boolean.FALSE);
                continue;
            case VALUE_NUMBER_INT:
                array.add(jp.getNumberValue());
                continue;
            case VALUE_NUMBER_FLOAT:
                array.add(jp.getNumberValue());
                continue;
            case VALUE_EMBEDDED_OBJECT:
                array.add(jp.getEmbeddedObject());
                continue;
            default:
                throw ctxt.mappingException("Unrecognized or unsupported JsonToken type: "+t);
            }
        }
        return array;
    }
}
