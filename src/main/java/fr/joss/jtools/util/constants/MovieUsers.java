package fr.joss.jtools.util.constants;

/**
 * Enumération des personnes utilisant la liste des films
 *
 * @author jntakpe
 */
public enum MovieUsers {

    AMELIA("Amelia"),
    CHARLES("Charles"),
    JULIEN("Julien"),
    JOCELYN("Jocelyn");

    private final String display;

    private MovieUsers(String display) {
        this.display = display;
    }

    public String getDisplay() {
        return display;
    }
}
