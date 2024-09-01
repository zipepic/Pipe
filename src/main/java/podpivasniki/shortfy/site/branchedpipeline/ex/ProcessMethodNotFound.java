package podpivasniki.shortfy.site.branchedpipeline.ex;

public class ProcessMethodNotFound extends RuntimeException{
    public ProcessMethodNotFound() {
        super();
    }

    public ProcessMethodNotFound(String message) {
        super(message);
    }

    public ProcessMethodNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
