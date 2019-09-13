package br.com.stilingue.lacio.significado;

import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.SIMPLE_STYLE;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.collect.Sets;

/**
 * @author bbviana
 */
public class Significado implements Serializable {

    private String texto;

    private Set<String> dominios;

    private int score;

    public Significado(String texto, Set<String> dominios) {
        this.texto = texto;
        this.dominios = ofNullable(dominios).orElse(new HashSet<>());
    }

    @Override
    public String toString() {
        return reflectionToString(this, SIMPLE_STYLE);
    }

    // ---

    public String getTexto() {
        return texto;
    }

    public Set<String> getDominios() {
        return dominios;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
