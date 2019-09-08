package br.com.stilingue.lacio;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author bbviana
 */
public class Citacao implements Serializable {
    private String frase;
    private String autor;

    public Citacao(String frase, String autor) {
        this.frase = frase;
        this.autor = autor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Citacao citacao = (Citacao) o;
        return Objects.equals(frase, citacao.frase) &&
                Objects.equals(autor, citacao.autor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(frase, autor);
    }

    // ---

    public String getFrase() {
        return frase;
    }

    public String getAutor() {
        return autor;
    }
}
