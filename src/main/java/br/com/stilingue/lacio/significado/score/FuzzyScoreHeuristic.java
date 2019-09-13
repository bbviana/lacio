package br.com.stilingue.lacio.significado.score;

import static java.util.Arrays.stream;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.text.similarity.FuzzyScore;

import br.com.stilingue.lacio.significado.Significado;
import br.com.stilingue.lacio.significado.score.ScoreHeuristic;

/**
 * Heurística usando alrgoritmo Fuzzy (que editores de texto usam) para comparação por semelhança.
 * Gerou muitos falsos negativos. Precisa de aprimoramentos.
 *
 * @author bbviana
 */
//@Component não funcionou bem
public class FuzzyScoreHeuristic implements ScoreHeuristic {

    @Override
    public List<Significado> apply(List<Significado> significados, String dominio) {
        FuzzyScore fuzzyScore = new FuzzyScore(new Locale("pt", "BR"));
        // FIXME recuperar
        List<String> relacionadasAoDominio = new ArrayList<>();

        return significados
                .stream()
                .peek(significado -> {
                    int score = 0;

                    List<String> parts = stream(significado.getTexto().split(" "))
                            .map(String::trim)
                            .map(it -> it.replaceAll("[().,;-]", ""))
                            .map(it -> it.replaceAll("<.+>", ""))
                            .map(String::toLowerCase)
                            .filter(it -> it.length() >= 4)
                            .collect(toList());

                    for (String part : parts) {
                        for (String relacionada : relacionadasAoDominio) {
                            Integer fScore = fuzzyScore.fuzzyScore(part, relacionada);
                            if (fScore >= 10) {
                                score += fScore;
                            }
                        }
                    }

                    significado.setScore(score);
                })
                .sorted(comparing(Significado::getScore).reversed())
                .collect(toList());
    }
}
