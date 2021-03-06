package kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.pesantren;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local.LembagaDbHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local.StatisikLembagaDbHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local.StatistikPesantrenDbHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Lembaga;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Pesantren;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.StatistikLembaga;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.StatistikPesantren;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.utils.GlobalData;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatistikPesantrenFragment extends Fragment {


    private PieChart pieChartJenjang, pieChartMukim, pieChartKelamin;
    private RelativeLayout mainLayout;
    private StatistikPesantren statistik;
    private Pesantren pesantren;

    TextView tvNoData;

    public StatistikPesantrenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statistik_pesantren, container, false);
        pesantren = Parcels.unwrap(getArguments().getParcelable("pesantren"));
        StatistikPesantrenDbHelper helper = new StatistikPesantrenDbHelper(getContext());
        statistik = helper.getStatistikPesantren(pesantren.getIdPesantren());

        pieChartJenjang = (PieChart) view.findViewById(R.id.chartJenjang);
        pieChartMukim = (PieChart) view.findViewById(R.id.chartMukim);
        pieChartKelamin = (PieChart) view.findViewById(R.id.chartJenisKelamin);

        tvNoData = (TextView) view.findViewById(R.id.text_no_data);

        configPieChart(pieChartJenjang);
        configPieChart(pieChartMukim);
        configPieChart(pieChartKelamin);

        addDataPieJenjang();
        addDataPieMukim();
        addDataPieKelamin();

        return view;
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

    private void addDataPieJenjang(){
        LembagaDbHelper helper = new LembagaDbHelper(getContext());
        List<Lembaga> lsLembaga = helper.getAllLembagaPesantren(pesantren.getNspp());

        if (lsLembaga.size() > 0){

            ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
            final ArrayList<String> labels = new ArrayList<String>();

            for (int i = 0 ; i < lsLembaga.size(); i++){
                Lembaga lembaga = lsLembaga.get(i);
                StatistikLembaga statistik = new StatisikLembagaDbHelper(getContext()).getStatistikLembaga(lembaga.getIdLembaga());
                int totalSantri = statistik.getJumlahSantri().getPria() + statistik.getJumlahSantri().getWanita();
                int jenjang = lembaga.getIdJenjangLembaga();
                if (jenjang == 0 )
                    jenjang = 1;
                String tipeJenjang = GlobalData.NAMA_JENJANG[jenjang-1];

                entries.add(new PieEntry((float) totalSantri, i));
                labels.add(i, tipeJenjang);
            }
//            entries.add(new PieEntry(100f, 0));
//            entries.add(new PieEntry(300f, 1));
//            entries.add(new PieEntry(230f, 2));
//            entries.add(new PieEntry(150f, 3));

            PieDataSet dataSet = new PieDataSet(entries, " Data Santri");

            //Add color
            ArrayList<Integer> colors = new ArrayList<Integer>();

            for (int c : ColorTemplate.JOYFUL_COLORS)
                colors.add(c);

            dataSet.setColors(colors);




//            labels.add("MI");
//            labels.add("MTS");
//            labels.add("MA");
//            labels.add("Perguruan");

            PieData data = new PieData(dataSet);
            data.setValueFormatter(new PercentFormatter());
            data.setValueTextSize(11f);
            data.setValueTextColor(Color.BLUE);

            pieChartJenjang.setData(data);

            // undo all highlights
            pieChartJenjang.highlightValues(null);

            // update pie chart
            pieChartJenjang.invalidate();


            //Legend
            Legend l = pieChartJenjang.getLegend();
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
            pieChartJenjang.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, Highlight h) {
                    //display msg whe value selected
                    if (e == null)
                        return;

                    Toast.makeText(getContext(), labels.get((Integer) e.getData()) + " = " +  e.getY(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected() {

                }
            });
        }else{
            pieChartJenjang.setVisibility(View.GONE);
            tvNoData.setVisibility(View.VISIBLE);
        }

    }

    private void addDataPieMukim(){

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        entries.add(new PieEntry((float) statistik.getJumlahSantriMukim().getMukim(), 0));
        entries.add(new PieEntry((float) statistik.getJumlahSantriMukim().getTidakMukim(), 1));

        PieDataSet dataSet = new PieDataSet(entries, " Data Santri");

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

        PieDataSet dataSet = new PieDataSet(entries, " Data Santri");

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
