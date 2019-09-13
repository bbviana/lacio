package br.com.stilingue.lacio;

import static com.google.common.collect.Lists.newArrayList;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.stilingue.lacio.resumo.Palavra;
import br.com.stilingue.lacio.significado.Significado;

/**
 * @author bbviana
 */
@RequestMapping("/api")
@Controller
public class ApiController {

    @Autowired
    private PalavrasService palavrasService;

    @RequestMapping(value = "/", method = GET)
    @ResponseBody
    public List<String> index() {
        return newArrayList(
                "[GET] /dominios/{palavra}/{page}",
                "[GET] /relacionadas/{dominio}/{palavra}/{page}",
                "[GET] /significados/{dominio}/{palavra}",
                "[GET] /resumo/{palavra}"
        );
    }

    @RequestMapping(value = "/dominios/{palavra}/{page}", method = GET)
    @ResponseBody
    public LinkedHashSet<String> dominios(@PathVariable String palavra, @PathVariable int page) {
        return palavrasService.dominios(palavra, page);
    }

    @RequestMapping(value = "/relacionadas/{dominio}/{palavra}/{page}", method = GET)
    @ResponseBody
    public LinkedHashSet<String> relacionadas(
            @PathVariable String dominio,
            @PathVariable String palavra,
            @PathVariable int page) {
        return palavrasService.relacionadas(dominio, palavra, page);
    }

    @RequestMapping(value = "/significados/{dominio}/{palavra}", method = GET)
    @ResponseBody
    public List<Significado> significados(
            @PathVariable String dominio,
            @PathVariable String palavra) {
        return palavrasService.significados(dominio, palavra);
    }

    @RequestMapping(value = "/resumo/{palavra}", method = GET)
    @ResponseBody
    public Palavra resumo(@PathVariable String palavra) {
        return palavrasService.resumo(palavra);
    }
}
