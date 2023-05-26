package com.rat.ratatouille23;

import org.junit.Test;
import static org.junit.Assert.*;

import com.rat.ratatouille23.eccezioni.rat.login.ReimpostaPasswordException;
import com.rat.ratatouille23.viewmodel.ReimpostaPasswordViewModel;

public class TestReimpostaPassword {

    ReimpostaPasswordViewModel viewModel = new ReimpostaPasswordViewModel();

    @Test(expected = ReimpostaPasswordException.class)
    public void testReimpostaPasswordEccezione1() throws ReimpostaPasswordException {
        String vecchiaPassword = null;
        String nuovaPassword = null;
        String confermaPassword = null;

        viewModel.reimpostaPasswordNuova(vecchiaPassword,nuovaPassword,confermaPassword);
    }

    @Test(expected = ReimpostaPasswordException.class)
    public void testReimpostaPasswordEccezione2() throws ReimpostaPasswordException {
        String vecchiaPassword = "null";
        String nuovaPassword = null;
        String confermaPassword = null;

        viewModel.reimpostaPasswordNuova(vecchiaPassword,nuovaPassword,confermaPassword);
    }

    @Test(expected = ReimpostaPasswordException.class)
    public void testReimpostaPasswordEccezione3() throws ReimpostaPasswordException {
        String vecchiaPassword = null;
        String nuovaPassword = "null";
        String confermaPassword = null;

        viewModel.reimpostaPasswordNuova(vecchiaPassword,nuovaPassword,confermaPassword);
    }

    @Test(expected = ReimpostaPasswordException.class)
    public void testReimpostaPasswordEccezione4() throws ReimpostaPasswordException {
        String vecchiaPassword = null;
        String nuovaPassword = null;
        String confermaPassword = "null";

        viewModel.reimpostaPasswordNuova(vecchiaPassword,nuovaPassword,confermaPassword);
    }

    @Test(expected = ReimpostaPasswordException.class)
    public void testReimpostaPasswordEccezione5() throws ReimpostaPasswordException {
        String vecchiaPassword = "null";
        String nuovaPassword = "null";
        String confermaPassword = null;

        viewModel.reimpostaPasswordNuova(vecchiaPassword,nuovaPassword,confermaPassword);
    }

    @Test(expected = ReimpostaPasswordException.class)
    public void testReimpostaPasswordEccezione6() throws ReimpostaPasswordException {
        String vecchiaPassword = null;
        String nuovaPassword = "null";
        String confermaPassword = "null";

        viewModel.reimpostaPasswordNuova(vecchiaPassword,nuovaPassword,confermaPassword);
    }

    @Test(expected = ReimpostaPasswordException.class)
    public void testReimpostaPasswordEccezione7() throws ReimpostaPasswordException {
        String vecchiaPassword = "null";
        String nuovaPassword = null;
        String confermaPassword = "null";

        viewModel.reimpostaPasswordNuova(vecchiaPassword,nuovaPassword,confermaPassword);
    }

    @Test(expected = ReimpostaPasswordException.class)
    public void testReimpostaPasswordEccezione8() throws ReimpostaPasswordException {
        String vecchiaPassword = "null";
        String nuovaPassword = "nulla";
        String confermaPassword = null;

        viewModel.reimpostaPasswordNuova(vecchiaPassword,nuovaPassword,confermaPassword);
    }

    @Test(expected = ReimpostaPasswordException.class)
    public void testReimpostaPasswordEccezione9() throws ReimpostaPasswordException {
        String vecchiaPassword = null;
        String nuovaPassword = "null";
        String confermaPassword = "nulla";

        viewModel.reimpostaPasswordNuova(vecchiaPassword,nuovaPassword,confermaPassword);
    }

    @Test(expected = ReimpostaPasswordException.class)
    public void testReimpostaPasswordEccezione10() throws ReimpostaPasswordException {
        String vecchiaPassword = "nulla";
        String nuovaPassword = null;
        String confermaPassword = "null";

        viewModel.reimpostaPasswordNuova(vecchiaPassword,nuovaPassword,confermaPassword);
    }

    //------------------------------------------------------------------------------------------------

    @Test
    public void testReimpostaPasswordTutteStringheVuote() {
        String vecchiaPassword = "";
        String nuovaPassword = "";
        String confermaPassword = "";
        boolean result;

        try {
            result = viewModel.reimpostaPasswordNuova(vecchiaPassword,nuovaPassword,confermaPassword);
            assertFalse(result);
        } catch (ReimpostaPasswordException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testReimpostaPasswordPrimaStringaVuota1() {
        String vecchiaPassword = "";
        String nuovaPassword = "null";
        String confermaPassword = "null";
        boolean result;

        try {
            result = viewModel.reimpostaPasswordNuova(vecchiaPassword,nuovaPassword,confermaPassword);
            assertFalse(result);
        } catch (ReimpostaPasswordException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testReimpostaPasswordPrimaStringaVuota2() {
        String vecchiaPassword = "";
        String nuovaPassword = "null";
        String confermaPassword = "llun";
        boolean result;

        try {
            result = viewModel.reimpostaPasswordNuova(vecchiaPassword,nuovaPassword,confermaPassword);
            assertFalse(result);
        } catch (ReimpostaPasswordException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testReimpostaPasswordSecondaStringaVuota1() {
        String vecchiaPassword = "null";
        String nuovaPassword = "";
        String confermaPassword = "null";
        boolean result;

        try {
            result = viewModel.reimpostaPasswordNuova(vecchiaPassword,nuovaPassword,confermaPassword);
            assertFalse(result);
        } catch (ReimpostaPasswordException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testReimpostaPasswordSecondaStringaVuota2() {
        String vecchiaPassword = "null";
        String nuovaPassword = "";
        String confermaPassword = "pippo";
        boolean result;

        try {
            result = viewModel.reimpostaPasswordNuova(vecchiaPassword,nuovaPassword,confermaPassword);
            assertFalse(result);
        } catch (ReimpostaPasswordException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testReimpostaPasswordTerzaStringaVuota1() {
        String vecchiaPassword = "null";
        String nuovaPassword = "null";
        String confermaPassword = "";
        boolean result;

        try {
            result = viewModel.reimpostaPasswordNuova(vecchiaPassword,nuovaPassword,confermaPassword);
            assertFalse(result);
        } catch (ReimpostaPasswordException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testReimpostaPasswordTerzaStringaVuota2() {
        String vecchiaPassword = "null";
        String nuovaPassword = "pippo";
        String confermaPassword = "";
        boolean result;

        try {
            result = viewModel.reimpostaPasswordNuova(vecchiaPassword,nuovaPassword,confermaPassword);
            assertFalse(result);
        } catch (ReimpostaPasswordException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testReimpostaPasswordVecchiaUgualeNuova1() {
        String vecchiaPassword = "null";
        String nuovaPassword = "null";
        String confermaPassword = "pippo";
        boolean result;

        try {
            result = viewModel.reimpostaPasswordNuova(vecchiaPassword,nuovaPassword,confermaPassword);
            assertFalse(result);
        } catch (ReimpostaPasswordException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testReimpostaPasswordVecchiaUgualeNuova2() {
        String vecchiaPassword = "null";
        String nuovaPassword = "null";
        String confermaPassword = "null";
        boolean result;

        try {
            result = viewModel.reimpostaPasswordNuova(vecchiaPassword,nuovaPassword,confermaPassword);
            assertFalse(result);
        } catch (ReimpostaPasswordException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testReimpostaPasswordNuovaDiversaConferma() {
        String vecchiaPassword = "pippo";
        String nuovaPassword = "null";
        String confermaPassword = "pippo";
        boolean result;

        try {
            result = viewModel.reimpostaPasswordNuova(vecchiaPassword,nuovaPassword,confermaPassword);
            assertFalse(result);
        } catch (ReimpostaPasswordException e) {
            throw new RuntimeException(e);
        }
    }

    //-------------------------------------------------------------------------------------------------------

    @Test
    public void testReimpostaPasswordCorretto() {
        String vecchiaPassword = "pippo";
        String nuovaPassword = "null";
        String confermaPassword = "null";
        boolean result;

        try {
            result = viewModel.reimpostaPasswordNuova(vecchiaPassword,nuovaPassword,confermaPassword);
            assertTrue(result);
        } catch (ReimpostaPasswordException e) {
            throw new RuntimeException(e);
        }
    }
}


