package com.rat.ratatouille23.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.rat.ratatouille23.R;
import com.rat.ratatouille23.databinding.FragmentVisualizzaStatisticheBinding;
import com.rat.ratatouille23.viewmodel.VisualizzaStatisticheViewModel;

import org.apache.commons.lang3.ArrayUtils;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;


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

        // Define the spinner
        Spinner yearSpinner = visualizzaStatisticheBinding.yearSpinner;

// Create an ArrayAdapter to populate the spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.year_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(adapter);

// Set the initial year to the first item in the spinner
        int initialYear = Integer.parseInt(yearSpinner.getSelectedItem().toString());

// Set the chart for the initial year
        impostaBarChart6(initialYear);

// Update the chart when a new year is selected in the spinner
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int year = Integer.parseInt(parent.getItemAtPosition(position).toString());
                impostaBarChart6(year);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        return fragmentView;
    }

    public static boolean isLeapYear(int year) {
        if (year % 4 == 0) {
            if (year % 100 == 0) {
                return year % 400 == 0;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }


    public void impostaBarChart5(int year) {
        BarChart chart = visualizzaStatisticheBinding.chart;

        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);
        chart.setMaxVisibleValueCount(50);
        chart.setPinchZoom(false);
        chart.setDrawGridBackground(false);

        // Create data entries for every day of the year
        Calendar calendar = Calendar.getInstance();
        float[] entries = new float[calendar.getActualMaximum(Calendar.DAY_OF_YEAR)];
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_YEAR, 1);

        // Create data sets for each month with a specific color
        ArrayList<BarDataSet> dataSets = new ArrayList<>();
        int[] colors = new int[]{Color.WHITE, Color.BLUE};
        int monthIndex = -1;
        BarDataSet dataSet = null;

        for (int i = 0; i < entries.length; i++) {
            int month = calendar.get(Calendar.MONTH);

            if (monthIndex != month) {
                monthIndex = month;
                dataSet = new BarDataSet(new ArrayList<BarEntry>(), new DateFormatSymbols(Locale.getDefault()).getMonths()[month]);
                dataSet.setColor(colors[monthIndex % colors.length]);
                dataSet.setValueTextColor(Color.GREEN);
                dataSet.setDrawValues(false);
                dataSets.add(dataSet);
            }

            entries[i] = new Random().nextInt((1500 - 100) + 1) + 100;
            dataSet.addEntry(new BarEntry(i, entries[i]));
            calendar.add(Calendar.DAY_OF_YEAR, 1);

            // Handle leap year
            if (calendar.get(Calendar.YEAR) != year && isLeapYear(year)) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, Calendar.FEBRUARY);
                calendar.set(Calendar.DAY_OF_MONTH, 29);
            }
        }

        // Create a data object from the data sets
        BarData lineData = new BarData(dataSets.toArray(new BarDataSet[0]));

        // Set X-axis attributes
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setCenterAxisLabels(false);
        xAxis.setValueFormatter(new ValueFormatter() {
            private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM", Locale.getDefault());

            @Override
            public String getFormattedValue(float value) {
                calendar.set(Calendar.DAY_OF_YEAR, (int) value + 1);
                Date date = calendar.getTime();
                return dateFormat.format(date);
            }
        });

        // Set Y-axis attributes
        YAxis yAxisLeft = chart.getAxisLeft();
        yAxisLeft.setAxisMinimum(0f);

        YAxis yAxisRight = chart.getAxisRight();
        yAxisRight.setEnabled(false);

        // Set legend and description attributes
        Legend legend = chart.getLegend();
        legend.setEnabled(false);

        Description description = chart.getDescription();
        description.setEnabled(false);

        // Set data and invalidate chart
        chart.setData(lineData);
        chart.animateY(1000);

        // Setup chart gesture listener
        setupChartGestureListener(chart, entries);

        chart.invalidate();
    }

    public void impostaBarChart6(int year) {
        BarChart chart = visualizzaStatisticheBinding.chart;

        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);
        chart.setMaxVisibleValueCount(50);
        chart.setPinchZoom(false);
        chart.setDrawGridBackground(false);

        // Create data entries for every day of the year
        Calendar calendar = Calendar.getInstance();
        float[] entries = new float[calendar.getActualMaximum(Calendar.DAY_OF_YEAR)];
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_YEAR, 1);

        // Create data sets for each month with a specific color
        ArrayList<BarDataSet> dataSets = new ArrayList<>();
        int[] colors = new int[]{Color.WHITE, Color.BLUE};
        int monthIndex = -1;
        BarDataSet dataSet = null;

        visualizzaStatisticheViewModel.loadEntries(year);

        for (int i = 0; i < entries.length; i++) {
            int month = calendar.get(Calendar.MONTH);

            if (monthIndex != month) {
                monthIndex = month;
                dataSet = new BarDataSet(new ArrayList<BarEntry>(), new DateFormatSymbols(Locale.getDefault()).getMonths()[month]);
                dataSet.setColor(colors[monthIndex % colors.length]);
                dataSet.setValueTextColor(Color.GREEN);
                dataSet.setDrawValues(false);
                dataSets.add(dataSet);
            }
            if (visualizzaStatisticheViewModel.checkEntryExists((float) i)) {
                entries[i] = visualizzaStatisticheViewModel.getYCoordinateFromXCoordinate((float) i);
            }
            else {
                entries[i] = 0;
            }

            dataSet.addEntry(new BarEntry(i, entries[i]));
            calendar.add(Calendar.DAY_OF_YEAR, 1);

            // Handle leap year
            if (calendar.get(Calendar.YEAR) != year && isLeapYear(year)) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, Calendar.FEBRUARY);
                calendar.set(Calendar.DAY_OF_MONTH, 29);
            }
        }

        // Create a data object from the data sets
        BarData lineData = new BarData(dataSets.toArray(new BarDataSet[0]));

        // Set X-axis attributes
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setCenterAxisLabels(false);
        xAxis.setValueFormatter(new ValueFormatter() {
            private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM", Locale.getDefault());

            @Override
            public String getFormattedValue(float value) {
                calendar.set(Calendar.DAY_OF_YEAR, (int) value + 1);
                Date date = calendar.getTime();
                return dateFormat.format(date);
            }
        });

        // Set Y-axis attributes
        YAxis yAxisLeft = chart.getAxisLeft();
        yAxisLeft.setAxisMinimum(0f);

        YAxis yAxisRight = chart.getAxisRight();
        yAxisRight.setEnabled(false);

        // Set legend and description attributes
        Legend legend = chart.getLegend();
        legend.setEnabled(false);

        Description description = chart.getDescription();
        description.setEnabled(false);

        // Set data and invalidate chart
        chart.setData(lineData);
        chart.animateY(1000);

        // Setup chart gesture listener
        setupChartGestureListener(chart, entries);

        chart.invalidate();
    }


    private void setupChartGestureListener(final BarChart chart, final float[] entries) {
        chart.setOnChartGestureListener(new OnChartGestureListener() {
            @Override
            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
            }

            @Override
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
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
                updateChartInfo(chart, entries);
            }

            @Override
            public void onChartTranslate(MotionEvent me, float dX, float dY) {
                updateChartInfo(chart, entries);
            }
        });
    }



    private void updateChartInfo(BarChart chart, float[] data) {
        // Get the X-axis range of the chart
        float xMin = chart.getLowestVisibleX();
        float xMax = chart.getHighestVisibleX();

        // Display the starting and ending X-axis values
        XAxis xAxis = chart.getXAxis();
        ValueFormatter formatter = xAxis.getValueFormatter();
        String startXValue = formatter.getFormattedValue(xMin);
        String endXValue = formatter.getFormattedValue(xMax);
        TextView startXTextView = visualizzaStatisticheBinding.dalValue;
        startXTextView.setText(String.format(Locale.getDefault(), "Start giorno: %s", startXValue));
        TextView endXTextView = visualizzaStatisticheBinding.alValue;
        endXTextView.setText(String.format(Locale.getDefault(), "End giorno: %s", endXValue));

        // Find the indices of the data array corresponding to the visible range
        int startIndex = (int) Math.ceil(xMin);
        int endIndex = (int) Math.floor(xMax);

        // Ensure that the indices are within the bounds of the data array
        if (startIndex >= data.length) {
            startIndex = data.length - 1;
        } else if (startIndex < 0) {
            startIndex = 0;
        }
        if (endIndex >= data.length) {
            endIndex = data.length - 1;
        } else if (endIndex < 0) {
            endIndex = 0;
        }

        // Extract the data points within the visible range
        float[] visibleData = Arrays.copyOfRange(data, startIndex, endIndex + 1);

        // Calculate the maximum, minimum, and median values based on the visible data
        float max = getMaximum(visibleData);
        float min = getMinimum(visibleData);
        float median = getMedian(visibleData);
        float sum = getSum(visibleData);

        // Update the TextViews in the layout
        TextView maxTextView = visualizzaStatisticheBinding.maxValue;
        maxTextView.setText(String.format(Locale.getDefault(), "Max: %.2f", max));

        TextView minTextView = visualizzaStatisticheBinding.minValue;
        minTextView.setText(String.format(Locale.getDefault(), "Min: %.2f", min));

        TextView medianTextView = visualizzaStatisticheBinding.medianValue;
        medianTextView.setText(String.format(Locale.getDefault(), "Medium: %.2f", median));

        TextView sumTextView = visualizzaStatisticheBinding.sommaValue;
        sumTextView.setText(String.format(Locale.getDefault(), "Somma: %.2f", sum));
    }

    private float getSum(float[] data) {
        float sum = 0;
        for (float value : data) {
            sum += value;
        }
        return sum;
    }



    // Returns the minimum value in the array
    private float getMinimum(float[] data) {
        if (data == null || data.length == 0) {
            return 0;
        }

        float min = Float.MAX_VALUE;
        boolean hasNonZeroValue = false;

        for (float value : data) {
            if (value != 0) {
                hasNonZeroValue = true;
                if (value < min) {
                    min = value;
                }
            }
        }

        return hasNonZeroValue ? min : 0;
    }

    private float getMedian(float[] data) {
        float[] nonZeroData = new float[data.length];
        int count = 0;
        for (float value : data) {
            if (value != 0) {
                nonZeroData[count++] = value;
            }
        }
        if (count == 0) {
            return 0;
        }
        nonZeroData = Arrays.copyOfRange(nonZeroData, 0, count);
        Arrays.sort(nonZeroData);
        int length = nonZeroData.length;
        if (length % 2 == 0) {
            return (nonZeroData[length / 2] + nonZeroData[length / 2 - 1]) / 2;
        } else {
            return nonZeroData[length / 2];
        }
    }

    public static float getMaximum(float[] values) {
        if (values == null || values.length == 0) {
            return 0;
        }
        float max = Float.MIN_VALUE;
        for (float value : values) {
            if (value > max) {
                max = value;
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