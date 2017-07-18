package kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.madrasah;


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

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import kelembagaan.pdpp.kemenag.gov.kelembagaan.R;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local.StatisikLembagaDbHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Lembaga;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.StatistikLembaga;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatistikMadrasahFragment extends Fragment {

    private PieChart pieChartMukim, pieChartKelamin;
    private RelativeLayout mainLayout;
    private StatistikLembaga statistik;
    private Lembaga madrasah;


    public StatistikMadrasahFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statistik_madrasah, container, false);
        madrasah = Parcels.unwrap(getArguments().getParcelable("madrasah"));

        StatisikLembagaDbHelper helper = new StatisikLembagaDbHelper(getContext());
        statistik = helper.getStatistikLembaga(madrasah.getIdLembaga());
        pieChartMukim = (PieChart) view.findViewById(R.id.chartMukim);
        pieChartKelamin = (PieChart) view.findViewById(R.id.chartJenisKelamin);
        configPieChart(pieChartMukim);
        configPieChart(pieChartKelamin);

        addDataPieMukim();
        addDataPieKelamin();
        return  view;
    }

    private void configPieChart(PieChart pChart){
        //configure piechart
        pChart.setUsePercentValues(true);
        pChart.getDescription().setEnabled(false);
        pChart.setDrawHoleEnabled(false);
        //Enable rotation of the chart by touch
        pChart.setRotationAngle(0);
        pChart.setRotationEnabled(false);

    }

    private void addDataPieMukim(){

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        entries.add(new PieEntry((float) statistik.getJumlahSantriMukim().getMukim(), 0));
        entries.add(new PieEntry((float) statistik.getJumlahSantriMukim().getTidakMukim(), 1));

        PieDataSet dataSet = new PieDataSet(entries, " Data Siswa");

        //Add color
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        dataSet.setColors(colors);



        final ArrayList<String> labels = new ArrayList<String>();
        labels.add("Mukim");
        labels.add("Tidak Mukim");

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLUE);

        pieChartMukim.setData(data);

        // undo all highlights
        pieChartMukim.highlightValues(null);

        // update pie chart
        pieChartMukim.invalidate();


        //Legend
        Legend l = pieChartMukim.getLegend();
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

        //Char value listener
        pieChartMukim.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                //display msg whe value selected
                if (e == null)
                    return;

                Toast.makeText(getContext(), labels.get((Integer) e.getData()) + " = " + e.getY(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    private void addDataPieKelamin(){
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        entries.add(new PieEntry((float) statistik.getJumlahSantri().getWanita(), 0));
        entries.add(new PieEntry((float) statistik.getJumlahSantri().getPria(), 1));

        PieDataSet dataSet = new PieDataSet(entries, " Data Siswa");

        //Add color
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.MATERIAL_COLORS)
            colors.add(c);

        dataSet.setColors(colors);



        final ArrayList<String> labels = new ArrayList<String>();
        labels.add("Wanita");
        labels.add("Pria");

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLUE);

        pieChartKelamin.setData(data);

        // undo all highlights
        pieChartKelamin.highlightValues(null);

        // update pie chart
        pieChartKelamin.invalidate();


        //Legend
        Legend l = pieChartKelamin.getLegend();
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

        //Char value listener
        pieChartKelamin.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                //display msg whe value selected
                if (e == null)
                    return;

                Toast.makeText(getContext(), labels.get((Integer) e.getData()) + " = " + e.getY(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

}
