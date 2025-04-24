package Exceptions;

public class MaxIterationException extends Exception {

    @Override
    public String toString(){
        return "Nombre maximum d'itérations atteint.";
    }
}
