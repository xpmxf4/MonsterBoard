package HailYoungHan.Board.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String message) {
        super(message);
    }

    public PostNotFoundException(Long postId) {
        super("No such postId : " + postId);
    }
}
