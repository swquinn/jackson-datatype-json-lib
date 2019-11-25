> **NOTICE:** This repository is no longer being actively maintained. Many of its dependencies are woefully out of date. If you use it and would like to issue pull requests to update its functionality or you are interested in taking ownership of the repository, please open an issue indicating as much.

Project to build Jackson (http://jackson.codehaus.org) extension module (jar) to support datatypes of the "net.sf.json" JSON library (see http://json-lib.sourceforge.net/). Based largely off of the work done by FasterXML for [jackson-datatype-json-org](https://github.com/FasterXML/jackson-datatype-json-org) to support the [JSON.org](http://json.org/) library.

This project largely was created to fill a need in another project where the JSON-Lib library was being used, and it wasn't feasible to convert to Jackson for everything. The package namespaces were kept as `com.fasterxml.jackson...` because it made the most sense.

## Status

This module is fully usable and officially released; there are two versions, one specifically targetting version `1.9.2` of the Jackson libraries (for seamless compatibility with Hibernate 4) and another release targetting Jackson 2.2.3 and beyond.

Any future development will focus of support for Jackson 2.x.

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
