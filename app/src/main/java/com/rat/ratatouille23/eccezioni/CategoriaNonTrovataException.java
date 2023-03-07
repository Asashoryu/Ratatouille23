package com.rat.ratatouille23.eccezioni;

public class CategoriaNonTrovataException extends PersonalizzaMenuException{
    public CategoriaNonTrovataException(String s) {
        super(s);
    }

    public CategoriaNonTrovataException() {
        super();
    }
}
