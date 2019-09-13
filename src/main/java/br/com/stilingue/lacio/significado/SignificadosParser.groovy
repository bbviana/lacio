package br.com.stilingue.lacio.significado


import groovy.json.JsonSlurper
import org.jsoup.nodes.Document
import org.springframework.stereotype.Component

/**
 * Groovy é excelente para navegação em JSON e XML.
 * Consegue ser melhor até que Javascript, que tem JSON como notação nativa.
 * Por isso decidimos implementar esse parser em Groovy.
 *
 * @author bbviana
 */
@Component
class SignificadosParser {

    List<Significado> parse(Document document) {
        String jsonAsString = document.body().text();

        def cleaned = jsonAsString.replaceFirst("\\)]}'", "")
        def json = new JsonSlurper().parseText(cleaned)
        def senses = json
                .dictw_async
                .payload
                .single_results
                .entry
                .sense_families
                .senses
                .flatten() as List

        def significados = senses.collect {
            new Significado(it.definition?.text, it.label_set?.subject as Set)
        }

        return significados
    }
}
