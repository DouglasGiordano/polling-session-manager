package br.com.softdesign.douglasgiordano.pollingsessionmanager.exception;

public class InvalidCpfException extends Exception{
    public InvalidCpfException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCpfException(String message) {
        super(message);
    }
}
