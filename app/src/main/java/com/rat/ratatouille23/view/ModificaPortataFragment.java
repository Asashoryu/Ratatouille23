package com.rat.ratatouille23.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.rat.ratatouille23.R;
import com.rat.ratatouille23.databinding.FragmentModificaPortataBinding;
import com.rat.ratatouille23.viewmodel.ModificaPortataViewModel;

public class ModificaPortataFragment extends Fragment {

    ModificaPortataViewModel modificaPortataViewModel;

    View fragmentView;

    FragmentModificaPortataBinding fragmentModificaPortataBinding;

    private boolean costoDiverso = false;

    private boolean categoriaDiversa = false;
    private boolean allergeniDiversi = false;
    private boolean descrizioneDiversa = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentModificaPortataBinding = FragmentModificaPortataBinding.inflate(inflater,container,false);

        fragmentView = fragmentModificaPortataBinding.getRoot();

        modificaPortataViewModel = new ViewModelProvider(this).get(ModificaPortataViewModel.class);
        fragmentModificaPortataBinding.setModificaPortataViewModel(modificaPortataViewModel);

        fragmentModificaPortataBinding.costoText.setEnabled(false);
        fragmentModificaPortataBinding.categoriaSpinner.setEnabled(false);
        fragmentModificaPortataBinding.listaAllergeniText.setEnabled(false);
        fragmentModificaPortataBinding.descrizioneText.setEnabled(false);

        fragmentModificaPortataBinding.btnSalva.setEnabled(false);

        fragmentModificaPortataBinding.costoText.addTextChangedListener(new TextChangedListener<EditText> (fragmentModificaPortataBinding.costoText) {
            @Override
            public void onTextChanged(EditText target, Editable s) {

                String stringaCosto = fragmentModificaPortataBinding.costoText.getText().toString();
                float costo = -1.0f;
                if (!stringaCosto.isEmpty()) {
                    costo = Float.parseFloat(stringaCosto);
                }
                costoDiverso = modificaPortataViewModel.costoDiverso(costo);
                fragmentModificaPortataBinding.btnSalva.setEnabled(almenoUnoDiverso());
            }
        });


        fragmentModificaPortataBinding.listaAllergeniText.addTextChangedListener(new TextChangedListener<EditText> (fragmentModificaPortataBinding.listaAllergeniText) {
            @Override
            public void onTextChanged(EditText target, Editable s) {
                String allergeni = fragmentModificaPortataBinding.listaAllergeniText.getText().toString();
                allergeniDiversi = modificaPortataViewModel.allergeniDiversi(allergeni);
                fragmentModificaPortataBinding.btnSalva.setEnabled(almenoUnoDiverso());
            }
        });

        fragmentModificaPortataBinding.descrizioneText.addTextChangedListener(new TextChangedListener<EditText> (fragmentModificaPortataBinding.descrizioneText) {
            @Override
            public void onTextChanged(EditText target, Editable s) {
                String descrizione = fragmentModificaPortataBinding.descrizioneText.getText().toString();
                descrizioneDiversa = modificaPortataViewModel.descrizioneDiversa(descrizione);
                fragmentModificaPortataBinding.btnSalva.setEnabled(almenoUnoDiverso());
            }
        });

        osservaSeTornareIndietro();
        osservaMessaggioErrore();
        impostaCategorieSpinner();

        return fragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(requireContext());
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Modifica Portata Fragment");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "ModificaPortataFragment");
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
        firebaseAnalytics.setAnalyticsCollectionEnabled(true);
    }

    private boolean almenoUnoDiverso() {
        return (costoDiverso || categoriaDiversa || allergeniDiversi || descrizioneDiversa);
    }

    public void impostaCategorieSpinner() {

        Spinner categoriaSpinner = fragmentModificaPortataBinding.categoriaSpinner;

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,modificaPortataViewModel.getCategoryNames());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoriaSpinner.setAdapter(adapter);
        modificaPortataViewModel.setIndexCategoriaIniziale(adapter.getPosition(modificaPortataViewModel.getCategoriaIniziale()));
        categoriaSpinner.setSelection(adapter.getPosition(modificaPortataViewModel.getCategoriaIniziale()));

        categoriaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String categoriaSelezionata = categoriaSpinner.getSelectedItem().toString();
                categoriaDiversa = modificaPortataViewModel.categoriaDiversa(categoriaSelezionata);
                fragmentModificaPortataBinding.btnSalva.setEnabled(almenoUnoDiverso());
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

    public abstract class TextChangedListener<T> implements TextWatcher {
        private T target;

        public TextChangedListener(T target) {
            this.target = target;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            this.onTextChanged(target, s);
        }

        public abstract void onTextChanged(T target, Editable s);
    }
}