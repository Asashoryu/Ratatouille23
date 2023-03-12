package com.rat.ratatouille23.view;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.rat.ratatouille23.databinding.FragmentVisualizzaStatisticheBinding;
import com.rat.ratatouille23.viewmodel.VisualizzaStatisticheViewModel;

import java.util.ArrayList;


public class VisualizzaStatisticheFragment extends Fragment {

    VisualizzaStatisticheViewModel visualizzaStatisticheViewModel;
    View fragmentView;
    FragmentVisualizzaStatisticheBinding visualizzaStatisticheBinding;

    BarChart barChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        visualizzaStatisticheBinding = FragmentVisualizzaStatisticheBinding.inflate(inflater, container, false);

        fragmentView = visualizzaStatisticheBinding.getRoot();

        visualizzaStatisticheViewModel = new ViewModelProvider(this).get(VisualizzaStatisticheViewModel.class);
        visualizzaStatisticheBinding.setVisualizzaStatisticheViewModel(visualizzaStatisticheViewModel);

        impostaBarChartTest();

        osservaSeTornareIndietro();
        osservaMessaggioErrore();

        return fragmentView;
    }

    public void impostaBarChart() {
        barChart = visualizzaStatisticheBinding.barChart;

        ArrayList<BarEntry> barEntries = new ArrayList<>();

        for (int i=1; i<10; i++) {
            float value = (float) (i*10.0);
            BarEntry barEntry = new BarEntry(i, value);
            barEntries.add(barEntry);
        }

        BarDataSet barDataSet = new BarDataSet(barEntries, "daad");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setDrawValues(false);
        barChart.setData(new BarData(barDataSet));
        barChart.animateY(2000);
        barChart.getDescription().setText("Test Chart mio");
        barChart.getDescription().setTextColor(Color.BLUE);

    }

    public void impostaBarChartTest() {
        barChart = visualizzaStatisticheBinding.barChart;

        ArrayList<BarEntry> barEntries = new ArrayList<>();

        for (int i=1; i<10; i++) {
            float value = (float) (i*10.0);
            BarEntry barEntry = new BarEntry(i, value);
            barEntries.add(barEntry);
        }

        BarDataSet barDataSet = new BarDataSet(barEntries, "daad");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setDrawValues(false);
        barChart.setData(new BarData(barDataSet));
        barChart.animateY(2000);
        barChart.getDescription().setText("Test Chart mio");
        barChart.getDescription().setTextColor(Color.BLUE);

    }

    public void osservaSeTornareIndietro() {
        visualizzaStatisticheViewModel.tornaIndietro.observe(getViewLifecycleOwner(), (tornaIndietro) -> {
            if (tornaIndietro) {
                visualizzaStatisticheViewModel.setFalseTornaIndietro();
                Navigation.findNavController(fragmentView).popBackStack();
            }
        });
    }

    public void osservaMessaggioErrore() {
        visualizzaStatisticheViewModel.messaggioVisualizzaStatistiche.observe(getViewLifecycleOwner(), (messaggio) -> {
            if (visualizzaStatisticheViewModel.isNuovoMessaggioVisualizzaStatistiche()) {
                visualizzaToastConMessaggio(messaggio);
                visualizzaStatisticheViewModel.cancellaMessaggioVisualizzaStatistiche();
            }
        });
    }

    public void visualizzaToastConMessaggio(String messaggio) {
        Toast.makeText(visualizzaStatisticheBinding.getRoot().getContext(), messaggio, Toast.LENGTH_SHORT).show();
    }
}