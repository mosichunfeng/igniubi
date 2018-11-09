package cn.neusoft.xuxiao.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Map;

public class JsonTool
{
  public static String dataToJson(Object data)
  {
    try
    {
      ObjectMapper mapper = new ObjectMapper();
      mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
      mapper.enable(SerializationFeature.INDENT_OUTPUT);
      StringWriter sw = new StringWriter();
      mapper.writeValue(sw, data);
      return sw.toString();
    } catch (IOException e) {
      e.printStackTrace();
    }throw new RuntimeException("IOException from a StringWriter?");
  }

  public static String dataToJsonWithNull(Object data)
  {
    try {
      ObjectMapper mapper = new ObjectMapper();

      mapper.enable(SerializationFeature.INDENT_OUTPUT);
      StringWriter sw = new StringWriter();
      mapper.writeValue(sw, data);
      return sw.toString(); } catch (IOException e) {
    }
    throw new RuntimeException("IOException from a StringWriter?");
  }

  public static Map<String, Object> jsonToMap(String data)
  {
    ObjectMapper mapper = new ObjectMapper();
    try {
      return (Map)mapper.readValue(data, Map.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static <V> V jsonToObject(String data, Class<V> valueClass, boolean fullmap)
  {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      if (!fullmap) {
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
      }
      return objectMapper.readValue(data, valueClass);
    } catch (JsonParseException e) {
      e.printStackTrace();
    } catch (JsonMappingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static <V> V jsonToObject(String data, Class<V> valueClass)
  {
    return jsonToObject(data, valueClass, false);
  }

  public static Object setJsonToObject(String data, TypeReference<?> typeReference, boolean fullmap)
  {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      if (!fullmap) {
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
      }
      return objectMapper.readValue(data, typeReference);
    } catch (JsonParseException e) {
      e.printStackTrace();
    } catch (JsonMappingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}