package br.com.stilingue.lacio


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

/**
 * Este teste de integração é frágil. Ele depende das fontes de dados estarem no ar (sites)
 * e que retornem sempre o mesmo resultado.
 * O foco aqui era mais mostrar como o Spock Framework (e Groovy) é legal.
 *
 * @author bbviana
 */
@SpringBootTest
class PalavrasServiceSpec extends Specification {

    @Autowired
    private PalavrasService palavrasService;

    def "confere os 10 1os domínios conceituais para 'gato'"() {
        when:
        def dominios = palavrasService.dominios("gato", 1)

        then:
        dominios != null
        dominios.sort() == [
                "animal",
                "beleza",
                "biologia",
                "construção",
                "esporte",
                "futebol",
                "macho",
                "marinha",
                "resto",
                "vínculo",
        ]
    }

    def "confere as 10 1as palavras relacionadas a 'gato' no domínio conceitual 'futebol'"() {
        when:
        def relcionadas = palavrasService.relacionadas("futebol", "gato", 1)

        then:
        relcionadas != null
        relcionadas.sort() == [
                "defesa",
                "ferrolho",
                "gato",
                "lateral",
                "lateral-direito",
                "lateral-esquerdo",
                "ponte",
                "retranca",
                "zaga",
                "zagueiro",
        ]
    }

}
