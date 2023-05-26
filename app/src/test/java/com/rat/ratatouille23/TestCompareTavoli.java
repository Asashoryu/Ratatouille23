package com.rat.ratatouille23;

import org.junit.Test;
import static org.junit.Assert.*;

import com.rat.ratatouille23.eccezioni.rat.tavoli.IndiceNegativoException;
import com.rat.ratatouille23.repository.Repository;


public class TestCompareTavoli {

    Repository repository = Repository.getInstance();

    @Test(expected = IndiceNegativoException.class)
    public void testCompareTavoliException1() throws IndiceNegativoException {
        int indice1 = -2;
        int indice2 = -3;

        repository.compare(indice1,indice2);
    }

    @Test(expected = IndiceNegativoException.class)
    public void testCompareTavoliException2() throws IndiceNegativoException {
        int indice1 = -2;
        int indice2 = 3;

        repository.compare(indice1,indice2);
    }

    @Test(expected = IndiceNegativoException.class)
    public void testCompareTavoliException3() throws IndiceNegativoException {
        int indice1 = 2;
        int indice2 = -3;

        repository.compare(indice1,indice2);
    }

    @Test(expected = IndiceNegativoException.class)
    public void testCompareTavoliException4() throws IndiceNegativoException {
        int indice1 = 0;
        int indice2 = -3;

        repository.compare(indice1,indice2);
    }

    @Test(expected = IndiceNegativoException.class)
    public void testCompareTavoliException5() throws IndiceNegativoException {
        int indice1 = -2;
        int indice2 = 0;

        repository.compare(indice1,indice2);
    }

    //----------------------------------------------------------------------------------------------

    @Test
    public void testCompareTavoli_primoMaggioreSecondo() {
        int indice1 = 5;
        int indice2 = 3;
        int result;

        try {
            result = repository.compare(indice1,indice2);
            assertEquals(indice1-indice2,result);
        } catch (IndiceNegativoException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCompareTavoli_primoMinoreSecondo() {
        int indice1 = 3;
        int indice2 = 5;
        int result;

        try {
            result = repository.compare(indice1,indice2);
            assertEquals(indice1-indice2,result);
        } catch (IndiceNegativoException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCompareTavoli_primoUgualeSecondo() {
        int indice1 = 5;
        int indice2 = 5;
        int result;

        try {
            result = repository.compare(indice1,indice2);
            assertEquals(0,result);
        } catch (IndiceNegativoException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCompareTavoli_primoUgualeSecondoUguale0() {
        int indice1 = 0;
        int indice2 = 0;
        int result;

        try {
            result = repository.compare(indice1,indice2);
            assertEquals(0,result);
        } catch (IndiceNegativoException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCompareTavoli_primo0secondoPositivo() {
        int indice1 = 0;
        int indice2 = 5;
        int result;

        try {
            result = repository.compare(indice1,indice2);
            assertEquals(indice1-indice2,result);
        } catch (IndiceNegativoException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCompareTavoli_primoPositivoSecondo0() {
        int indice1 = 5;
        int indice2 = 0;
        int result;

        try {
            result = repository.compare(indice1,indice2);
            assertEquals(indice1,result);
        } catch (IndiceNegativoException e) {
            throw new RuntimeException(e);
        }
    }
}
