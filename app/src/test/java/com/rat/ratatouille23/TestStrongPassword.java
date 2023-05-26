package com.rat.ratatouille23;

import org.junit.Test;
import static org.junit.Assert.*;

import com.rat.ratatouille23.eccezioni.rat.login.PasswordNonForteException;
import com.rat.ratatouille23.viewmodel.ReimpostaPasswordViewModel;

public class TestStrongPassword {

    ReimpostaPasswordViewModel viewModel = new ReimpostaPasswordViewModel();

    @Test
    public void testIsStrongPassword_Valid() {
        // Caso di test: password forte
        String password = "Abc123!@#";
        String caratteriSpeciali = "!@#";
        int numeroCaratteriSpeciali = 3;

        try {
            boolean result = viewModel.isStrongPassword(password, caratteriSpeciali, numeroCaratteriSpeciali);
            assertTrue(result);
        } catch (PasswordNonForteException e) {
            fail("Eccezione non prevista");
        }
    }

    @Test
    public void  testIsStrongPassword_CaratteriSpecialiNulli() {
        String password = "ciao@@+";
        String caratteriSpeciali = "@+!";
        int numeroCaratteriSpeciali = 0;
        boolean result;

        try {
            result = viewModel.isStrongPassword(password,caratteriSpeciali,numeroCaratteriSpeciali);
            assertTrue(result);
        } catch (PasswordNonForteException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void  testIsStrongPassword_piuCaratteriSpeciali() {
        String password = "ciao@@+";
        String caratteriSpeciali = "@+!";
        int numeroCaratteriSpeciali = 2;
        boolean result;

        try {
            result = viewModel.isStrongPassword(password,caratteriSpeciali,numeroCaratteriSpeciali);
            assertTrue(result);
        } catch (PasswordNonForteException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void  testIsStrongPassword_noCaratteriSpeciali() {
        String password = "ciao";
        String caratteriSpeciali = "@+!";
        int numeroCaratteriSpeciali = 0;
        boolean result;

        try {
            result = viewModel.isStrongPassword(password,caratteriSpeciali,numeroCaratteriSpeciali);
            assertTrue(result);
        } catch (PasswordNonForteException e) {
            throw new RuntimeException(e);
        }
    }

    //--------------------------------------------------------------------------------------------------------------------------

    @Test
    public void  testIsStrongPassword_menoCaratteriSpeciali() {
        String password = "ciao@@+";
        String caratteriSpeciali = "@+!";
        int numeroCaratteriSpeciali = 4;
        boolean result;

        try {
            result = viewModel.isStrongPassword(password,caratteriSpeciali,numeroCaratteriSpeciali);
            assertFalse(result);
        } catch (PasswordNonForteException e) {
            throw new RuntimeException(e);
        }
    }

    //--------------------------------------------------------------------------------------------------------------------

    @Test(expected = PasswordNonForteException.class)
    public void testIsStrongPassword_Exception() throws PasswordNonForteException {
        // Caso di test: eccezione generata
        String password = null;
        String caratteriSpeciali = "!@#";
        int numeroCaratteriSpeciali = 3;

        viewModel.isStrongPassword(password, caratteriSpeciali, numeroCaratteriSpeciali);
    }

    @Test(expected = PasswordNonForteException.class)
    public void testIsStrongPassword_Exception2() throws PasswordNonForteException {
        // Caso di test: eccezione generata
        String password = "null";
        String caratteriSpeciali = null;
        int numeroCaratteriSpeciali = 3;

        viewModel.isStrongPassword(password, caratteriSpeciali, numeroCaratteriSpeciali);
    }

    @Test(expected = PasswordNonForteException.class)
    public void testIsStrongPassword_Exception3() throws PasswordNonForteException {
        // Caso di test: eccezione generata
        String password = "null";
        String caratteriSpeciali = "!@#";
        int numeroCaratteriSpeciali = -3;

        viewModel.isStrongPassword(password, caratteriSpeciali, numeroCaratteriSpeciali);
    }

    @Test(expected = PasswordNonForteException.class)
    public void testIsStrongPassword_Exception4() throws PasswordNonForteException {
        // Caso di test: eccezione generata
        String password = null;
        String caratteriSpeciali = null;
        int numeroCaratteriSpeciali = -3;

        viewModel.isStrongPassword(password, caratteriSpeciali, numeroCaratteriSpeciali);
    }

    @Test(expected = PasswordNonForteException.class)
    public void testIsStrongPassword_Exception5() throws PasswordNonForteException {
        // Caso di test: eccezione generata
        String password = null;
        String caratteriSpeciali = null;
        int numeroCaratteriSpeciali = 3;

        viewModel.isStrongPassword(password, caratteriSpeciali, numeroCaratteriSpeciali);
    }

    @Test(expected = PasswordNonForteException.class)
    public void testIsStrongPassword_Exception6() throws PasswordNonForteException {
        // Caso di test: eccezione generata
        String password = null;
        String caratteriSpeciali = null;
        int numeroCaratteriSpeciali = 0;

        viewModel.isStrongPassword(password, caratteriSpeciali, numeroCaratteriSpeciali);
    }

    @Test(expected = PasswordNonForteException.class)
    public void testIsStrongPassword_Exception7() throws PasswordNonForteException {
        // Caso di test: eccezione generata
        String password = null;
        String caratteriSpeciali = "@@@";
        int numeroCaratteriSpeciali = -3;

        viewModel.isStrongPassword(password, caratteriSpeciali, numeroCaratteriSpeciali);
    }

    @Test(expected = PasswordNonForteException.class)
    public void testIsStrongPassword_Exception8() throws PasswordNonForteException {
        // Caso di test: eccezione generata
        String password = "null";
        String caratteriSpeciali = null;
        int numeroCaratteriSpeciali = -3;

        viewModel.isStrongPassword(password, caratteriSpeciali, numeroCaratteriSpeciali);
    }
}
