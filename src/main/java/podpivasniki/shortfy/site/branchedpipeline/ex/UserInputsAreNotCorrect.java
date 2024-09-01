package podpivasniki.shortfy.site.branchedpipeline.ex;

public class UserInputsAreNotCorrect extends RuntimeException{
    public UserInputsAreNotCorrect() {
        super();
    }

    public UserInputsAreNotCorrect(String message) {
        super(message);
    }

    public UserInputsAreNotCorrect(String message, Throwable cause) {
        super(message, cause);
    }
}
