package com.rat.ratatouille23.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rat.ratatouille23.R;
import com.rat.ratatouille23.adapter.CategorieItemAdapter;
import com.rat.ratatouille23.adapter.PortateItemAdapter;
import com.rat.ratatouille23.databinding.FragmentOrdinazioneBinding;
import com.rat.ratatouille23.viewmodel.OrdinazioneViewModel;

public class OrdinazioneFragment extends Fragment {

    OrdinazioneViewModel ordinazioneViewModel;
    View fragmentView;
    FragmentOrdinazioneBinding ordinazioneBinding;

    CategorieItemAdapter categorieItemAdapter;

    PortateItemAdapter portateItemAdapter;

    PortateItemAdapter portateContoItemAdapter;

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

        return fragmentView;
    }

    public void ImpostaCategorieItemAdapter() {
        categorieItemAdapter = new CategorieItemAdapter( (categoria) -> {
            ordinazioneViewModel.aggiornaListaPortate(categoria);
            System.err.println("Letto tutta la lambda di categorie item adapter");
        });
        ordinazioneBinding.listaCategorie.setAdapter(categorieItemAdapter);
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
        portateContoItemAdapter = new PortateItemAdapter((portata) -> {});
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
}