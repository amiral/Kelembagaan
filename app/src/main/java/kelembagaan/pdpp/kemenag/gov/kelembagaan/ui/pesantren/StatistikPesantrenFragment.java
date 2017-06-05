package kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.pesantren;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import kelembagaan.pdpp.kemenag.gov.kelembagaan.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatistikPesantrenFragment extends Fragment {


    private PieChart pieChart;
    private RelativeLayout mainLayout;
    private float[] yData = {100, 350, 150};
    private  String[] xData = {"MTS","MA", "Perguruan"};
    public StatistikPesantrenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statistik_pesantren, container, false);

//        mainLayout = (RelativeLayout) view.findViewById(R.id.layout_statistik) ;
//        pieChart = new PieChart(getContext());
//        ViewGroup.LayoutParams params = pieChart.getLayoutParams();
//        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
//        params.width = ViewGroup.LayoutParams.MATCH_PARENT;

//        mainLayout.addView(pieChart);

        pieChart = (PieChart) view.findViewById(R.id.chart);
        //configure piechart
        pieChart.setUsePercentValues(true);
//        Description description = new Description();
//        description.setText("Jumlah Santri Perjenjang");
//        description.
        pieChart.getDescription().setEnabled(false);

        pieChart.setDrawHoleEnabled(false);

        //Enable rotation of the chart by touch
        pieChart.setRotationAngle(0);
        pieChart.setRotationEnabled(false);

        //Char value listener
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                //display msg whe value selected
                if (e == null)
                    return;

                Toast.makeText(getContext(), e.getData() + " = " + e.getY(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

        addDataPie();

        return view;
    }

    private void addDataPie(){
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        entries.add(new PieEntry(100f, 0));
        entries.add(new PieEntry(300f, 1));
        entries.add(new PieEntry(130f, 2));

        PieDataSet dataSet = new PieDataSet(entries, " Data Santri");

        //Add color
        ArrayList<Integer> colors = new ArrayList<Integer>();

//        for (int c : ColorTemplate.VORDIPLOM_COLORS)
//            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

//        for (int c : ColorTemplate.COLORFUL_COLORS)
//            colors.add(c);

//        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);



        ArrayList<String> labels = new ArrayList<String>();
        labels.add("MTS");
        labels.add("MA");
        labels.add("Perguruan");

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLUE);

        pieChart.setData(data);

        // undo all highlights
        pieChart.highlightValues(null);

        // update pie chart
        pieChart.invalidate();


        //Legend
        Legend l = pieChart.getLegend();
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);

        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_CENTER);


        List<LegendEntry> legendEntries = new ArrayList<>();

        for (int i = 0; i < labels.size(); i++) {
            LegendEntry entry = new LegendEntry();
            entry.formColor = colors.get(i);
            entry.label = labels.get(i);
            legendEntries.add(entry);
        }

        l.setCustom(legendEntries);
    }
    private void addData(){
        ArrayList<PieEntry> yVals = new ArrayList<PieEntry>();

        for (int i = 0; i < yData.length ; i++)
            yVals.add(new PieEntry(yData[i],i));


        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < xData.length ; i++)
            xVals.add(xData[i]);


        PieDataSet dataSet = new PieDataSet(yVals, "Data Santri");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);



        //Add color
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
                colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);


        // instantiate pie data object now
        PieData data = new PieData();
//        data.set
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.GRAY);

        pieChart.setData(data);

        // undo all highlights
        pieChart.highlightValues(null);

        // update pie chart
        pieChart.invalidate();


    }

}
