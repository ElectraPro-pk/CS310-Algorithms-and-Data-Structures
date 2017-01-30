package cs310;

/**
 * Created by Michael Hoang on 12/2/2014.
 */
public class HasCycleException extends Exception {
    public HasCycleException() { super(); }
    public HasCycleException(String message) { super(message); }
    public HasCycleException(String message, Throwable cause) { super(message, cause); }
    public HasCycleException(Throwable cause) { super(cause); }
}