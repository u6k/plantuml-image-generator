
package me.u6k.plantuml_image_generator.exception;

public class ReadWebResourceException extends RuntimeException {

    public ReadWebResourceException() {
        super();
    }

    public ReadWebResourceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ReadWebResourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReadWebResourceException(String message) {
        super(message);
    }

    public ReadWebResourceException(Throwable cause) {
        super(cause);
    }

}
