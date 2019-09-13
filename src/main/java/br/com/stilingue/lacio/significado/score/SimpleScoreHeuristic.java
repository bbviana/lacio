package br.com.stilingue.lacio.significado.score;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.stereotype.Component;

import br.com.stilingue.lacio.significado.Significado;
import br.com.stilingue.lacio.significado.score.ScoreHeuristic;

/**
 * Heurística simples para cálculo de score de um significado.
 * Dada uma lista de significados e um domínio conceitual:
 * 1) Se dentre os domínios do significado estiver o domínio especificado: 10 pontos
 * 2) Se no texto do significado estiver o domínio especificado: 5 pontos
 *
 * Por ex:
 * Algumas definições para "terra", segundo o Google dictionary:
 *
 * 1. ASTRONOMIA (domínio)
 * planeta do sistema solar, o terceiro quanto à proximidade do Sol, habitado pelo homem ☞ inicial maiúsc.
 *
 * 3.
 * área ou região não especificada; local, região, território.
 *
 * Se passarmos o domínio ASTRONOMIA, o significado 1 tem score 10 (match no domínio)
 * e 3 tem score 0.
 * Se o domínio for REGIÃO, 1 tem score 0 e 3 tem score 5 (match no texto).
 *
 *
 * Essa heurística é bem simples. Apresentou muito poucos falsos negativos, mas não é precisa.
 * Deixa passar várias inferêmcias de signifciado.
 * Por ex, ela não é capaz de associar o significado abaixo "gato", domínio "beleza":
 *
 * 8. INFORMAL•BRASILEIRISMO
 * rapaz ou homem muito atraente; gatão.
 *
 * Como o significado não traz "Beleza" no texto e nem no domínio, a heurística não é capaz
 * de inferir.
 *
 * @author bbviana
 */
@Component
public class SimpleScoreHeuristic implements ScoreHeuristic {

    @Override
    public List<Significado> apply(List<Significado> significados, String dominio) {
        return significados
                .stream()
                .peek(significado -> {
                    int score = 0;
                    boolean algumDominioDoSignificadoDaMatchComODominioEspecificado =
                            significado
                                    .getDominios()
                                    .stream()
                                    // contains se mostrou mais assertivo que equals
                                    .anyMatch(it -> it.contains(dominio));

                    if (algumDominioDoSignificadoDaMatchComODominioEspecificado) {
                        score += 10;
                    }

                    if (significado.getTexto().contains(dominio)) {
                        score += 5;
                    }

                    // 10 e 5 são valores arbitrários: funcionaram bem, mas é preciso mais testes

                    significado.setScore(score);
                })
                .sorted(comparing(Significado::getScore).reversed())
                .collect(toList());
    }
}
