package br.com.softdesign.douglasgiordano.pollingsessionmanager.exception;

public class UnableVoteException extends Exception{
    public UnableVoteException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnableVoteException(String message) {
        super(message);
    }
}
