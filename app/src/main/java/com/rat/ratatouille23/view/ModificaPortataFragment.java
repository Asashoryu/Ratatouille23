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

import com.rat.ratatouille23.R;
import com.rat.ratatouille23.databinding.FragmentModificaPortataBinding;
import com.rat.ratatouille23.viewmodel.ModificaPortataViewModel;

public class ModificaPortataFragment extends Fragment {

    ModificaPortataViewModel modificaPortataViewModel;

    View fragmentView;

    FragmentModificaPortataBinding fragmentModificaPortataBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentModificaPortataBinding = FragmentModificaPortataBinding.inflate(inflater,container,false);

        fragmentView = fragmentModificaPortataBinding.getRoot();

        modificaPortataViewModel = new ViewModelProvider(this).get(ModificaPortataViewModel.class);
        fragmentModificaPortataBinding.setModificaPortataViewModel(modificaPortataViewModel);

        osservaSeTornareIndietro();
        osservaMessaggioErrore();
        impostaCategorieSpinner();

        return fragmentView;

    }

    public void impostaCategorieSpinner() {

        Spinner categoriaSpinner = fragmentModificaPortataBinding.categoriaSpinner;

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,modificaPortataViewModel.getCategoryNames());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoriaSpinner.setAdapter(adapter);

        categoriaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String categoriaSelezionata = categoriaSpinner.getSelectedItem().toString();
                modificaPortataViewModel.setCategoriaSelezionata(categoriaSelezionata);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //do nothing
            }
        });
    }

    public void osservaSeTornareIndietro() {
        modificaPortataViewModel.tornaIndietro.observe(getViewLifecycleOwner(), (tornaIndietro) -> {
            if (tornaIndietro) {
                modificaPortataViewModel.setFalseTornaIndietro();
                Navigation.findNavController(fragmentView).popBackStack();
            }
        });
    }

    public void osservaMessaggioErrore() {
        modificaPortataViewModel.messaggioModificaPortata.observe(getViewLifecycleOwner(), (messaggio) -> {
            if (modificaPortataViewModel.isNuovoMessaggioModificaPortata()) {
                fragmentModificaPortataBinding.errorFrame.setBackgroundResource(R.drawable.error_background);
                fragmentModificaPortataBinding.errorMessage.setText(messaggio);
                fragmentModificaPortataBinding.errorSign.setVisibility(View.VISIBLE);
                modificaPortataViewModel.cancellaMessaggioModificaPortata();
            }
        });
    }
}