package br.com.stilingue.lacio;

import java.io.Serializable;
import java.util.Set;

/**
 * @author bbviana
 */
public class GrupoSinonimos implements Serializable {

    private Set<String> sinonimos;

    private Set<String> antonimos;

    public GrupoSinonimos(Set<String> sinonimos) {
        this.sinonimos = sinonimos;
    }

    public GrupoSinonimos(Set<String> sinonimos, Set<String> antonimos) {
        this.sinonimos = sinonimos;
        this.antonimos = antonimos;
    }

    public Set<String> getSinonimos() {
        return sinonimos;
    }

    public Set<String> getAntonimos() {
        return antonimos;
    }
}
