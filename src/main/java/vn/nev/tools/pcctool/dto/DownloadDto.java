package vn.nev.tools.pcctool.dto;


import java.util.Date;
import java.util.UUID;
import lombok.Data;

@Data
public class DownloadDto {

  private String id = UUID.randomUUID().toString();
  private String fullPath;
  private String fileName;
  private Date requestAt = new Date();

}
