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
import com.rat.ratatouille23.databinding.FragmentAggiungiIngredienteBinding;
import com.rat.ratatouille23.viewmodel.AggiungiIngredienteViewModel;

public class AggiungiIngredienteFragment extends Fragment {

    AggiungiIngredienteViewModel aggiungiIngredienteViewModel;
    View fragmentView;
    FragmentAggiungiIngredienteBinding aggiungiIngredienteBinding;

    IngredientiItemAdapter ingredientiItemAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        aggiungiIngredienteBinding = FragmentAggiungiIngredienteBinding.inflate(inflater, container, false);

        fragmentView = aggiungiIngredienteBinding.getRoot();

        aggiungiIngredienteViewModel = new ViewModelProvider(this).get(AggiungiIngredienteViewModel.class);
        aggiungiIngredienteBinding.setAggiungiIngredienteViewModel(aggiungiIngredienteViewModel);

        osservaSeTornareIndietro();
        osservaMessaggioErrore();

        return fragmentView;
    }

    public void osservaSeTornareIndietro() {
        aggiungiIngredienteViewModel.tornaIndietro.observe(getViewLifecycleOwner(), (tornaIndietro) -> {
            if (tornaIndietro) {
                aggiungiIngredienteViewModel.setFalseTornaIndietro();
                Navigation.findNavController(fragmentView).popBackStack();
            }
        });
    }

    public void osservaMessaggioErrore() {
        aggiungiIngredienteViewModel.messaggioAggiungiIngrediente.observe(getViewLifecycleOwner(), (messaggio) -> {
            if (aggiungiIngredienteViewModel.isNuovoMessaggioAggiungiIngrediente()) {
                aggiungiIngredienteBinding.errorFrame.setBackgroundResource(R.drawable.error_background);
                aggiungiIngredienteBinding.errorMessage.setText(messaggio);
                aggiungiIngredienteBinding.errorSign.setVisibility(View.VISIBLE);
                //visualizzaToastConMessaggio(messaggio);
                aggiungiIngredienteViewModel.cancellaMessaggioAggiungiIngrediente();
            }
        });
    }

    /*public void visualizzaToastConMessaggio(String messaggio) {
        Toast.makeText(aggiungiIngredienteBinding.getRoot().getContext(), messaggio, Toast.LENGTH_SHORT).show();
    }*/
}