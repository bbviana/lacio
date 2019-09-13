package br.com.stilingue.lacio.significado;

import static java.lang.String.format;

import org.jsoup.nodes.Document;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import br.com.stilingue.lacio.SilentJsoup;

/**
 * Recupera significados de uma palavra usando a API do Google Dictionary.
 *
 * @author bbviana
 */
@Component
public class SignificadoFetcher {

    @Cacheable("significados")
    public Document fetch(String palavra) {
        String url = format("https://www.google.com.br/async/dictw?async=term:%s,corpus:pt-BR", palavra);
        return SilentJsoup.get(url);
    }
}
