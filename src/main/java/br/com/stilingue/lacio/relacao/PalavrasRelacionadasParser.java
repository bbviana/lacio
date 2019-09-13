package br.com.stilingue.lacio.relacao;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toCollection;

import java.util.LinkedHashSet;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author bbviana
 */
@Component
public class PalavrasRelacionadasParser {

    @Value("${import.result.limit}")
    private int resultLimit;

    public LinkedHashSet<String> parse(Document document, Integer page) {
        int resolvedPage = ofNullable(page).orElse(1);
        long resolvedLimit = page == null ? Long.MAX_VALUE : resultLimit;

        return document
                .select("#mainContent #contentList ul:not(.loadmore) a")
                .stream()
                .map(Element::text)
                .map(String::trim)
                .filter(it -> !"".equals(it))
                .filter(it -> it.length() > 1)
                .skip((resolvedPage - 1) * resolvedLimit)
                .limit(resolvedLimit)
                .collect(toCollection(LinkedHashSet::new));
    }
}
