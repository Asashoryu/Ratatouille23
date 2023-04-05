package com.rat.ratatouille23.eccezioni.rat.menu;

public class CategoriaNonTrovataException extends PersonalizzaMenuException {
    public CategoriaNonTrovataException(String s) {
        super(s);
    }

    public CategoriaNonTrovataException() {
        super();
    }
}
