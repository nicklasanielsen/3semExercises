package exceptions;

/**
 *
 * @author Nicklas Nielsen
 */
public class PersonNotFoundException extends Exception {

    public PersonNotFoundException(String message) {
        super(message);
    }
}
