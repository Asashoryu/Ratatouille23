package com.rat.ratatouille23.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rat.ratatouille23.R;
import com.rat.ratatouille23.databinding.FragmentAggiungiDipendenteViewBinding;
import com.rat.ratatouille23.viewmodel.AggiungiDipendenteViewModel;

public class AggiungiDipendenteView extends Fragment {

    AggiungiDipendenteViewModel AggiungiDipendenteViewModel;
    View fragmentView;
    FragmentAggiungiDipendenteViewBinding AggiungiDipendenteBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        AggiungiDipendenteBinding = FragmentAggiungiDipendenteViewBinding.inflate(inflater, container, false);

        fragmentView = AggiungiDipendenteBinding.getRoot();

        AggiungiDipendenteViewModel = new ViewModelProvider(this).get(AggiungiDipendenteViewModel.class);
        AggiungiDipendenteBinding.setAggiungiDipendenteViewModel(AggiungiDipendenteViewModel);

        return fragmentView;
    }
}