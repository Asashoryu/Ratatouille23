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
import com.rat.ratatouille23.adapter.IngredientiItemAdapter;
import com.rat.ratatouille23.databinding.FragmentAggiungiDipendenteBinding;
import com.rat.ratatouille23.viewmodel.AggiungiDipendenteViewModel;

public class AggiungiDipendenteFragment extends Fragment {

    AggiungiDipendenteViewModel aggiungiDipendenteViewModel;
    View fragmentView;
    FragmentAggiungiDipendenteBinding aggiungiDipendenteBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        aggiungiDipendenteBinding = FragmentAggiungiDipendenteBinding.inflate(inflater, container, false);

        fragmentView = aggiungiDipendenteBinding.getRoot();

        aggiungiDipendenteViewModel = new ViewModelProvider(this).get(AggiungiDipendenteViewModel.class);
        aggiungiDipendenteBinding.setAggiungiDipendenteViewModel(aggiungiDipendenteViewModel);

        osservaSeTornareIndietro();
        osservaMessaggioErrore();

        return fragmentView;
    }

    public void osservaSeTornareIndietro() {
        aggiungiDipendenteViewModel.tornaIndietro.observe(getViewLifecycleOwner(), (tornaIndietro) -> {
            if (tornaIndietro) {
                aggiungiDipendenteViewModel.setFalseTornaIndietro();
                Navigation.findNavController(fragmentView).popBackStack();
            }
        });
    }

    public void osservaMessaggioErrore() {
        aggiungiDipendenteViewModel.messaggioAggiungiDipendente.observe(getViewLifecycleOwner(), (messaggio) -> {
            if (aggiungiDipendenteViewModel.isNuovoMessaggioAggiungiDipendente()) {
                visualizzaToastConMessaggio(messaggio);
                aggiungiDipendenteViewModel.cancellaMessaggioAggiungiDipendente();
            }
        });
    }

    public void visualizzaToastConMessaggio(String messaggio) {
        Toast.makeText(aggiungiDipendenteBinding.getRoot().getContext(), messaggio, Toast.LENGTH_SHORT).show();
    }
}