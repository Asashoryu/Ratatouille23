package com.rat.ratatouille23.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rat.ratatouille23.R;
import com.rat.ratatouille23.adapter.CategorieItemAdapter;
import com.rat.ratatouille23.adapter.PortateContoItemAdapter;
import com.rat.ratatouille23.adapter.PortateItemAdapter;
import com.rat.ratatouille23.databinding.FragmentVisualizzaContoBinding;
import com.rat.ratatouille23.viewmodel.VisualizzaContoViewModel;


public class VisualizzaContoFragment extends Fragment {

    VisualizzaContoViewModel visualizzaContoViewModel;
    View fragmentView;
    FragmentVisualizzaContoBinding visualizzaContoBinding;

    PortateContoItemAdapter portateContoItemAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        visualizzaContoBinding = FragmentVisualizzaContoBinding.inflate(inflater, container, false);

        fragmentView = visualizzaContoBinding.getRoot();

        visualizzaContoViewModel = new ViewModelProvider(this).get(VisualizzaContoViewModel.class);
        visualizzaContoBinding.setVisualizzaContoViewModel(visualizzaContoViewModel);

        ImpostaPortateContoItemAdapter();

        osservaCambientoPortateConto();

        return fragmentView;
    }

    public void ImpostaPortateContoItemAdapter() {
        portateContoItemAdapter = new PortateContoItemAdapter((portata) -> {});
        visualizzaContoBinding.listaPortateConto.setAdapter(portateContoItemAdapter);
    }

    public void osservaCambientoPortateConto() {
        visualizzaContoViewModel.listaPortateConto.observe(getViewLifecycleOwner(), listaPortateConto ->
        {
            portateContoItemAdapter.setData(listaPortateConto);
        });
    }
}