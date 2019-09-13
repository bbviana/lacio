package br.com.stilingue.lacio.significado.score;

import java.util.List;

import br.com.stilingue.lacio.significado.Significado;

/**
 * Dada uma lista de significados, aplica um score a cada elemento de acordo com o domínio
 * conceitual passado.
 *
 * @author bbviana
 */
public interface ScoreHeuristic {

    List<Significado> apply(List<Significado> significados, String dominio);
}
