Project to build Jackson (http://jackson.codehaus.org) extension module (jar) to support datatypes of the "net.sf.json" JSON library (see [http://json-lib.sourceforge.net/])

## Status

Module is fully usable and officially released; the initial version specifically targets Jackson 2.2.3 and is based off of the work done to support the [JSON.org](http://json.org/) library.

Only Jackson 2.x is supported at this time.

## Usage

### Maven dependency

To use module (version 2.x) on Maven-based projects, use following dependency:

```xml
<dependency>
  <groupId>com.fasterxml.jackson.datatype</groupId>
  <artifactId>jackson-datatype-json-lib</artifactId>
  <version>2.2.3</version>
</dependency>
```

(or whatever version is most up-to-date at the moment)

### Registering module

To use the the Module in Jackson, simply register it with the ObjectMapper instance:

```java
// import com.fasterxml.jackson.datatype.jsonlib.JsonLibModule;

ObjectMapper mapper = new ObjectMapper();
mapper.registerModule(new JsonLibModule());
```

This will ensure that basic datatype of `org.json` package can be read and written using Jackson data-binding functionality.

### Data conversions

After registering the module, you can read and write JSON to/from org.json.JSONObject similar to handling custom POJOs or standard JDK types:

```java
JSONObject ob = mapper.readValue(json, JSONObject.class); // read from a source
String json = mapper.writeValue(ob); // output as String
```

As well as do conversion to/from POJOs:

```java
MyValue value = mapper.convertValue(jsonObject, MyValue.class);
JSONObject jsonObject = mapper.convertValue(value, JSONObject.class);
```

or to/from Tree Model:

```java
JsonNode root = mapper.valueToTree(jsonObject);
jsonObject = mapper.treeToValue(root, JSONObject.class);
```

Similarly, you can read/write/convert-to/convert-from `JSONArray` instead of `JSONObject`.