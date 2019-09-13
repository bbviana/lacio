package br.com.stilingue.lacio;

import static br.com.stilingue.lacio.Measurement.logTime;
import static br.com.stilingue.lacio.conceito.ConceitoContext.ctx;

import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.stilingue.lacio.conceito.Conceito;
import br.com.stilingue.lacio.conceito.ConceitoFactory;
import br.com.stilingue.lacio.resumo.Palavra;
import br.com.stilingue.lacio.significado.Significado;

/**
 * @author bbviana
 */
@Service
public class PalavrasService {

    @Autowired
    private ConceitoFactory conceitoFactory;

    /**
     * @param page 1-based
     */
    public LinkedHashSet<String> dominios(String palavra, int page) {
        Conceito<LinkedHashSet<String>> dominios = conceitoFactory.lookup("Dominios");
        return logTime(
                () -> dominios.get(ctx().palavra(palavra).page(page)),
                "dominio(%s)", palavra);
    }

    public LinkedHashSet<String> relacionadas(String dominio, String palavra) {
        return relacionadas(dominio, palavra, null);
    }

    /**
     * @param page 1-based
     */
    public LinkedHashSet<String> relacionadas(String dominio, String palavra, Integer page) {
        Conceito<LinkedHashSet<String>> palavrasRelacionadas = conceitoFactory.lookup("PalavrasRelacionadas");
        return logTime(
                () -> palavrasRelacionadas.get(ctx().dominio(dominio).palavra(palavra).page(page)),
                "relacionadas(%s, %s)", dominio, palavra);
    }

    public List<Significado> significados(String dominio, String palavra) {
        Conceito<List<Significado>> significados = conceitoFactory.lookup("Significados");
        return logTime(
                () -> significados.get(ctx().dominio(dominio).palavra(palavra)),
                "significados(%s, %s)", dominio, palavra);
    }

    public Palavra resumo(String palavra) {
        Conceito<Palavra> resumo = conceitoFactory.lookup("Resumo");
        return logTime(
                () -> resumo.get(ctx().palavra(palavra)),
                "resumo(%s)", palavra);
    }
}
