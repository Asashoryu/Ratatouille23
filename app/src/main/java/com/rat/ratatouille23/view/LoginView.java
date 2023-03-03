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
import com.rat.ratatouille23.utility.NomeTipo;
import com.rat.ratatouille23.viewmodel.LoginViewModel;

public class LoginView extends Fragment {

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

        return fragmentView;
    }

    public void osservaSeAndareAvanti() {
        loginViewModel.loggato.observe(getViewLifecycleOwner(), (loggato) -> {
            if (!loggato.equals(NomeTipo.FALSE)) {
                if (loggato.equals(NomeTipo.AMMINISTRATORE)) {
                    loginViewModel.setLoggatoFalse();
                    Navigation.findNavController(fragmentView).navigate(R.id.action_loginView_to_homeAmministratoreView);
                }
                else if (loggato.equals(NomeTipo.SUPERVISORE)) {
                    loginViewModel.setLoggatoFalse();
                    Navigation.findNavController(fragmentView).navigate(R.id.action_loginView_to_homeSupervisoreView);
                }
                else if (loggato.equals(NomeTipo.ADDETTOSALA)) {
                    loginViewModel.setLoggatoFalse();
                    Navigation.findNavController(fragmentView).navigate(R.id.action_loginView_to_homeAddettoSalaView);
                }
                else if (loggato.equals(NomeTipo.ADDETTOCUCINA)) {
                    loginViewModel.setLoggatoFalse();
                    Navigation.findNavController(fragmentView).navigate(R.id.action_loginView_to_homeAddettoCucinaView);
                }
                else {
                    visualizzaToastConMessaggio(loggato);
                    loginViewModel.setLoggatoFalse();
                }
            }
        });
    }

    public void visualizzaToastConMessaggio(String messaggio) {
        Toast.makeText(loginBinding.getRoot().getContext(), messaggio, Toast.LENGTH_SHORT).show();
    }
}