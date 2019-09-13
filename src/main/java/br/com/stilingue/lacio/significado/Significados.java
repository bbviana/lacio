package br.com.stilingue.lacio.significado;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.stilingue.lacio.conceito.Conceito;
import br.com.stilingue.lacio.conceito.ConceitoContext;
import br.com.stilingue.lacio.significado.score.ScoreHeuristic;

/**
 * @author bbviana
 */
@Component
public class Significados implements Conceito<List<Significado>> {

    @Autowired
    private SignificadoFetcher fetcher;

    @Autowired
    private SignificadosParser parser;

    @Autowired
    private ScoreHeuristic scoreHeuristic;

    @Override
    public List<Significado> get(ConceitoContext ctx) {
        String dominio = checkNotNull(ctx.getDominio());
        String palavra = checkNotNull(ctx.getPalavra());

        Document document = fetcher.fetch(palavra);
        List<Significado> significados = parser.parse(document);
        return scoreHeuristic.apply(significados, dominio);
    }
}
