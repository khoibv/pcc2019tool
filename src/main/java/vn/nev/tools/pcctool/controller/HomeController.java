package vn.nev.tools.pcctool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import vn.nev.tools.pcctool.dto.ConversionRequestDto;
import vn.nev.tools.pcctool.dto.ConversionResponseDto;
import vn.nev.tools.pcctool.dto.ResponseDto;
import vn.nev.tools.pcctool.service.IConversionService;

@Controller
public class HomeController extends BaseController {

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

    return ResponseDto.success(responseDto);
  }

}
