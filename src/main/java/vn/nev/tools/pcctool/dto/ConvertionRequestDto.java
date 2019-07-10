package vn.nev.tools.pcctool.dto;

import javax.validation.constraints.Size;
import lombok.Data;
import vn.nev.tools.pcctool.common.ColumnLengths;

@Data
public class ConvertionRequestDto {

  @Size(max = ColumnLengths.QUERY)
  private String query;

}
