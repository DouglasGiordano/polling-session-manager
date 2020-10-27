package br.com.softdesign.douglasgiordano.pollingsessionmanager.exception;

/**
 * @author Douglas Giordano
 * Class Entity Not Found Exception
 */
public class EntityNotFoundException extends Exception {
    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}
