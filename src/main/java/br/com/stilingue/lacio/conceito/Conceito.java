package br.com.stilingue.lacio.conceito;

/**
 * @author bbviana
 */
public interface Conceito<T> {

    T get(ConceitoContext ctx);
}
