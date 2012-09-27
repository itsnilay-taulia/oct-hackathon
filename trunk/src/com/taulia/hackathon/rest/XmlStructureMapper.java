package com.taulia.hackathon.rest;

import android.util.Log;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Matt Zeilenga
 */
public class XmlStructureMapper {
  private static final String LOG_TAG = "XmlContentMapper";

  private Map<String, String> content;

  public XmlStructureMapper(Node xmlContent) {
    this.content = new HashMap<String, String>();

    NodeList children = xmlContent.getChildNodes();
    for (int i = 0; i < children.getLength(); i++) {
      Node child = children.item(i);
      content.put(child.getNodeName(), child.getNodeValue());
    }
  }

  public String getFieldValueAsString(String fieldName) {
    return content.get(fieldName);
  }

  public BigDecimal getFieldValueAsBigDecimal(String fieldName) {
    String fieldValAsStr = getFieldValueAsString(fieldName);
    try {
      return new BigDecimal(fieldValAsStr);
    }
    catch (Exception ex) {
      logParseException(fieldName, fieldValAsStr, "BigDecimal", ex);
      return null;
    }
  }

  public Date getFieldValueAsDate(String fieldName) {
    String fieldValAsStr = getFieldValueAsString(fieldName);
    try {
      return new SimpleDateFormat("yyyy-MM-dd").parse(fieldValAsStr);
    }
    catch (Exception ex) {
      logParseException(fieldName, fieldValAsStr, "Date", ex);
      return null;
    }
  }

  private void logParseException(String fieldName, String fieldValue, String type, Exception ex) {
    String msg = "Exception thrown while parsing name [".concat(fieldName).concat("] value [").concat(fieldValue).concat("] as ").concat(type);
    Log.e(LOG_TAG, msg, ex);
  }
}
