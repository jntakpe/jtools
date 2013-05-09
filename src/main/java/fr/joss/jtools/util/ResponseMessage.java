package fr.joss.jtools.util;

/**
 * Réponse renvoyée par l'application aux requêtes notamment aux requêtes AJAX.
 * Peut aussi être ajouté à un objet ModelAndView.
 *
 * @author jntakpe
 */
public class ResponseMessage {

    private final String message;

    private final boolean success;

    private final Object data;

    private ResponseMessage(String message, boolean success, Object data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public static ResponseMessage getSuccessMessage() {
        return new ResponseMessage(null, true, null);
    }

    public static ResponseMessage getSuccessMessage(String message) {
        return new ResponseMessage(message, true, null);
    }

    public static ResponseMessage getSuccessMessage(String message, Object data) {
        return new ResponseMessage(message, true, data);
    }

    public static ResponseMessage getErrorMessage(String message) {
        return new ResponseMessage("Erreur. " + message, false, null);
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public Object getData() {
        return data;
    }
}
