package vn.nev.tools.pcctool.common;

public class PcctoolException extends RuntimeException {

    public PcctoolException() {
        super();
    }

    public PcctoolException(String message) {
        super(message);
    }

    public PcctoolException(String message, Throwable cause) {
        super(message, cause);
    }

    public PcctoolException(Throwable cause) {
        super(cause);
    }

}
