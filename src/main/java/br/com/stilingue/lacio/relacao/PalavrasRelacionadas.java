package br.com.stilingue.lacio.relacao;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.LinkedHashSet;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.stilingue.lacio.conceito.Conceito;
import br.com.stilingue.lacio.conceito.ConceitoContext;

/**
 * @author bbviana
 */
@Component
public class PalavrasRelacionadas implements Conceito<LinkedHashSet<String>> {

    @Autowired
    private PalavrasRelacionadasFetcher fetcher;

    @Autowired
    private PalavrasRelacionadasParser parser;

    @Override
    public LinkedHashSet<String> get(ConceitoContext ctx) {
        String palavra = checkNotNull(ctx.getPalavra());
        String dominio = checkNotNull(ctx.getDominio());
        Integer page = checkNotNull(ctx.getPage());

        Document document = fetcher.fetch(dominio, palavra);
        return parser.parse(document, page);
    }
}
