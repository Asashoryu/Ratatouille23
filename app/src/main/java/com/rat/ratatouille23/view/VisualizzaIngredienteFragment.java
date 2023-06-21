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
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.rat.ratatouille23.R;
import com.rat.ratatouille23.adapter.IngredientiItemAdapter;
import com.rat.ratatouille23.databinding.FragmentVisualizzaIngredienteBinding;
import com.rat.ratatouille23.viewmodel.VisualizzaIngredienteViewModel;

public class VisualizzaIngredienteFragment extends Fragment {
    VisualizzaIngredienteViewModel visualizzaIngredienteViewModel;
    View fragmentView;
    FragmentVisualizzaIngredienteBinding visualizzaIngredienteBinding;

    IngredientiItemAdapter ingredientiItemAdapter;

    private boolean costoDiverso = false;
    private boolean quantitaDiversa = false;
    private boolean descrizioneDiversa = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        visualizzaIngredienteBinding = FragmentVisualizzaIngredienteBinding.inflate(inflater, container, false);

        fragmentView = visualizzaIngredienteBinding.getRoot();

        visualizzaIngredienteViewModel = new ViewModelProvider(this).get(VisualizzaIngredienteViewModel.class);
        visualizzaIngredienteBinding.setVisualizzaIngredienteViewModel(visualizzaIngredienteViewModel);

        visualizzaIngredienteBinding.costoText.setEnabled(false);
        visualizzaIngredienteBinding.quantitaText.setEnabled(false);
        visualizzaIngredienteBinding.descrizioneText.setEnabled(false);

        visualizzaIngredienteBinding.btnSalva.setEnabled(false);

        visualizzaIngredienteBinding.costoText.addTextChangedListener(new TextChangedListener<EditText> (visualizzaIngredienteBinding.costoText) {
            @Override
            public void onTextChanged(EditText target, Editable s) {
                String stringaCosto = visualizzaIngredienteBinding.costoText.getText().toString();
                float costo = -1.0f;
                if (!stringaCosto.isEmpty()) {
                    costo = Float.parseFloat(stringaCosto);
                }
                costoDiverso = visualizzaIngredienteViewModel.costoDiverso(costo);
                visualizzaIngredienteBinding.btnSalva.setEnabled(almenoUnoDiverso());
            }
        });

        visualizzaIngredienteBinding.quantitaText.addTextChangedListener(new TextChangedListener<EditText> (visualizzaIngredienteBinding.quantitaText) {
            @Override
            public void onTextChanged(EditText target, Editable s) {
                String stringaQuantita = visualizzaIngredienteBinding.quantitaText.getText().toString();
                float quantita = -1.0f;
                if (!stringaQuantita.isEmpty()) {
                    quantita = Float.parseFloat(stringaQuantita);
                }
                quantitaDiversa = visualizzaIngredienteViewModel.quantitaDiverso(quantita);
                visualizzaIngredienteBinding.btnSalva.setEnabled(almenoUnoDiverso());
            }
        });

        visualizzaIngredienteBinding.descrizioneText.addTextChangedListener(new TextChangedListener<EditText> (visualizzaIngredienteBinding.descrizioneText) {
            @Override
            public void onTextChanged(EditText target, Editable s) {
                String descrizione = visualizzaIngredienteBinding.descrizioneText.getText().toString();
                descrizioneDiversa = visualizzaIngredienteViewModel.descrizioneDiverso(descrizione);
                visualizzaIngredienteBinding.btnSalva.setEnabled(almenoUnoDiverso());
            }
        });

        osservaSeTornareIndietro();
        osservaMessaggioErrore();

        return fragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(requireContext());
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Schermata Visualizza Ingrediente Fragment");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "VisualizzaIngredienteFragment");
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
        firebaseAnalytics.setAnalyticsCollectionEnabled(true);
    }

    private boolean almenoUnoDiverso() {
        return (costoDiverso || quantitaDiversa || descrizioneDiversa);
    }

    public void osservaSeTornareIndietro() {
        visualizzaIngredienteViewModel.tornaIndietro.observe(getViewLifecycleOwner(), (tornaIndietro) -> {
            if (tornaIndietro) {
                visualizzaIngredienteViewModel.setFalseTornaIndietro();
                Navigation.findNavController(fragmentView).popBackStack();
            }
        });
    }

    public void osservaMessaggioErrore() {
        visualizzaIngredienteViewModel.messaggioVisualizzaIngrediente.observe(getViewLifecycleOwner(), (messaggio) -> {
            if (visualizzaIngredienteViewModel.isNuovoMessaggioVisualizzaIngrediente()) {
                visualizzaIngredienteBinding.errorFrame.setBackgroundResource(R.drawable.error_background);
                visualizzaIngredienteBinding.errorMessage.setText(messaggio);
                visualizzaIngredienteBinding.errorSign.setVisibility(View.VISIBLE);
                visualizzaIngredienteViewModel.cancellaMessaggioVisualizzaIngrediente();
            }
        });
    }

    public void visualizzaToastConMessaggio(String messaggio) {
        Toast.makeText(visualizzaIngredienteBinding.getRoot().getContext(), messaggio, Toast.LENGTH_SHORT).show();
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