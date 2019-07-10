package vn.nev.tools.pcctool.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import vn.nev.tools.pcctool.dto.ConvertionRequestDto;
import vn.nev.tools.pcctool.dto.ConvertionResponseDto;
import vn.nev.tools.pcctool.service.IConvertionService;

@Controller
public class HomeController extends BaseController {

  @Autowired
  private IConvertionService convertionService;

  @GetMapping(path = {"home", ""})
  public String index(Model model) {

    model.addAttribute("convertion", new ConvertionRequestDto());

    return "home/index";
  }

  @PostMapping("convert")
  public String convert(@Valid @ModelAttribute("convertion") ConvertionRequestDto convertion, final Errors errors, Model model) {

    if (errors.hasErrors()) {
      model.addAttribute("convertion", convertion);
      return "home/index";
    }

    ConvertionResponseDto responseDto = convertionService.convert(convertion);
    model.addAttribute("convertion", responseDto);

    return "home/convert-ok";
  }

}
