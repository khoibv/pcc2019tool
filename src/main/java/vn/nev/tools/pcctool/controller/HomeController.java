package vn.nev.tools.pcctool.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import vn.nev.tools.pcctool.common.Constants.DateFormat;
import vn.nev.tools.pcctool.common.Constants.Session;
import vn.nev.tools.pcctool.common.exception.NEIOException;
import vn.nev.tools.pcctool.dto.ConversionRequestDto;
import vn.nev.tools.pcctool.dto.ConversionResponseDto;
import vn.nev.tools.pcctool.dto.DownloadDto;
import vn.nev.tools.pcctool.dto.ResponseDto;
import vn.nev.tools.pcctool.service.IConversionService;
import vn.nev.tools.pcctool.util.NEUtil;
import vn.nev.tools.pcctool.util.SessionUtil;

@Controller
public class HomeController extends BaseController {

  private static final String CONTENT_TYPE = "application/zip";

  @Autowired
  private Environment environment;

  @Autowired
  private IConversionService conversionService;

  @GetMapping(path = {"", "/", "home"})
  public String index(Model model) {

    model.addAttribute("conversion", new ConversionRequestDto());

    return "home/index";
  }

  @PostMapping("convert")
  @ResponseBody
  public ResponseDto convert(@RequestBody ConversionRequestDto conversion) {

    ConversionResponseDto responseDto = conversionService.convert(conversion);

    String filename = String
        .format("%s_%s.zip", conversion.getServiceId(), NEUtil.formatDate(new Date(),
            DateFormat.YYYYMMDD_HHMMSS));
    Path path = Paths.get(environment.getProperty("application.path.download"), conversion.getAuthor());
    String zipFilePath = Paths
        .get(path.toString(), filename)
        .toString();

    if(!Files.exists(path)) {
      try {
        Files.createDirectories(path);
      } catch (IOException e) {
        throw new NEIOException(e);
      }
    }

    conversionService.createZipFile(responseDto, zipFilePath);
    DownloadDto downloadDto = new DownloadDto();
    downloadDto.setFullPath(zipFilePath);
    downloadDto.setFileName(filename);
    SessionUtil.set(Session.DOWNLOAD_FILE, downloadDto);

    return ResponseDto.success(downloadDto.getId());
  }


  @GetMapping(value = "download", produces = CONTENT_TYPE)
  public void downloadZip(String downloadId, HttpServletResponse response) {

    DownloadDto downloadDto = SessionUtil.get(Session.DOWNLOAD_FILE);
    if (downloadDto == null || !downloadDto.getId().equals(downloadId)) {
      return;
    }

    File file = new File(downloadDto.getFullPath());
    if (!file.exists()) {
      return;
    }

    try (InputStream inputStream = new FileInputStream(file)) {
      // copy it to response's OutputStream
      response.setContentType(CONTENT_TYPE);
      response.setHeader("Content-Disposition", "attachment;filename=" + downloadDto.getFileName());
      response.setHeader("Content-Length", String.valueOf(file.length()));

      // copy it to response's OutputStream
      FileCopyUtils.copy(inputStream, response.getOutputStream());
      response.flushBuffer();

      SessionUtil.delete(Session.DOWNLOAD_FILE);
      Files.deleteIfExists(Paths.get(downloadDto.getFullPath()));
    } catch (IOException ex) {
      throw new NEIOException(ex);
    }
  }

}
