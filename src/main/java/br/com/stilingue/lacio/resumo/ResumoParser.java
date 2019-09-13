package br.com.stilingue.lacio.resumo;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

/**
 * @author bbviana
 */
@Component
public class ResumoParser {

    public Palavra parse(Document document, String palavra) {
        Element mainContent = document.selectFirst("#mainContent");
        return new Palavra(palavra)
                .setDivisaoSilabica(processDivisaoSilabica(mainContent))
                .setClasseGramatical(processClasseGramatical(mainContent))
                .setSignificados(processSignificados(mainContent))
                .setSinonimos(processSinonimos(mainContent))
                .setRelacionadas(processRelacionadas(mainContent))
                .setExpressoes(processExpressoes(mainContent))
                .setCitacoes(processCitacoes(mainContent));
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
                .map(Element::text)
                .sorted()
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
}
