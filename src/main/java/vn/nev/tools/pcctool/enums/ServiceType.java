package vn.nev.tools.pcctool.enums;

public enum ServiceType {

  SELECT("R", "select1"),

  STORE("P", "proc1");

  private String type;

  private String methodName;

  ServiceType(String type, String methodName) {
    this.type = type;
    this.methodName = methodName;
  }

  public String getDtoSuffix() {
    return type + methodName + "Dto";
  }

  public String getAccessSuffix() {
    return type + "Access";
  }

  public String getType() {
    return type;
  }

  public String getMethodName() {
    return methodName;
  }
}
