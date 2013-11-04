package com.fasterxml.jackson.datatype.jsonlib;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SimpleReadTest extends TestBase
{
    public void testReadObject() throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JsonLibModule());

        JSONObject ob = mapper.readValue("{\"a\":{\"b\":3}, \"c\":[9, -4], \"d\":null, \"e\":true}",
                JSONObject.class);
        assertEquals(4, ob.size());
        JSONObject ob2 = ob.getJSONObject("a");
        assertEquals(1, ob2.size());
        assertEquals(3, ob2.getInt("b"));
        JSONArray array = ob.getJSONArray("c");
        assertEquals(2, array.size());
        assertEquals(9, array.getInt(0));
        assertEquals(-4, array.getInt(1));
        assertTrue(JSONUtils.isNull(ob.get("d")));
        assertTrue(ob.getBoolean("e"));
    }

    public void testReadArray() throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JsonLibModule());

        JSONArray array = mapper.readValue("[null, 13, false, 1.25, \"abc\", {\"a\":13}, [ ] ]",
                JSONArray.class);
        assertEquals(7, array.size());
        assertTrue(JSONUtils.isNull(array.get(0)));
        assertEquals(13, array.getInt(1));
        assertFalse(array.getBoolean(2));
        assertEquals(Double.valueOf(1.25), array.getDouble(3));
        assertEquals("abc", array.getString(4));
        JSONObject ob = array.getJSONObject(5);
        assertEquals(1, ob.size());
        assertEquals(13, ob.getInt("a"));
        JSONArray array2 = array.getJSONArray(6);
        assertEquals(0, array2.size());
    }
}
