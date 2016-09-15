package Exceptions;

/**
 * Created by ovidiudanielbarba on 16/08/16.
 */
public class AlreadyPresentInDbException extends Exception {

    public AlreadyPresentInDbException() {
        super("Value already present in DB");
    }
}
