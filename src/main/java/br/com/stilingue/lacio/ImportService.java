package br.com.stilingue.lacio;

import static br.com.stilingue.lacio.Measurement.logTime;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author bbviana
 */
@Service
public class ImportService {

    @Autowired
    private Logger log;

    public Palavra fetch(String nomePalavra) {
        return logTime(() -> {
            String url = "https://dicionariocriativo.com.br/" + nomePalavra;

            Document document = silentConnect(url);

            Element mainContent = document.selectFirst("#mainContent");

            Palavra palavra = new Palavra(nomePalavra)
                    .setDivisaoSilabica(processDivisaoSilabica(mainContent))
                    .setClasseGramatical(processClasseGramatical(mainContent))
                    .setSignificados(processSignificados(mainContent))
                    .setSinonimos(processSinonimos(mainContent))
                    .setRelacionadas(processRelacionadas(mainContent))
                    .setExpressoes(processExpressoes(mainContent))
                    .setCitacoes(processCitacoes(mainContent));

            return palavra;
        }, "fetch(%s)", nomePalavra);
    }

    private static String processDivisaoSilabica(Element mainContent) {
        return mainContent
                .select("header:first-child small")
                .text();
    }

    private static String processClasseGramatical(Element mainContent) {
        return mainContent
                .select("#significado .auleteResult:first-child p:first-child")
                .text();
    }

    private static LinkedHashSet<String> processSignificados(Element mainContent) {
        return mainContent
                .select("#significado .auleteResult ul")
                .stream()
                .map(it -> it.select("a"))
                // FIXME tratar unicode Pr\u00f3spero
                .map(Elements::text)
                .collect(toCollection(LinkedHashSet::new));
    }

    private static List<GrupoSinonimos> processSinonimos(Element mainContent) {
        return mainContent
                .select("#sinant .contentList .contentListData")
                .stream()
                .map(it -> {
                    Elements sinonimosAntonimos = it.select("> p");

                    if (sinonimosAntonimos.isEmpty()) {
                        // FIXME filtrar nulos da lista
                        return null;
                    }

                    Set<String> sinonimos = sinonimosAntonimos.get(0)
                            .select("a")
                            .stream()
                            .map(Element::text)
                            .collect(toSet());

                    Set<String> antonimos = null;
                    if (sinonimosAntonimos.size() == 2) {
                        antonimos = sinonimosAntonimos.get(1)
                                .select("a")
                                .stream()
                                .map(Element::text)
                                .collect(toCollection(LinkedHashSet::new));
                    }

                    return new GrupoSinonimos(sinonimos, antonimos);
                })
                .collect(toList());
    }

    private static LinkedHashSet<String> processRelacionadas(Element mainContent) {
        return mainContent
                .select("#analogico .tags .analogico")
                .stream()
                // FIXME tratar unicode Pr\u00f3spero
                .map(Element::text)
                .collect(toCollection(LinkedHashSet::new));
    }

    private static Set<Expressao> processExpressoes(Element mainContent) {
        return mainContent
                .select("#expressoes .contentListData")
                .stream()
                .map(it -> {
                    String frase = it.selectFirst("> .bigger").text();
                    String significado = it.selectFirst("> p").text();
                    return new Expressao(frase, significado);
                })
                .collect(toSet());
    }

    private static Set<Citacao> processCitacoes(Element mainContent) {
        return mainContent
                .select("#citacoes .contentListData")
                .stream()
                .map(it -> {
                    String frase = it.selectFirst("> p").text();
                    String autor = it.selectFirst("> p.c_primary").text();
                    return new Citacao(frase, autor);
                })
                .collect(toSet());
    }

    // FIXME cache
    private static Document silentConnect(String url) {
        return logTime(() -> {
            try {
                return Jsoup.connect(url).get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, "Jsoup.connect(%s)", url);
    }
}
