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
import com.rat.ratatouille23.databinding.FragmentIndicaQuantitaIngredienteBinding;
import com.rat.ratatouille23.viewmodel.IndicaQuantitaViewModel;

public class IndicaQuantitaIngredienteFragment extends Fragment {

    IndicaQuantitaViewModel indicaQuantitaViewModel;
    View fragmentView;
    FragmentIndicaQuantitaIngredienteBinding indicaQuantitaBinding;

    IngredientiItemAdapter ingredientiItemAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        indicaQuantitaBinding = FragmentIndicaQuantitaIngredienteBinding.inflate(inflater, container, false);

        fragmentView = indicaQuantitaBinding.getRoot();

        indicaQuantitaViewModel = new ViewModelProvider(this).get(IndicaQuantitaViewModel.class);
        indicaQuantitaBinding.setIndicaQuantitaViewModel(indicaQuantitaViewModel);

        osservaSeTornareIndietro();
        osservaMessaggioErrore();

        return fragmentView;
    }

    public void osservaSeTornareIndietro() {
        indicaQuantitaViewModel.tornaIndietro.observe(getViewLifecycleOwner(), (tornaIndietro) -> {
            if (tornaIndietro) {
                indicaQuantitaViewModel.setFalseTornaIndietro();
                Navigation.findNavController(fragmentView).popBackStack();
            }
        });
    }

    public void osservaMessaggioErrore() {
        indicaQuantitaViewModel.messaggioIndicaQuantita.observe(getViewLifecycleOwner(), (messaggio) -> {
            if (indicaQuantitaViewModel.isNuovoMessaggioIndicaQuantita()) {
                visualizzaToastConMessaggio(messaggio);
                indicaQuantitaViewModel.cancellaMessaggioIndicaQuantita();
            }
        });
    }

    public void visualizzaToastConMessaggio(String messaggio) {
        Toast.makeText(indicaQuantitaBinding.getRoot().getContext(), messaggio, Toast.LENGTH_SHORT).show();
    }
}