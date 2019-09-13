package br.com.stilingue.lacio.dominioConceitual;

import org.jsoup.nodes.Document;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import br.com.stilingue.lacio.SilentJsoup;

/**
 * Dada uma palavra, recupera os domínios conceituais dos quais ela faz parte.
 * Utiliza como fonte dicionariocriativo.com.br.
 *
 * @author bbviana
 */
@Component
public class DominioFetcher {

    @Cacheable("dominios")
    public Document fetch(String palavra) {
        String url = "https://dicionariocriativo.com.br/analogico/" + palavra;
        return SilentJsoup.get(url);
    }
}
