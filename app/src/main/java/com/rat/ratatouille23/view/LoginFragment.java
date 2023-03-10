package com.rat.ratatouille23.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rat.ratatouille23.R;
import com.rat.ratatouille23.databinding.FragmentLoginViewBinding;
import com.rat.ratatouille23.viewmodel.LoginViewModel;

public class LoginFragment extends Fragment {

    LoginViewModel loginViewModel;
    View fragmentView;
    FragmentLoginViewBinding loginBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        loginBinding = FragmentLoginViewBinding.inflate(inflater, container, false);

        fragmentView = loginBinding.getRoot();

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginBinding.setLoginViewModel(loginViewModel);

        osservaSeAndareAvanti();
        osservaSeReimpostarePassword();
        osservaMessaggioErrore();

        return fragmentView;
    }

    public void osservaSeReimpostarePassword() {
        loginViewModel.isVaiAReimpostaPassword.observe(getViewLifecycleOwner(), (isVaiAReimpostaPassword) -> {
            if (isVaiAReimpostaPassword == true) {
                loginViewModel.setFalseIsVaiAReimpostaPassword();
                Navigation.findNavController(fragmentView).navigate(R.id.action_loginView_to_reimpostaPasswordFragment);
            }
        });
    }
    public void osservaSeAndareAvanti() {
        loginViewModel.isVaiAvanti.observe(getViewLifecycleOwner(), (isVaiAvanti) -> {
            if (isVaiAvanti == true) {
                if (loginViewModel.isUtenteAmministratore()) {
                    loginViewModel.setFalseIsVaiAvanti();
                    Navigation.findNavController(fragmentView).navigate(R.id.action_loginView_to_homeAmministratoreView);
                }
                else if (loginViewModel.isUtenteSupervisore()) {
                    loginViewModel.setFalseIsVaiAvanti();
                    Navigation.findNavController(fragmentView).navigate(R.id.action_loginView_to_homeSupervisoreView);
                }
                else if (loginViewModel.isUtenteAddettoSala()) {
                    loginViewModel.setFalseIsVaiAvanti();
                    Navigation.findNavController(fragmentView).navigate(R.id.action_loginView_to_homeAddettoSalaView);
                }
                else if (loginViewModel.isUtenteAddettoCucina()) {
                    loginViewModel.setFalseIsVaiAvanti();
                    Navigation.findNavController(fragmentView).navigate(R.id.action_loginView_to_homeAddettoCucinaView);
                }
            }
        });
    }

    public void osservaMessaggioErrore() {
        loginViewModel.messaggioLogin.observe(getViewLifecycleOwner(), (messaggio) -> {
            if (loginViewModel.isNuovoMessaggioLogin()) {
                visualizzaToastConMessaggio(loginViewModel.getMessaggioLogin());
                loginViewModel.cancellaMessaggioLogin();
            }
        });
    }

    public void visualizzaToastConMessaggio(String messaggio) {
        Toast.makeText(loginBinding.getRoot().getContext(), messaggio, Toast.LENGTH_SHORT).show();
    }
}