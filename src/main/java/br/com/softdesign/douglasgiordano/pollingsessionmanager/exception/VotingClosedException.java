package br.com.softdesign.douglasgiordano.pollingsessionmanager.exception;

public class VotingClosedException extends Exception{
    public VotingClosedException(String message, Throwable cause) {
        super(message, cause);
    }

    public VotingClosedException(String message) {
        super(message);
    }
}
