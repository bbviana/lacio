package br.com.stilingue.lacio.conceito;

/**
 * @author bbviana
 */
public class ConceitoContext {
    private String dominio;
    private String palavra;
    private Integer page;

    public static ConceitoContext ctx() {
        return new ConceitoContext();
    }

    // ---

    public String getDominio() {
        return dominio;
    }

    public ConceitoContext dominio(String dominio) {
        this.dominio = dominio;
        return this;
    }

    public String getPalavra() {
        return palavra;
    }

    public ConceitoContext palavra(String palavra) {
        this.palavra = palavra;
        return this;
    }

    public Integer getPage() {
        return page;
    }

    public ConceitoContext page(Integer page) {
        this.page = page;
        return this;
    }
}
