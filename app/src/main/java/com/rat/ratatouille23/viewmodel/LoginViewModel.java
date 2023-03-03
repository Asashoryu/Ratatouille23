package com.rat.ratatouille23.viewmodel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rat.ratatouille23.R;
import com.rat.ratatouille23.repository.Repository;
import com.rat.ratatouille23.utility.NomeTipo;


public class LoginViewModel extends ViewModel {

    private String nome;
    private String password;
    private Repository repository;

    public MutableLiveData<String> loggato = new MutableLiveData<>(NomeTipo.FALSE);

    public LoginViewModel() {
        repository = Repository.getInstance();
        repository.setLoginViewModel(this);
    }

    public void setLoggatoFalse() {
        loggato.setValue(NomeTipo.FALSE);
    }

    public void setLoggato(String text) {
        loggato.postValue(text);
    }

    public void login(String nome, String password) {
        try {
            repository.login(nome, password);
        } catch (Exception e) {
            setLoggato(e.getMessage());
        }
    }
}