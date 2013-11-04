package com.fasterxml.jackson.datatype.jsonlib;

import java.io.IOException;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;

public class TestVersions extends TestBase
{
    public void testMapperVersions() throws IOException
    {
    	JsonLibModule module = new JsonLibModule();
        assertVersion(module);
    }

    /*
    /**********************************************************
    /* Helper methods
    /**********************************************************
     */
    
    private void assertVersion(Versioned vers)
    {
        final Version v = vers.version();
        assertEquals(PackageVersion.VERSION, v);
    }
}

