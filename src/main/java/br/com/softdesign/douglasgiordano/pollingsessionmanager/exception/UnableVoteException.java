package br.com.softdesign.douglasgiordano.pollingsessionmanager.exception;

/**
 * @author Douglas Giordano
 * Class Unable Vote Exception
 */
public class UnableVoteException extends Exception {
    public UnableVoteException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnableVoteException(String message) {
        super(message);
    }
}
