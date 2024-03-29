package com.rat.ratatouille23.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.rat.ratatouille23.R;
import com.rat.ratatouille23.adapter.CategorieItemAdapter;
import com.rat.ratatouille23.adapter.PortateContoItemAdapter;
import com.rat.ratatouille23.adapter.PortateItemAdapter;
import com.rat.ratatouille23.databinding.FragmentOrdinazioneBinding;
import com.rat.ratatouille23.viewmodel.OrdinazioneViewModel;

public class OrdinazioneFragment extends Fragment {

    OrdinazioneViewModel ordinazioneViewModel;
    View fragmentView;
    FragmentOrdinazioneBinding ordinazioneBinding;

    CategorieItemAdapter categorieItemAdapter;

    PortateItemAdapter portateItemAdapter;

    PortateContoItemAdapter portateContoItemAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ordinazioneBinding = FragmentOrdinazioneBinding.inflate(inflater, container, false);

        fragmentView = ordinazioneBinding.getRoot();

        ordinazioneViewModel = new ViewModelProvider(this).get(OrdinazioneViewModel.class);
        ordinazioneBinding.setOrdinazioneViewModel(ordinazioneViewModel);

        ImpostaCategorieItemAdapter();
        ImpostaPortateItemAdapter();
        ImpostaPortateContoItemAdapter();

        osservaCambientoCategorie();
        osservaCambientoPortate();
        osservaCambientoPortateConto();

        osservaMessaggioErrore();

        osservaCambiamentoCostoTotaleConto();

        osservaSeTornareIndietro();

        return fragmentView;
    }

    public void ImpostaCategorieItemAdapter() {
        categorieItemAdapter = new CategorieItemAdapter( (categoria) -> {
            ordinazioneViewModel.aggiornaListaPortate(categoria);
            System.err.println("Letto tutta la lambda di categorie item adapter");
        });
        ordinazioneBinding.listaCategorie.setAdapter(categorieItemAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(requireContext());
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Schermata Ordinazione Fragment");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "OrdinazioneFragment");
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
        firebaseAnalytics.setAnalyticsCollectionEnabled(true);
    }

    public void ImpostaPortateItemAdapter() {
        portateItemAdapter = new PortateItemAdapter( (portata) -> {
            ordinazioneViewModel.aggiungiPortataAllOrdinazione(portata);
            ordinazioneViewModel.aggiornaListaPortateConto();
            System.err.println("Letto tutta la lambda di portate item adapter");
        });
        ordinazioneBinding.listaPortate.setAdapter(portateItemAdapter);
    }

    public void ImpostaPortateContoItemAdapter() {
        portateContoItemAdapter = new PortateContoItemAdapter((portataConto) -> {
            ordinazioneViewModel.rimuoviPortataDaOrdinazione(portataConto);
            ordinazioneViewModel.aggiornaListaPortateConto();
        });
        ordinazioneBinding.listaPortateConto.setAdapter(portateContoItemAdapter);
    }

    public void osservaCambientoCategorie() {
        ordinazioneViewModel.listaCategorie.observe(getViewLifecycleOwner(), listaCategorie ->
        {
            categorieItemAdapter.setData(listaCategorie);
        });
    }

    public void osservaCambientoPortate() {
        ordinazioneViewModel.listaPortate.observe(getViewLifecycleOwner(), listaPortate ->
        {
            portateItemAdapter.setData(listaPortate);
        });
    }

    public void osservaCambientoPortateConto() {
        ordinazioneViewModel.listaPortateConto.observe(getViewLifecycleOwner(), listaPortateConto ->
        {
            portateContoItemAdapter.setData(listaPortateConto);
        });
    }

    public void osservaCambiamentoCostoTotaleConto() {
        ordinazioneViewModel.costoTotaleConto.observe(getViewLifecycleOwner(), costoTotaleConto ->
        {
            ordinazioneBinding.txtCostoTotale.setText(costoTotaleConto.toString());
        });
    }

    public void osservaSeTornareIndietro() {
        ordinazioneViewModel.tornaIndietro.observe(getViewLifecycleOwner(), (tornaIndietro) -> {
            if (tornaIndietro) {
                ordinazioneViewModel.setFalseTornaIndietro();
                Navigation.findNavController(fragmentView).popBackStack();
            }
        });
    }

    public void osservaMessaggioErrore() {
        ordinazioneViewModel.messaggioOrdinazione.observe(getViewLifecycleOwner(), (messaggio) -> {
            if (ordinazioneViewModel.isNuovoMessaggioOrdinazione()) {
                visualizzaToastConMessaggio(messaggio);
                ordinazioneViewModel.cancellaMessaggioOrdinazione();
            }
        });
    }

    public void visualizzaToastConMessaggio(String messaggio) {
        Toast.makeText(ordinazioneBinding.getRoot().getContext(), messaggio, Toast.LENGTH_SHORT).show();
    }
}