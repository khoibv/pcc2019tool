package vn.nev.tools.pcctool.dto;

import java.text.MessageFormat;
import lombok.ToString;
import vn.nev.tools.pcctool.common.Constants;
import vn.nev.tools.pcctool.util.NEUtil;


@ToString
public class ColumnDto {

  private String columnName;
  private String columnNameCamel;
  private String columnNamePascal;

  private String comment;

  public ColumnDto() {
  }

  public ColumnDto(String columnName, String comment) {
    this.columnName = columnName;
    this.comment = comment;
  }

  public String getFieldDeclaration() {
    return "  private String " + this.columnNameCamel + ";";
  }

  /**
   * Tạo getter method
   */
  public String getSetterString() {
    // Code khó đọc so với đưa vào Thymeleaf template, nhưng tạm để vì lý do performance
    return MessageFormat.format(
        "  public void set{0}(String {1}) '{'" + Constants.LINE_SEPARATOR_WINDOWS
            + "    this.{1} = {1};" + Constants.LINE_SEPARATOR_WINDOWS
            + "  '}'", this.columnNamePascal, this.columnNameCamel);
  }

  /**
   * Tạo setter method
   */
  public String getGetterString() {
    return MessageFormat.format(
        "  public String get{0}() '{'" + Constants.LINE_SEPARATOR_WINDOWS
            + "    return {1};" + Constants.LINE_SEPARATOR_WINDOWS
            + "  '}'", this.columnNamePascal, this.columnNameCamel);
  }

  /**
   * Tạo chuỗi ký tự định dạng target.setCompanyCode(source.getCompanyCode());
   */
  public String getCopyString(String source, String target) {
    return String.format("    %s.set%s(%s.get%s());", target, columnNamePascal, source, columnNamePascal);
  }

  public String getColumnName() {
    return columnName;
  }

  public void setColumnName(String columnName) {
    this.columnName = columnName;

    this.columnNamePascal = NEUtil.toPascalCase(columnName);
    this.columnNameCamel = NEUtil.toCamelCase(columnName);
  }

  public String getColumnNameCamel() {
    return columnNameCamel;
  }


  public String getColumnNamePascal() {
    return columnNamePascal;
  }


  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }
}
