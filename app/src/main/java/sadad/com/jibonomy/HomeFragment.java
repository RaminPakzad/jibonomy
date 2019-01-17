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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import sadad.com.jibonomy.biz.adapter.TransactionGlimpseAdapter;
import sadad.com.jibonomy.biz.dto.ChartDataDto;
import sadad.com.jibonomy.entities.Transaction;
import sadad.com.jibonomy.services.TransactionService;

/**
 * @author ramin pakzad (RPakzadmanesh@gmail.com) on 1/13/2019.
 */
public class HomeFragment extends Fragment {
    View rootView;

    private PieChart chart;
    private RecyclerView transactionGlimpseList;
    private TransactionGlimpseAdapter transactionGlimpseAdapter;
    private TransactionService transactionService;
    private TextView monthNameText;
    private View centerView;

    private String[] jaliliMonthList = {"","فروردین","اردیبهشت","خرداد","تیر","مرداد","شهریور","مهر","آبان","آذر","دی","بهمن","اسفند"};
    private int currentPosition = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.home_fragment, container, false);

        transactionGlimpseList = rootView.findViewById(R.id.transactionGlimpseList);

        chart = rootView.findViewById(R.id.overallExpenseChart);

        List<Transaction> transactions = new ArrayList<>();
        transactionService = new TransactionService(getContext());

        // month controller
        monthNameText = (TextView) rootView.findViewById(R.id.monthName);
        ImageButton next = (ImageButton) rootView.findViewById(R.id.next_month);
        ImageButton pre = (ImageButton) rootView.findViewById(R.id.pre_month);
        centerView = (View) rootView.findViewById(R.id.center_view_data);
        centerView.bringToFront();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextMonth();
            }
        });

        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preMonth();
            }
        });


        transactions = transactionService.getTransactiones();
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
        //chart.setCenterText(generateCenterSpannableText());

        //chart.setDrawHoleEnabled(true);
        //chart.setHoleColor(Color.WHITE);

        //chart.setTransparentCircleColor(Color.WHITE);
        //chart.setTransparentCircleAlpha(110);

        chart.setHoleRadius(58f);
        chart.setTransparentCircleRadius(61f);

        //chart.setDrawCenterText(true);

        chart.setRotationAngle(0);
        //chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);
        chart.setDrawEntryLabels(false);

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
        setData();
    }

    private SpannableString generateCenterSpannableText() {
        String mpAndroidChart = "هزینه 120000";
        String s1 = "درآمد1500000";
        SpannableString s = new SpannableString(mpAndroidChart + "\n" + s1);
        return s;
    }


    private void setData() {
        ArrayList<PieEntry> entries = new ArrayList<>();

        List<PieEntry> chartData = transactionService.getChartData();
        PieDataSet dataSet = new PieDataSet(chartData, "Election Results");
        dataSet.setDrawIcons(false);
        chart.getLegend().setEnabled(false);
        //dataSet.setSliceSpace(3f);
        //dataSet.setIconsOffset(new MPPointF(0, 40));
        //dataSet.setSelectionShift(5f);
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
        chart.highlightValues(null);
        chart.invalidate();
    }

    public void nextMonth(){
        if(currentPosition==12){
            currentPosition = 1;
        } else {
            currentPosition++;
        }
        monthNameText.setText(jaliliMonthList[currentPosition]);
    }

    public void preMonth(){
        if(currentPosition==1){
            currentPosition = 12;
        } else {
            currentPosition--;
        }
        monthNameText.setText(jaliliMonthList[currentPosition]);
    }
}
