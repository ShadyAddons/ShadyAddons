package cheaters.get.banned.config;

// Fix yo types, yo
public class TypeMismatchException extends RuntimeException {

    public TypeMismatchException(String expected, String given) {
        super("Expected " + expected + ", got " + given);
    }

}