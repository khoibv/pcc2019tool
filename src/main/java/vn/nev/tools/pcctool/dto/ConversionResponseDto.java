package vn.nev.tools.pcctool.dto;

import lombok.Data;

@Data
public class ConversionResponseDto {

  private String inBean;
  private String outBean;
  private String dto;
  private String service;
  private String serviceRemote;
  private String dataAccess;

}
