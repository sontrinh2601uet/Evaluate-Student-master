package com.evaluateStudent.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.evaluateStudent.R;
import com.evaluateStudent.structure.ConnectToData;
import com.evaluateStudent.structure.Standard;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class EvaluateHistoryFragment extends Fragment {

    private HashMap<String, ArrayList<Pair>> evaluateHistory;
    private double sumPoint = 0;

    private TextView sumPointText;

    public static EvaluateHistoryFragment createInstance(String studentId) {
        EvaluateHistoryFragment login = new EvaluateHistoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("studentId", studentId);
        login.setArguments(bundle);

        return login;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.evaluate_history_fragment, container, false);
        LinearLayout chartView = view.findViewById(R.id.list_chart);
        sumPointText = view.findViewById(R.id.sum_point_to_current);

        if (getDataEvaluateOfStudent()) {
            createChart(chartView, inflater);
        }
        return view;
    }

    private boolean getDataEvaluateOfStudent() {
        evaluateHistory = new HashMap<>();
        String temp = ConnectToData.getDataFile();
        String[] data;
        if (temp == null) {
            return false;
        }

        data = temp.split(";");

        for (String datum : data) {
            String[] fracture = datum.substring(0, datum.length() - 1).split(",");
            String[] listStandardPoint = fracture[4].split("\\$");


            for (String standard : listStandardPoint) {
                if(standard.equals("")) continue;
                String[] pair = standard.split("~");

                if (!evaluateHistory.containsKey(pair[0])) {
                    evaluateHistory.put(pair[0].replace("$", ""), new ArrayList<Pair>());
                }

               evaluateHistory.get(pair[0].replace("$", "")).add(new Pair(fracture[0], Float.valueOf(pair[1])));
            }
        }

        return true;
    }

    private void createChart(LinearLayout view, LayoutInflater inflater) {
        LinearLayout chartView;
        Standard standard;
        int divine = 0;
        for (Map.Entry<String, ArrayList<Pair>> dataEachChart : evaluateHistory.entrySet()) {
            try {
                int idStandard = Integer.valueOf(dataEachChart.getKey());
                standard = ConnectToData.getStandardById(idStandard);
                divine++;
            } catch (Exception e) {
                continue;
            }

            chartView = (LinearLayout) inflater.inflate(R.layout.chart_layout, null);
            ((TextView) chartView.findViewById(R.id.standard_content)).setText(standard.getContent());
            ((TextView) chartView.findViewById(R.id.point_of_standard)).setText(getString(R.string.evaluate_history_sum_standard, calculatePoint(dataEachChart.getValue())));
            sumPoint += Double.valueOf(calculatePoint(dataEachChart.getValue()));
            LineChart lineChart = chartView.findViewById(R.id.standard_chart);

            setDataForChart(lineChart, dataEachChart.getValue());
            view.addView(chartView);
        }

        sumPointText.setText(getString(R.string.evaluate_history_sum_point, String.valueOf(sumPoint / (double)divine)));
    }

    private void setDataForChart(LineChart lineChart, ArrayList<Pair> data) {
        lineChart.setScaleEnabled(true);
        lineChart.setPinchZoom(true);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        YAxis yAxisRight = lineChart.getAxisRight();
        yAxisRight.setEnabled(false);

        ArrayList<Entry> NoOfEmp = new ArrayList<>();
        final ArrayList<String> point = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            Pair datum = data.get(i);
            NoOfEmp.add(new Entry(i, datum.getPoint()));
            point.add(datum.getDate());
        }

        IAxisValueFormatter formatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return point.get((int) value);
            }
        };

        LineDataSet dataSet = new LineDataSet(NoOfEmp, "Thời gian");
        LineData dataChart = new LineData(dataSet);
        xAxis.setValueFormatter(formatter);
        lineChart.setData(dataChart);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        lineChart.animateXY(500, 500);
        lineChart.setDescription(null);
    }

    private String calculatePoint(ArrayList<Pair> data) {
        double point = 0;
        for (Pair datum : data) {
            point += datum.getPoint();
        }

        point = point / (double) data.size();
        return new DecimalFormat("#.##").format(point);
    }

    private class Pair implements Comparable<Pair>{
        private String date;
        private Float point;

        Pair (String date, Float point) {
            this.date = date;
            this.point = point;
        }

        public String getDate() {
            return date.split(" ")[0];
        }

        public Float getPoint() {
            return point;
        }


        @Override
        public int compareTo(Pair o) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date strDate, compareTo;
            try {
                strDate = sdf.parse(this.date);
                compareTo = sdf.parse(o.date);
            } catch (ParseException e) {
                return 0;
            }


            if (strDate.after(compareTo)) {
                return 1;
            }
            return 0;
        }
    }
}
