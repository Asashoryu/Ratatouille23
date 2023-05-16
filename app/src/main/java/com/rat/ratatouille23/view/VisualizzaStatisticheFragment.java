package com.rat.ratatouille23.view;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.rat.ratatouille23.R;
import com.rat.ratatouille23.databinding.FragmentVisualizzaStatisticheBinding;
import com.rat.ratatouille23.viewmodel.VisualizzaStatisticheViewModel;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


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


        osservaSeTornareIndietro();
        osservaMessaggioErrore();

        impostaSpinnerAnniCheImpostaGrafico();



        return fragmentView;
    }

    public void impostaSpinnerAnniCheImpostaGrafico() {
        // Definisci lo spinner
        Spinner spinnerAnno = visualizzaStatisticheBinding.yearSpinner;

        // Crea un ArrayAdapter per popolare lo spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.year_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAnno.setAdapter(adapter);

        // Imposta l'anno iniziale al primo elemento dello spinner
        int annoIniziale = Integer.parseInt(spinnerAnno.getSelectedItem().toString());

        // Imposta il grafico per l'anno iniziale
        impostaBarChart(annoIniziale);

        // Aggiorna il grafico quando viene selezionato un nuovo anno nello spinner
        spinnerAnno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int anno = Integer.parseInt(parent.getItemAtPosition(position).toString());
                impostaBarChart(anno);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Non fare nulla
            }
        });
    }


    public static boolean isAnnoBisestile(int anno) {
        if (anno % 4 == 0) {
            if (anno % 100 == 0) {
                return anno % 400 == 0;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public void impostaBarChart(int anno) {
        BarChart grafico = visualizzaStatisticheBinding.chart;

        grafico.setDrawBarShadow(false);
        grafico.setDrawValueAboveBar(true);
        grafico.setMaxVisibleValueCount(50);
        grafico.setPinchZoom(false);
        grafico.setDrawGridBackground(false);

        // Crea voci di dati per ogni giorno dell'anno
        Calendar calendario = Calendar.getInstance();
        float[] voci = new float[calendario.getActualMaximum(Calendar.DAY_OF_YEAR)];
        calendario.set(Calendar.YEAR, anno);
        calendario.set(Calendar.DAY_OF_YEAR, 1);

        // Crea set di dati per ogni mese con un colore specifico
        ArrayList<BarDataSet> setDati = new ArrayList<>();
        int[] colori = new int[]{Color.WHITE, Color.CYAN};
        int indiceMese = -1;
        BarDataSet setDato = null;

        visualizzaStatisticheViewModel.loadEntries(anno);

        for (int i = 0; i < voci.length; i++) {
            int mese = calendario.get(Calendar.MONTH);

            if (indiceMese != mese) {
                indiceMese = mese;
                setDato = new BarDataSet(new ArrayList<BarEntry>(), new DateFormatSymbols(Locale.getDefault()).getMonths()[mese]);
                setDato.setColor(colori[indiceMese % colori.length]);
                setDato.setValueTextColor(Color.GREEN);
                setDato.setDrawValues(false);
                setDati.add(setDato);
            }
            if (visualizzaStatisticheViewModel.verificaSeEntryEsiste((float) i)) {
                voci[i] = visualizzaStatisticheViewModel.getCoordinataYDaCoordinataX((float) i);
            }
            else {
                voci[i] = 0;
            }

            setDato.addEntry(new BarEntry(i, voci[i]));
            calendario.add(Calendar.DAY_OF_YEAR, 1);

            // Gestisce l'anno bisestile
            if (calendario.get(Calendar.YEAR) != anno && isAnnoBisestile(anno)) {
                calendario.set(Calendar.YEAR, anno);
                calendario.set(Calendar.MONTH, Calendar.FEBRUARY);
                calendario.set(Calendar.DAY_OF_MONTH, 29);
            }
        }

        // Crea un oggetto dati dai set di dati
        BarData datiGrafico = new BarData(setDati.toArray(new BarDataSet[0]));

        // Imposta attributi asse X
        XAxis asseX = grafico.getXAxis();
        asseX.setPosition(XAxis.XAxisPosition.BOTTOM);
        asseX.setGranularity(1f);
        asseX.setGranularityEnabled(true);
        asseX.setCenterAxisLabels(false);
        asseX.setTextColor(Color.WHITE);
        asseX.setTextSize(10);
        asseX.setAvoidFirstLastClipping(true);
        asseX.setXOffset(10);
        asseX.setValueFormatter(new ValueFormatter() {
            private final SimpleDateFormat formatoData = new SimpleDateFormat("dd MMM", Locale.getDefault());

            @Override
            public String getFormattedValue(float valore) {
                calendario.set(Calendar.DAY_OF_YEAR, (int) valore);
                Date data = calendario.getTime();
                return formatoData.format(data);
            }
        });

        // Imposta attributi asse Y
        YAxis asseYSinistro = grafico.getAxisLeft();
        asseYSinistro.setAxisMinimum(0f);
        asseYSinistro.setTextColor(Color.WHITE);
        asseYSinistro.setTextSize(10);

        YAxis asseYDestro = grafico.getAxisRight();
        asseYDestro.setEnabled(false);

        // Imposta attributi legenda e descrizione
        Legend legenda = grafico.getLegend();
        legenda.setEnabled(false);

        Description descrizione = grafico.getDescription();
        descrizione.setEnabled(false);

        // Imposta dati e invalida il grafico
        grafico.setData(datiGrafico);
        grafico.animateY(1000);

        // Imposta il listener per i gesti sul grafico
        avviaChartGestureListener(grafico, voci);
        aggiornaChartInfo(grafico, voci);

        grafico.invalidate();
    }


    private void avviaChartGestureListener(final BarChart chart, final float[] entries) {
        chart.setOnChartGestureListener(new OnChartGestureListener() {
            @Override
            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
            }

            @Override
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
                aggiornaChartInfo(chart, entries);
            }

            @Override
            public void onChartLongPressed(MotionEvent me) {
            }

            @Override
            public void onChartDoubleTapped(MotionEvent me) {
            }

            @Override
            public void onChartSingleTapped(MotionEvent me) {
            }

            @Override
            public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
            }

            @Override
            public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

            }

            @Override
            public void onChartTranslate(MotionEvent me, float dX, float dY) {
                aggiornaChartInfo(chart, entries);
            }
        });
    }



    private void aggiornaChartInfo(BarChart grafico, float[] dati) {
        // Ottieni l'intervallo sull'asse X del grafico
        float xMin = grafico.getLowestVisibleX();
        float xMax = grafico.getHighestVisibleX();

        // Mostra i valori di inizio e fine sull'asse X
        XAxis asseX = grafico.getXAxis();
        ValueFormatter formatter = asseX.getValueFormatter();
        String valoreXInizio = formatter.getFormattedValue(xMin);
        String valoreXFine = formatter.getFormattedValue(xMax);
        TextView textViewXInizio = visualizzaStatisticheBinding.dalValue;
        textViewXInizio.setText(String.format(Locale.getDefault(), "Giorno inizio: %s", valoreXInizio));
        TextView textViewXFine = visualizzaStatisticheBinding.alValue;
        textViewXFine.setText(String.format(Locale.getDefault(), "Giorno fine: %s", valoreXFine));

        // Trova gli indici dell'array dati corrispondenti all'intervallo visibile
        int indiceInizio = (int) Math.ceil(xMin);
        int indiceFine = (int) Math.floor(xMax);

        // Assicurati che gli indici siano all'interno dei limiti dell'array dati
        if (indiceInizio >= dati.length) {
            indiceInizio = dati.length - 1;
        } else if (indiceInizio < 0) {
            indiceInizio = 0;
        }
        if (indiceFine >= dati.length) {
            indiceFine = dati.length - 1;
        } else if (indiceFine < 0) {
            indiceFine = 0;
        }

        // Estrai i punti dati all'interno dell'intervallo visibile
        float[] datiVisibili = Arrays.copyOfRange(dati, indiceInizio, indiceFine + 1);

        // Calcola i valori massimi, minimi e medi basati sui dati visibili
        float massimo = getMassimo(datiVisibili);
        float minimo = getMinimo(datiVisibili);
        float somma = getSomma(datiVisibili);
        float mediana = getMedia(datiVisibili, somma);

        // Aggiorna le TextView nel layout
        TextView textViewMassimo = visualizzaStatisticheBinding.maxValue;
        textViewMassimo.setText(String.format(Locale.getDefault(), "Massimo: %.2f", massimo));

        TextView textViewMinimo = visualizzaStatisticheBinding.minValue;
        textViewMinimo.setText(String.format(Locale.getDefault(), "Minimo: %.2f", minimo));

        TextView textViewMediana = visualizzaStatisticheBinding.medianValue;
        textViewMediana.setText(String.format(Locale.getDefault(), "Medio: %.2f", mediana));

        TextView textViewSomma = visualizzaStatisticheBinding.sommaValue;
        textViewSomma.setText(String.format(Locale.getDefault(), "Totale intervallo: %.2f", somma));
    }


    private float getSomma(float[] dati) {
        float sum = 0;
        for (float dato : dati) {
            sum += dato;
        }
        return sum;
    }

    private float getMinimo(float[] dati) {
        if (dati == null || dati.length == 0) {
            return 0;
        }

        float min = Float.MAX_VALUE;
        boolean haValoreNonZero  = false;

        for (float dato : dati) {
            if (dato != 0) {
                haValoreNonZero  = true;
                if (dato < min) {
                    min = dato;
                }
            }
        }

        return haValoreNonZero  ? min : 0;
    }

    private float getMedia(float[] dati, float somma) {
        float[] datiNonZero = new float[dati.length];
        int count = 0;
        for (float dato : dati ) {
            if (dato != 0) {
                datiNonZero[count++] = dato;
            }
        }
        if (count == 0) {
            return 0;
        }

        datiNonZero = Arrays.copyOfRange(datiNonZero, 0, count);
        Arrays.sort(datiNonZero);
        int length = datiNonZero.length;
        if (length % 2 == 0) {
            return somma / length;
        } else {
            return datiNonZero[length / 2];
        }
    }

    public static float getMassimo(float[] dati) {
        if (dati == null || dati.length == 0) {
            return 0;
        }
        float max = Float.MIN_VALUE;
        for (float dato : dati) {
            if (dato > max) {
                max = dato;
            }
        }
        return max;
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