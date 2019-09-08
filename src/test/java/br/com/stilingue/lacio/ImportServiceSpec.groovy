package br.com.stilingue.lacio


import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static groovy.json.JsonOutput.prettyPrint
import static groovy.json.JsonOutput.toJson

/**
 * @author bbviana
 */
//@RunWith(SpringRunner.class)
@SpringBootTest
class ImportServiceSpec extends Specification {

    @Autowired
    private ImportService importService;

    def "Fetch"() {
        expect:
        importService != null

        when:
        def palavra = importService.fetch("estilingue")

        then:
        palavra != null
    }


    public static void main(String[] args) {
        Logger log = LoggerFactory.getLogger(LacioApplication)
        ImportService service = new ImportService(log: log)

        def palavra = service.fetch("música")
        println prettyPrint(toJson(palavra))
    }
}
