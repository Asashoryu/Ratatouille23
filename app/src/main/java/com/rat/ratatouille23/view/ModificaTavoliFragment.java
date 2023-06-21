package com.rat.ratatouille23.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.rat.ratatouille23.R;
import com.rat.ratatouille23.adapter.TavoliItemAdapter;
import com.rat.ratatouille23.databinding.FragmentModificaTavoliBinding;
import com.rat.ratatouille23.viewmodel.ModificaTavoliViewModel;

public class ModificaTavoliFragment extends Fragment {

    ModificaTavoliViewModel modificaTavoliViewModel;
    View fragmentView;
    FragmentModificaTavoliBinding modificaTavoliBinding;

    TavoliItemAdapter tavoliItemAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        modificaTavoliBinding = FragmentModificaTavoliBinding.inflate(inflater, container, false);

        fragmentView = modificaTavoliBinding.getRoot();

        modificaTavoliViewModel = new ViewModelProvider(this).get(ModificaTavoliViewModel.class);
        modificaTavoliBinding.setModificaTavoliViewModel(modificaTavoliViewModel);

        impostaTavoliItemAdapter();

        osservaCambientoTavoli();

        osservaSeTornareIndietro();

        return fragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(requireContext());
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Schermata Modifica Tavoli Fragment");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "ModificaTavoliFragment");
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
        firebaseAnalytics.setAnalyticsCollectionEnabled(true);
    }

    public void impostaTavoliItemAdapter() {
        tavoliItemAdapter = new TavoliItemAdapter( (tavolo) -> {
            final String dialogTitle = "Elimina tavolo";
            final String dialogMessage = "Sei sicuro di voler rimuovere questo tavolo?";
            final String dialogPositiveButton = "Elimina";
            final String dialogNegativeButton = "Annulla";

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            // Set the title and message of the dialog
            builder.setTitle(dialogTitle)
                    .setMessage(dialogMessage);
            //.setIcon(R.drawable.ic_delete); // Set an icon for the dialog, if desired

            // Set the positive button of the dialog
            builder.setPositiveButton(dialogPositiveButton, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // Delete the table here
                    modificaTavoliViewModel.rimuoviTavolo(tavolo);
                }
            });

            // Set the negative button of the dialog
            builder.setNegativeButton(dialogNegativeButton, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // Dismiss the dialog without doing anything
                    dialog.dismiss();
                }
            });

            // Create and show the dialog
            AlertDialog dialog = builder.create();
            dialog.show();

            // Customize the buttons after the dialog is created
            Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
            Button negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);

            // Set the text color of the buttons
            // positiveButton.setTextColor(getResources().getColor(R.color.red));
            // negativeButton.setTextColor(getResources().getColor(R.color.gray));

            // Set the background color of the buttons
            positiveButton.setBackgroundColor(Color.TRANSPARENT);
            negativeButton.setBackgroundColor(Color.TRANSPARENT);
        });
        modificaTavoliBinding.listaTavoli.setAdapter(tavoliItemAdapter);
    }

    public void osservaCambientoTavoli() {
        modificaTavoliViewModel.listaTavoli.observe(getViewLifecycleOwner(), listaTavoli ->
        {
            tavoliItemAdapter.setData(listaTavoli);
        });
    }

    public void osservaMessaggioErrore() {
        modificaTavoliViewModel.messaggioModificaTavoli.observe(getViewLifecycleOwner(), (messaggio) -> {
            if (modificaTavoliViewModel.isNuovoMessaggioModificaTavoli()) {
                visualizzaToastConMessaggio(messaggio);
                modificaTavoliViewModel.cancellaMessaggioModificaTavoli();
            }
        });
    }

    public void visualizzaToastConMessaggio(String messaggio) {
        Toast.makeText(modificaTavoliBinding.getRoot().getContext(), messaggio, Toast.LENGTH_SHORT).show();
    }

    public void osservaSeTornareIndietro() {
        modificaTavoliViewModel.tornaIndietro.observe(getViewLifecycleOwner(), (tornaIndietro) -> {
            if (tornaIndietro) {
                Navigation.findNavController(fragmentView).popBackStack();
            }
        });
    }

}