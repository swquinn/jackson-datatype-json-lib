package com.fasterxml.jackson.datatype.jsonlib;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Iterator;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

public class JSONObjectSerializer extends JSONBaseSerializer<JSONObject>
{
    public final static JSONObjectSerializer instance = new JSONObjectSerializer();

    public JSONObjectSerializer()
    {
        super(JSONObject.class);
    }
    
    @Override
    public void serialize(JSONObject value, JsonGenerator jgen, SerializerProvider provider)
        throws IOException, JsonGenerationException
    {
        jgen.writeStartObject();
        serializeContents(value, jgen, provider);
        jgen.writeEndObject();
    }

    @Override
    public void serializeWithType(JSONObject value, JsonGenerator jgen, SerializerProvider provider,
            TypeSerializer typeSer)
        throws IOException, JsonGenerationException
    {
        typeSer.writeTypePrefixForObject(value, jgen);
        serializeContents(value, jgen, provider);
        typeSer.writeTypeSuffixForObject(value, jgen);
    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint)
        throws JsonMappingException
    {
        return createSchemaNode("object", true);
    }
    
    protected void serializeContents(JSONObject value, JsonGenerator jgen, SerializerProvider provider)
        throws IOException, JsonGenerationException
    {
        Iterator<?> it = value.keys();
        while (it.hasNext()) {
            String key = (String) it.next();
            Object ob;
            try {
                ob = value.get(key);
            } catch (JSONException e) {
                throw new JsonGenerationException(e);
            }
            if (ob == null) {
                if (provider.isEnabled(SerializationFeature.WRITE_NULL_MAP_VALUES)) {
                    jgen.writeNullField(key);
                }
                continue;
            }
            jgen.writeFieldName(key);
            Class<?> cls = ob.getClass();
            if (cls == JSONObject.class) {
                serialize((JSONObject) ob, jgen, provider);
            } else if (cls == JSONArray.class) {
                JSONArraySerializer.instance.serialize((JSONArray) ob, jgen, provider);
            } else  if (cls == String.class) {
                jgen.writeString((String) ob);
            } else  if (cls == Integer.class) {
                jgen.writeNumber(((Integer) ob).intValue());
            } else  if (cls == Long.class) {
                jgen.writeNumber(((Long) ob).longValue());
            } else  if (cls == Boolean.class) {
                jgen.writeBoolean(((Boolean) ob).booleanValue());
            } else  if (cls == Double.class) {
                jgen.writeNumber(((Double) ob).doubleValue());
            } else if (cls == JSONArray.class) {
                JSONArraySerializer.instance.serialize((JSONArray) ob, jgen, provider);
            } else if (JSONObject.class.isAssignableFrom(cls)) { // sub-class
                serialize((JSONObject) ob, jgen, provider);
            } else if (JSONArray.class.isAssignableFrom(cls)) { // sub-class
                JSONArraySerializer.instance.serialize((JSONArray) ob, jgen, provider);
            } else {
                provider.defaultSerializeValue(ob, jgen);
            }
        }
    }
}
