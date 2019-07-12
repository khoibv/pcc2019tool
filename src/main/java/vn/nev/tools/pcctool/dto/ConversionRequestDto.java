package vn.nev.tools.pcctool.dto;

import java.util.List;
import javax.validation.constraints.Size;
import lombok.Data;
import vn.nev.tools.pcctool.common.ColumnLengths;
import vn.nev.tools.pcctool.enums.ServiceType;

@Data
public class ConversionRequestDto {

  private String serviceId;
  private ServiceType serviceType = ServiceType.SELECT; // SELECT or STORE PROC
  private String author;
  private List<ColumnDto> selectColumns;
  private List<ColumnDto> whereColumns;

  public String getDtoName() {
    return this.serviceId + serviceType.getDtoSuffix();
  }

  public String getAccessName() {
    return this.serviceId + serviceType.getAccessSuffix();
  }
}
