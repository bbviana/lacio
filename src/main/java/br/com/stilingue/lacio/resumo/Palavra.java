package br.com.stilingue.lacio.resumo;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author bbviana
 */
public class Palavra implements Serializable {

    private String nome;

    private String classeGramatical;

    private String divisaoSilabica;

    // ---

    private LinkedHashSet<String> relacionadas;

    private List<GrupoSinonimos> sinonimos;

    // ---

    private LinkedHashSet<String> significados;

    private Set<Expressao> expressoes;

    private Set<Citacao> citacoes;

    public Palavra(String nome) {
        this.nome = nome;
    }

    // ---

    public String getNome() {
        return nome;
    }

    public String getClasseGramatical() {
        return classeGramatical;
    }

    public Palavra setClasseGramatical(String classeGramatical) {
        this.classeGramatical = classeGramatical;
        return this;
    }

    public String getDivisaoSilabica() {
        return divisaoSilabica;
    }

    public Palavra setDivisaoSilabica(String divisaoSilabica) {
        this.divisaoSilabica = divisaoSilabica;
        return this;
    }

    public LinkedHashSet<String> getRelacionadas() {
        return relacionadas;
    }

    public Palavra setRelacionadas(LinkedHashSet<String> relacionadas) {
        this.relacionadas = relacionadas;
        return this;
    }

    public List<GrupoSinonimos> getSinonimos() {
        return sinonimos;
    }

    public Palavra setSinonimos(List<GrupoSinonimos> sinonimos) {
        this.sinonimos = sinonimos;
        return this;
    }

    public LinkedHashSet<String> getSignificados() {
        return significados;
    }

    public Palavra setSignificados(LinkedHashSet<String> significados) {
        this.significados = significados;
        return this;
    }

    public Set<Expressao> getExpressoes() {
        return expressoes;
    }

    public Palavra setExpressoes(Set<Expressao> expressoes) {
        this.expressoes = expressoes;
        return this;
    }

    public Set<Citacao> getCitacoes() {
        return citacoes;
    }

    public Palavra setCitacoes(Set<Citacao> citacoes) {
        this.citacoes = citacoes;
        return this;
    }
}
