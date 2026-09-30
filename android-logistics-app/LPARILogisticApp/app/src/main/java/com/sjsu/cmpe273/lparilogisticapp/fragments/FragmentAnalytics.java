package com.sjsu.cmpe273.lparilogisticapp.fragments;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.sjsu.cmpe273.lparilogisticapp.R;

import java.util.ArrayList;
import java.util.List;


public class FragmentAnalytics extends Fragment {

    private float[] yData = { 57.5f, 26.8f, 15.7f };
    private String[] xData = { "Low Caution", "Medium Alert", "High Alert"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.AppTheme_Light);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);


        View rootView = localInflater.inflate(R.layout.fragment_analytics_overall, container, false);


        PieChart pieChart = (PieChart) rootView.findViewById(R.id.chart4);
        SetPieChart(pieChart);


        BarChart chart = (BarChart) rootView.findViewById(R.id.chart);
        setBarChart(chart);



        LineChart lineChart = (LineChart) rootView.findViewById(R.id.chart3);
        setLineChart(lineChart);

        HorizontalBarChart chart2 = (HorizontalBarChart) rootView.findViewById(R.id.chart2);
        BarData data = new BarData(getXAxisValues(), getDataSet());
        chart2.setData(data);
        chart2.setDescription("My Chart");
        chart2.animateXY(2000, 2000);
        chart2.invalidate();






        return rootView;
    }


    //pie chart
    private void SetPieChart(PieChart pieChart) {
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        for (int i = 0; i < yData.length; i++)
            yVals1.add(new Entry(yData[i], i));

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < xData.length; i++)
            xVals.add(xData[i]);

        // create pie data set
        PieDataSet dataSet = new PieDataSet(yVals1, " ");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        // add many colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        dataSet.setColors(colors);

        // instantiate pie data object now
        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.setDescription("Different Types of Delivery");

        // undo all highlights
        //pieChart.highlightValues(null);

        pieChart.animateY(5000);
        // update pie chart
        pieChart.invalidate();
    }


    //Bar Chart
    private void setBarChart(BarChart chart) {

        //List<IBarDataSet> dataSetSample = getDataSet();
        List<IBarDataSet> dataSets = new ArrayList<>();
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(11.000f, 0); // Jan
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(4.000f, 1); // Feb
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(6.000f, 2); // Mar
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(3.000f, 3); // Apr
        valueSet1.add(v1e4);

        ArrayList<BarEntry> valueSet2 = new ArrayList<>();
        BarEntry v2e1 = new BarEntry(15.000f, 0); // Jan
        valueSet2.add(v2e1);
        BarEntry v2e2 = new BarEntry(9.000f, 1); // Feb
        valueSet2.add(v2e2);
        BarEntry v2e3 = new BarEntry(12.000f, 2); // Mar
        valueSet2.add(v2e3);
        BarEntry v2e4 = new BarEntry(6.000f, 3); // Apr
        valueSet2.add(v2e4);

        ArrayList<BarEntry> valueSet3 = new ArrayList<>();
        BarEntry v3e1 = new BarEntry(8.000f, 0); // Jan
        //valueSet3.add(v3e1);
        BarEntry v3e2 = new BarEntry(6.000f, 1); // Feb
        valueSet3.add(v3e2);
        BarEntry v3e3 = new BarEntry(9.000f, 2); // Mar
        valueSet3.add(v3e3);
        BarEntry v3e4 = new BarEntry(2.000f, 3); // Apr
        valueSet3.add(v3e4);

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Food Items");
        barDataSet1.setColor(Color.rgb(0, 155, 0));

        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Electronic Items");
        //barDataSet2.setColor(Color.YELLOW);
        barDataSet2.setColor(Color.rgb(75, 0, 130));

        BarDataSet barDataSet3 = new BarDataSet(valueSet3, "miscellaneous");
        barDataSet3.setColors(ColorTemplate.COLORFUL_COLORS);

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);
        dataSets.add(barDataSet3);




        // List<String> xAxis = getXAxisValues();
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("JAN");
        xAxis.add("FEB");
        xAxis.add("MAR");
        xAxis.add("APR");
        BarData data = new BarData(xAxis, dataSets);
        chart.setData(data);
        chart.setDescription(" ");
        chart.animateXY(5000, 5000);
        chart.invalidate();
    }



    //Line Chart
    private void setLineChart(LineChart lineChart) {
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(16f, 0));
        entries.add(new Entry(29f, 1));
        entries.add(new Entry(19f, 2));
        entries.add(new Entry(12f, 3));
       // entries.add(new Entry(18f, 4));
       // entries.add(new Entry(9f, 5));

        LineDataSet dataset = new LineDataSet(entries, "# of Package loss in Each month");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");

        LineData data3 = new LineData(labels, dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
        dataset.setDrawCubic(true);
        dataset.setDrawFilled(true);

        lineChart.setData(data3);
        lineChart.animateY(5000);
    }




    private ArrayList<IBarDataSet> getDataSet() {
        ArrayList<IBarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(11.000f, 0); // Jan

        BarEntry v1e2 = new BarEntry(4.000f, 1); // Feb
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(6.000f, 2); // Mar
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(3.000f, 3); // Apr
        valueSet1.add(v1e4);

        ArrayList<BarEntry> valueSet2 = new ArrayList<>();
        BarEntry v2e1 = new BarEntry(15.000f, 0); // Jan
        valueSet2.add(v2e1);
        BarEntry v2e2 = new BarEntry(9.000f, 1); // Feb
        valueSet2.add(v2e2);
        BarEntry v2e3 = new BarEntry(12.000f, 2); // Mar
        valueSet2.add(v2e3);
        BarEntry v2e4 = new BarEntry(6.000f, 3); // Apr
        valueSet2.add(v2e4);



        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Alert Zone");
        barDataSet1.setColor(Color.RED);
        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Safe Zone");
        barDataSet1.setColor(Color.GREEN);


        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);

        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("JAN");
        xAxis.add("FEB");
        xAxis.add("MAR");
        xAxis.add("APR");

        return xAxis;
    }
}
