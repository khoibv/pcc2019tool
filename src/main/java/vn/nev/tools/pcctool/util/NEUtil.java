package vn.nev.tools.pcctool.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import vn.nev.tools.pcctool.common.Constants;

public final class NEUtil {

  public static String toPascalCase(String source) {
    if (StringUtils.isEmpty(source)) {
      return source;
    }

    String[] parts = source.split("_");
    String camelCaseString = Constants.EMPTY;
    for (String part : parts) {
      camelCaseString = camelCaseString + toProperCase(part);
    }

    return camelCaseString;
  }

  public static String toProperCase(String source) {
    return source.substring(0, 1).toUpperCase() + source.substring(1).toLowerCase();
  }

  public static String toCamelCase(String source) {
    if (StringUtils.isEmpty(source)) {
      return source;
    }

    String pascalCaseString = toPascalCase(source);
    return pascalCaseString.substring(0, 1).toLowerCase() + pascalCaseString.substring(1);
  }

  /**
   * Format date to string with specific pattern
   */
  public static String formatDate(Date date, String pattern) {
    SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
    dateFormat.setLenient(false);

    return dateFormat.format(date);
  }

}
