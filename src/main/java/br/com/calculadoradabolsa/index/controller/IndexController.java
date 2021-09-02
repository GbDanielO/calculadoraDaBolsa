package br.com.calculadoradabolsa.index.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/")
public class IndexController {

  @RequestMapping("")
  public String carregaPaginaUpload() {
    return "index";
  }

}
