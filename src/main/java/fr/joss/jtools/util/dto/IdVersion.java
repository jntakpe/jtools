package fr.joss.jtools.util.dto;

/**
 * POJO utilisé pour mettre à jour les ids et versions d'un formulaire
 *
 * @author jntakpe
 */
public class IdVersion {

    private final Long id;

    private final Integer version;

    public IdVersion(Long id, Integer version) {
        this.id = id;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public Integer getVersion() {
        return version;
    }
}
