package sadad.com.jibonomy;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sadad.com.jibonomy.biz.adapter.TransactionGlimpseAdapter;
import sadad.com.jibonomy.entities.Transaction;

/**
 * @author ramin pakzad (RPakzadmanesh@gmail.com) on 1/13/2019.
 */
public class HomeFragment extends Fragment {
    View rootView;

    private PieChart chart;
    private RecyclerView transactionGlimpseList;
    private TransactionGlimpseAdapter transactionGlimpseAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.home_fragment, container, false);
        transactionGlimpseList = rootView.findViewById(R.id.transactionGlimpseList);
        chart = rootView.findViewById(R.id.overallExpenseChart);
        List<Transaction> transactions = new ArrayList<>();
/*

        transactions.add(new Transaction(1l, new Date(), "test", (byte) 1, new BigDecimal(12000)));
        transactions.add(new Transaction(2l, new Date(), "test", (byte) 1, new BigDecimal(1000)));
        transactions.add(new Transaction(3l, new Date(), "test", (byte) 1, new BigDecimal(5000)));
        transactions.add(new Transaction(4l, new Date(), "test", (byte) 1, new BigDecimal(2000)));
        transactions.add(new Transaction(5l, new Date(), "test", (byte) 1, new BigDecimal(78000)));
*/


        transactionGlimpseAdapter = new TransactionGlimpseAdapter(transactions, rootView.getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(rootView.getContext());
        transactionGlimpseList.setLayoutManager(mLayoutManager);
        transactionGlimpseList.setItemAnimator(new DefaultItemAnimator());
        transactionGlimpseList.setAdapter(transactionGlimpseAdapter);
        initialChart();


        return rootView;
    }

    private void initialChart() {

        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);

        chart.setDragDecelerationFrictionCoef(0.95f);

//        chart.setCenterTextTypeface(tfLight);
        chart.setCenterText(generateCenterSpannableText());

        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.WHITE);

        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);

        chart.setHoleRadius(58f);
        chart.setTransparentCircleRadius(61f);

        chart.setDrawCenterText(true);

        chart.setRotationAngle(0);
        // enable rotation of the chart by touch
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);
        chart.setDrawEntryLabels(false);

        // chart.setUnit(" €");
        // chart.setDrawUnitsInChart(true);

        // add a selection listener
        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Toast.makeText(rootView.getContext(), "onValueSelected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {
                Toast.makeText(rootView.getContext(), "onNothingSelected", Toast.LENGTH_SHORT).show();
            }
        });

        setData(10, 2.3f);
    }

    private SpannableString generateCenterSpannableText() {

//        String mpAndroidChart = "MPAndroidChart";
        String mpAndroidChart = "هزینه 120000";
//        String s1 = "developed by Philipp Jahoda";
        String s1 = "درآمد1500000";
        SpannableString s = new SpannableString(mpAndroidChart + "\n" + s1);
//        s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
//        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
//        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
//        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
//        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
//        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }


    private void setData(int count, float range) {
        ArrayList<PieEntry> entries = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        entries.add(new PieEntry(2.5f, "خیار"));
        entries.add(new PieEntry(3.5f, "second"));
        entries.add(new PieEntry(4.5f, "third"));
        entries.add(new PieEntry(5.5f, "forth"));
        entries.add(new PieEntry(3.5f, "fifth"));

        PieDataSet dataSet = new PieDataSet(entries, "Election Results");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);

        chart.setData(data);
        // undo all highlights
        chart.highlightValues(null);
        chart.invalidate();
    }
}
