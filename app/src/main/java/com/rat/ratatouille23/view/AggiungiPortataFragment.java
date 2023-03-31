package com.rat.ratatouille23.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.rat.ratatouille23.R;
import com.rat.ratatouille23.adapter.IngredientiItemAdapter;
import com.rat.ratatouille23.databinding.FragmentAggiungiPortataBinding;
import com.rat.ratatouille23.viewmodel.AggiungiPortataViewModel;

public class AggiungiPortataFragment extends Fragment {

    AggiungiPortataViewModel aggiungiPortataViewModel;
    View fragmentView;
    FragmentAggiungiPortataBinding aggiungiPortataBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        aggiungiPortataBinding = FragmentAggiungiPortataBinding.inflate(inflater, container, false);

        fragmentView = aggiungiPortataBinding.getRoot();

        aggiungiPortataViewModel = new ViewModelProvider(this).get(AggiungiPortataViewModel.class);
        aggiungiPortataBinding.setAggiungiPortataViewModel(aggiungiPortataViewModel);

        impostaSpinner();

        osservaSeTornareIndietro();
        osservaMessaggioErrore();

        return fragmentView;
    }

    public void impostaSpinner() {

        Spinner categorySpinner = aggiungiPortataBinding.categoriaText;

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, aggiungiPortataViewModel.getCategoryNames());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
        aggiungiPortataBinding.categoriaText.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                aggiungiPortataViewModel.getSelectedCategory().set((String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void osservaSeTornareIndietro() {
        aggiungiPortataViewModel.tornaIndietro.observe(getViewLifecycleOwner(), (tornaIndietro) -> {
            if (tornaIndietro) {
                aggiungiPortataViewModel.setFalseTornaIndietro();
                Navigation.findNavController(fragmentView).popBackStack();
            }
        });
    }

    public void osservaMessaggioErrore() {
        aggiungiPortataViewModel.messaggioAggiungiPortata.observe(getViewLifecycleOwner(), (messaggio) -> {
            if (aggiungiPortataViewModel.isNuovoMessaggioAggiungiPortata()) {
                visualizzaToastConMessaggio(messaggio);
                aggiungiPortataViewModel.cancellaMessaggioAggiungiPortata();
            }
        });
    }

    public void visualizzaToastConMessaggio(String messaggio) {
        Toast.makeText(aggiungiPortataBinding.getRoot().getContext(), messaggio, Toast.LENGTH_SHORT).show();
    }
}