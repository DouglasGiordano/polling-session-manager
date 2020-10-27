package br.com.softdesign.douglasgiordano.pollingsessionmanager.exception;


/**
 * @author Douglas Giordano
 * Class Voting Closed Exception
 */
public class VotingClosedException extends Exception {
    public VotingClosedException(String message, Throwable cause) {
        super(message, cause);
    }

    public VotingClosedException(String message) {
        super(message);
    }
}
