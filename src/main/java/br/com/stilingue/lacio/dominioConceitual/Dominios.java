package br.com.stilingue.lacio.dominioConceitual;

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
public class Dominios implements Conceito<LinkedHashSet<String>> {

    @Autowired
    private DominioFetcher fetcher;

    @Autowired
    private DominiosParser parser;

    @Override
    public LinkedHashSet<String> get(ConceitoContext ctx) {
        String palavra = checkNotNull(ctx.getPalavra());
        Integer page = checkNotNull(ctx.getPage());

        Document document = fetcher.fetch(palavra);
        return parser.parse(document, page);
    }
}
