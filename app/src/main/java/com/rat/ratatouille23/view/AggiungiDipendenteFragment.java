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

import com.google.firebase.analytics.FirebaseAnalytics;
import com.rat.ratatouille23.R;
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

        impostaSpinner();

        osservaSeTornareIndietro();
        osservaMessaggioErrore();

        return fragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(requireContext());
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Schermata Aggiungi Dipendente");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "AggiungiDipendenteFragment");
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
        firebaseAnalytics.setAnalyticsCollectionEnabled(true);
    }


    public void impostaSpinner() {

        Spinner categorySpinner = aggiungiDipendenteBinding.ruoloText;

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, aggiungiDipendenteViewModel.getRuoloNames());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
        aggiungiDipendenteBinding.ruoloText.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                aggiungiDipendenteViewModel.getSelectedRuolo().set((String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
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
                aggiungiDipendenteBinding.errorFrame.setBackgroundResource(R.drawable.error_background);
                aggiungiDipendenteBinding.errorMessage.setText(messaggio);
                aggiungiDipendenteBinding.errorSign.setVisibility(View.VISIBLE);
                //visualizzaToastConMessaggio(messaggio);
                aggiungiDipendenteViewModel.cancellaMessaggioAggiungiDipendente();
            }
        });
    }

    /*public void visualizzaToastConMessaggio(String messaggio) {
        Toast.makeText(aggiungiDipendenteBinding.getRoot().getContext(), messaggio, Toast.LENGTH_SHORT).show();
    }*/
}