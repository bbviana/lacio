package br.com.stilingue.lacio;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author bbviana
 */
public class Expressao implements Serializable {

    private String frase;

    private String significado;

    public Expressao(String frase, String significado) {
        this.frase = frase;
        this.significado = significado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expressao expressao = (Expressao) o;
        return Objects.equals(frase, expressao.frase) &&
                Objects.equals(significado, expressao.significado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(frase, significado);
    }

    // ---

    public String getFrase() {
        return frase;
    }

    public String getSignificado() {
        return significado;
    }
}
