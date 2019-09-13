package br.com.stilingue.lacio.conceito;

import static java.util.Collections.emptyList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * @author bbviana
 */
@Component
public class ConceitoFactory {

    @Autowired
    private List<Conceito> conceitos = emptyList();

    @Cacheable("conceitos")
    @SuppressWarnings("unchecked")
    public <T> Conceito<T> lookup(String conceito) {
        return conceitos
                .stream()
                .filter(it -> {
                    String nomeConceito = it.getClass().getSimpleName();
                    return nomeConceito.equals(conceito);
                })
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
