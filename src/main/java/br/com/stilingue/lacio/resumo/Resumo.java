package br.com.stilingue.lacio.resumo;

import static com.google.common.base.Preconditions.checkNotNull;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.stilingue.lacio.conceito.Conceito;
import br.com.stilingue.lacio.conceito.ConceitoContext;

/**
 * Deixamos a API implementada (/api/resumo/{palavra}, mas não a utilizamos no view
 * (faltou tempo).
 *
 * @author bbviana
 */
@Component
public class Resumo implements Conceito<Palavra> {

    @Autowired
    private ResumoFetcher fetcher;

    @Autowired
    private ResumoParser parser;

    @Override
    public Palavra get(ConceitoContext ctx) {
        String palavra = checkNotNull(ctx.getPalavra());

        Document document = fetcher.fetch(palavra);
        return parser.parse(document, palavra);
    }
}
