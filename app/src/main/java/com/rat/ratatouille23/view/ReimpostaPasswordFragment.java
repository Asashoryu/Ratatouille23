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
import com.rat.ratatouille23.databinding.FragmentReimpostaPasswordBinding;
import com.rat.ratatouille23.viewmodel.ReimpostaPasswordViewModel;

public class ReimpostaPasswordFragment extends Fragment {

    ReimpostaPasswordViewModel reimpostaPasswordViewModel;
    View fragmentView;
    FragmentReimpostaPasswordBinding reimpostaPasswordBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        reimpostaPasswordBinding = FragmentReimpostaPasswordBinding.inflate(inflater, container, false);

        fragmentView = reimpostaPasswordBinding.getRoot();

        reimpostaPasswordViewModel = new ViewModelProvider(this).get(ReimpostaPasswordViewModel.class);
        reimpostaPasswordBinding.setReimpostaPasswordViewModel(reimpostaPasswordViewModel);

        osservaSeTornareIndietro();
        osservaMessaggioErrore();

        return fragmentView;
    }

    public void osservaSeTornareIndietro() {
        reimpostaPasswordViewModel.tornaIndietro.observe(getViewLifecycleOwner(), (tornaIndietro) -> {
            if (tornaIndietro) {
                reimpostaPasswordViewModel.setFalseTornaIndietro();
                Navigation.findNavController(fragmentView).popBackStack();
            }
        });
    }

    public void osservaMessaggioErrore() {
        reimpostaPasswordViewModel.messaggioReimpostaPassword.observe(getViewLifecycleOwner(), (messaggio) -> {
            if (reimpostaPasswordViewModel.isNuovoMessaggioReimpostaPassword()) {
                visualizzaToastConMessaggio(messaggio);
                reimpostaPasswordViewModel.cancellaMessaggioReimpostaPassword();
            }
        });
    }

    public void visualizzaToastConMessaggio(String messaggio) {
        Toast.makeText(reimpostaPasswordBinding.getRoot().getContext(), messaggio, Toast.LENGTH_SHORT).show();
    }
}