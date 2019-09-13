package br.com.stilingue.lacio.resumo;

import org.jsoup.nodes.Document;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import br.com.stilingue.lacio.SilentJsoup;

/**
 * Recupera detalhes de uma palavra a partir de dicionariocriativo.com.br
 *
 * @author bbviana
 */
@Component
public class ResumoFetcher {

    @Cacheable("resumo")
    public Document fetch(String palavra) {
        String url = "https://dicionariocriativo.com.br/" + palavra;
        return SilentJsoup.get(url);
    }
}
