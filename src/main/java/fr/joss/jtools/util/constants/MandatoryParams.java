package fr.joss.jtools.util.constants;

/**
 * Enumération des paramètres obligatoires
 *
 * @author jntakpe
 */
public enum MandatoryParams {

    SMTP_HOST("smtp.host"),
    SMTP_PORT("smtp.port"),
    SMTP_FROM("smtp.from");


    private final String key;

    private MandatoryParams(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
