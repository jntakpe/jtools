package fr.joss.jtools.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

/**
 * Entité représentant un paramètre
 *
 * @author jntakpe
 */
@Entity
@SequenceGenerator(name = "SG", sequenceName = "SEQ_PARAMETER")
public class Parameter extends GenericDomain {

    @Column(nullable = false, unique = true)
    private String key;

    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Parameter)) return false;

        Parameter parameter = (Parameter) o;

        return key.equals(parameter.key);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    @Override
    public String toString() {
        return key;
    }
}
