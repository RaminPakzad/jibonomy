package sadad.com.jibonomy;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import sadad.com.jibonomy.biz.adapter.TransactionGlimpseAdapter;
import sadad.com.jibonomy.entities.Transaction;
import sadad.com.jibonomy.services.TransactionService;

/**
 * @author ramin pakzad (RPakzadmanesh@gmail.com) on 1/13/2019.
 */
public class HomeFragment extends Fragment {
    View rootView;
    SharedPreferences prefs;
    private PieChart chart;
    private RecyclerView transactionGlimpseList;
    private TransactionGlimpseAdapter transactionGlimpseAdapter;
    private TransactionService transactionService;
    private TextView monthNameText, appBalanceTextView;
    private View centerView;
    private String[] jaliliMonthList = {"", "فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد", "شهریور", "مهر", "آبان", "آذر", "دی", "بهمن", "اسفند"};
    private int currentPosition = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.home_fragment, container, false);

        prefs = getContext().getSharedPreferences("user", getContext().MODE_PRIVATE);
        DecimalFormat df = new DecimalFormat("#,###");
        appBalanceTextView = (TextView) rootView.findViewById(R.id.app_balance);
        appBalanceTextView.setText(df.format(prefs.getLong("appBalance", 0)) + " ریال");

        transactionGlimpseList = rootView.findViewById(R.id.transactionGlimpseList);
        chart = rootView.findViewById(R.id.overallExpenseChart);
        List<Transaction> transactions = new ArrayList<>();
        transactionService = new TransactionService(getContext());

        // chart center view

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
        if (transactions.size() >= 8) {
            transactions = transactions.subList(0, 7);
        }

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
        chart.setHoleRadius(58f);
        chart.setTransparentCircleRadius(61f);
        chart.setRotationAngle(0);
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


    private void setData() {
        ArrayList<PieEntry> entries = new ArrayList<>();

        List<PieEntry> chartData = transactionService.getChartData(currentPosition, true);
        PieDataSet dataSet = new PieDataSet(chartData, "Election Results");
        dataSet.setDrawIcons(false);
        chart.getLegend().setEnabled(false);
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

    public void nextMonth() {
        if (currentPosition == 12) {
            currentPosition = 1;
        } else {
            currentPosition++;
        }
        monthNameText.setText(jaliliMonthList[currentPosition]);
        setData();
    }

    public void preMonth() {
        if (currentPosition == 1) {
            currentPosition = 12;
        } else {
            currentPosition--;
        }
        monthNameText.setText(jaliliMonthList[currentPosition]);
        setData();
    }
}
