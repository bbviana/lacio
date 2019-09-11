package br.com.stilingue.lacio;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.LinkedHashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author bbviana
 */
@Controller
@RequestMapping("/api")
public class LacioController {

    @Autowired
    private ImportService importService;

    @RequestMapping(value = "/", method = GET)
    @ResponseBody
    public String index() {
        return "Index";
    }

    @RequestMapping(value = "/dominios/{palavra}", method = GET)
    @ResponseBody
    public LinkedHashSet<String> dominios(@PathVariable String palavra) {
        return importService.dominios(palavra);
    }

    @RequestMapping(value = "/relacionadas/{dominio}/{palavra}", method = GET)
    @ResponseBody
    public LinkedHashSet<String> relacionadas(
            @PathVariable String dominio,
            @PathVariable String palavra) {
        return importService.relacionadas(dominio, palavra);
    }

    @RequestMapping(value = "/resumo/{palavra}", method = GET)
    @ResponseBody
    public Palavra resumo(@PathVariable String palavra) {
        return importService.fetch(palavra);
    }
}