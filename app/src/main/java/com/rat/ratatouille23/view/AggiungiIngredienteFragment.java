package com.rat.ratatouille23.view;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
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

        impostaSeekBar();

        osservaSeTornareIndietro();
        osservaMessaggioErrore();

        return fragmentView;
    }

    public void impostaSeekBar() {

        SeekBar seekBar;
        TextView sogliaTextView;
        final int soglia = 0;

        seekBar = aggiungiIngredienteBinding.seekBar;
        sogliaTextView = aggiungiIngredienteBinding.sogliaText;

        // Set the initial value of the "soglia" variable
        sogliaTextView.setText(String.valueOf(soglia));

        // Set a listener to update the "soglia" variable when the SeekBar is moved
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Update the soglia TextView
                sogliaTextView.setText(String.valueOf(progress));

                // Calculate the percentage value of quantita based on the SeekBar progress
                EditText quantitaTextView = aggiungiIngredienteBinding.quantitaText;
                String quantitaString = quantitaTextView.getText().toString().trim();
                if (!quantitaString.isEmpty()) {
                    double quantita = Double.parseDouble(quantitaString);
                    double percentuale = (progress / 100.0) * quantita;
                    sogliaTextView.setText(String.format("%.2f", percentuale));
                } else {
                    sogliaTextView.setText(String.valueOf(soglia));
                }

                // Update the SeekBar progress drawable with the percentage text
                Drawable progressDrawable = seekBar.getProgressDrawable().mutate();
                progressDrawable.setLevel(progress * 100);
                seekBar.setProgressDrawable(progressDrawable);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Do nothing
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Do nothing
            }
        });

        // Set the custom progress drawable with percentage text
        LayerDrawable layerDrawable = (LayerDrawable) seekBar.getProgressDrawable();
        Drawable progressDrawable = layerDrawable.getDrawable(1).mutate();
        progressDrawable.setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        layerDrawable.setDrawableByLayerId(android.R.id.progress, progressDrawable);
        seekBar.setProgressDrawable(layerDrawable);
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