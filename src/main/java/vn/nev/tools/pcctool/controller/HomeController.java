package vn.nev.tools.pcctool.controller;

import org.springframework.stereotype.Controller;

@Controller
public class HomeController extends BaseController {

  public String index() {
    return "home/index";
  }


}
