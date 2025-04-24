package Exceptions;

public class IdenticalElementsException extends Exception {

    @Override
    public String toString() {
        return "Trop d'éléments identiques.";
    }
}
