package br.com.stilingue.lacio.relacao;

import static java.lang.String.format;

import org.jsoup.nodes.Document;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import br.com.stilingue.lacio.SilentJsoup;

/**
 * Recupera palavras relacionadas dentro de um domínio conceitual.
 * Utiliza como fonte dicionariocriativo.com.br.
 *
 * @author bbviana
 */
@Component
public class PalavrasRelacionadasFetcher {

    @Cacheable("relacionadas")
    public Document fetch(String dominio, String palavra) {
        String url = format("https://dicionariocriativo.com.br/analogico/%s/substantivo/%s",
                palavra, dominio);
        return SilentJsoup.get(url);
    }
}
