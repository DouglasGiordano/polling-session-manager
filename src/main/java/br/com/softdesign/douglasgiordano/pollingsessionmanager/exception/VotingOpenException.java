package br.com.softdesign.douglasgiordano.pollingsessionmanager.exception;

public class VotingOpenException extends Exception{
    public VotingOpenException(String message, Throwable cause) {
        super(message, cause);
    }

    public VotingOpenException(String message) {
        super(message);
    }
}
