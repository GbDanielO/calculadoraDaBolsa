package br.com.calculadoradabolsa.notanegociacao.controller;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.calculadoradabolsa.notanegociacao.service.ISrvNotaNegociacao;

/**
 * 
 * @author Daniel Oliveira
 * 
 * calculadoradabolsa: 08-dezembro-2019 04:00
 *
 * Todos os direitos reservados. Nenhuma parte de código
 * desse projeto pode ser copiada sem autorização expressa
 * para quaisquer fins.
 */
@Controller
@RequestMapping(path = "/notasNegociacao")
public class NotaNegociacaoController implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 6044448072761589955L;

  @Autowired
  private ISrvNotaNegociacao srvNotaNegociacao;

  @RequestMapping(path = "")
  public String carregaPaginaNotasNegociacao() {
    return "notaNegociacao/notaNegociacao";
  }

}
