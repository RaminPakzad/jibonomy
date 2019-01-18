package sadad.com.jibonomy;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.model.GradientColor;
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
public class DashboardFragment extends Fragment {
    View rootView;
    SharedPreferences prefs;
    private PieChart chart;
    private BarChart barChart;

    private TransactionGlimpseAdapter transactionGlimpseAdapter;
    private TransactionService transactionService;
    private TextView monthNameText, appBalanceTextView;
    private View centerView;
    private String[] jaliliMonthList = {"", "فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد", "شهریور", "مهر", "آبان", "آذر", "دی", "بهمن", "اسفند"};
    private int currentPosition = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dashboard_fragment, container, false);

        prefs = getContext().getSharedPreferences("user", getContext().MODE_PRIVATE);
        DecimalFormat df = new DecimalFormat("#,###");
        appBalanceTextView = (TextView) rootView.findViewById(R.id.app_balance);
        appBalanceTextView.setText(df.format(prefs.getLong("appBalance", 0)) + " ریال");


        chart = rootView.findViewById(R.id.overallExpenseChart);
        barChart = rootView.findViewById(R.id.overallExpenseChartBar);
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
        initialPieChart();
        initialBarChart();
        return rootView;
    }

    private void initialPieChart() {
        PieChart chart = this.chart;
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
        setPieData();
    }

    private void initialBarChart() {
        BarChart barChart = this.barChart;
//        barChart.setUsePercentValues(true);
        barChart.getDescription().setEnabled(false);
        barChart.setExtraOffsets(5, 10, 5, 5);
        barChart.setDragDecelerationFrictionCoef(0.95f);
//        barChart.setHoleRadius(58f);
//        barChart.setTransparentCircleRadius(61f);
//        barChart.setRotationAngle(0);
        barChart.setHighlightPerTapEnabled(true);
//        barChart.setDrawEntryLabels(false);

        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Toast.makeText(rootView.getContext(), "onValueSelected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {
                Toast.makeText(rootView.getContext(), "onNothingSelected", Toast.LENGTH_SHORT).show();
            }
        });
        setBarData(5, 10);
    }


    private void setPieData() {
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

    private void setBarData(int count, float range) {

        float start = 1f;

        ArrayList<BarEntry> values = new ArrayList<>();

        for (int i = (int) start; i < start + count; i++) {
            float val = (float) (Math.random() * (range + 1));

            if (Math.random() * 100 < 25) {
                values.add(new BarEntry(i, val));
            } else {
                values.add(new BarEntry(i, val));
            }
        }

        BarDataSet set1;

        if (barChart.getData() != null &&
                barChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) barChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();

        } else {
            set1 = new BarDataSet(values, monthNameText.getText().toString());
            set1.setDrawIcons(false);

//            set1.setColors(ColorTemplate.MATERIAL_COLORS);

            /*int startColor = ContextCompat.getColor(this, android.R.color.holo_blue_dark);
            int endColor = ContextCompat.getColor(this, android.R.color.holo_blue_bright);
            set1.setGradientColor(startColor, endColor);*/

            int startColor1 = ContextCompat.getColor(rootView.getContext(), android.R.color.holo_orange_light);
            int startColor2 = ContextCompat.getColor(rootView.getContext(), android.R.color.holo_blue_light);
            int startColor3 = ContextCompat.getColor(rootView.getContext(), android.R.color.holo_orange_light);
            int startColor4 = ContextCompat.getColor(rootView.getContext(), android.R.color.holo_green_light);
            int startColor5 = ContextCompat.getColor(rootView.getContext(), android.R.color.holo_red_light);
            int endColor1 = ContextCompat.getColor(rootView.getContext(), android.R.color.holo_blue_dark);
            int endColor2 = ContextCompat.getColor(rootView.getContext(), android.R.color.holo_purple);
            int endColor3 = ContextCompat.getColor(rootView.getContext(), android.R.color.holo_green_dark);
            int endColor4 = ContextCompat.getColor(rootView.getContext(), android.R.color.holo_red_dark);
            int endColor5 = ContextCompat.getColor(rootView.getContext(), android.R.color.holo_orange_dark);

            List<GradientColor> gradientColors = new ArrayList<>();
            gradientColors.add(new GradientColor(startColor1, endColor1));
            gradientColors.add(new GradientColor(startColor2, endColor2));
            gradientColors.add(new GradientColor(startColor3, endColor3));
            gradientColors.add(new GradientColor(startColor4, endColor4));
            gradientColors.add(new GradientColor(startColor5, endColor5));

            set1.setGradientColors(gradientColors);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.9f);

            barChart.setData(data);
        }
    }

    public void nextMonth() {
        if (currentPosition == 12) {
            currentPosition = 1;
        } else {
            currentPosition++;
        }
        monthNameText.setText(jaliliMonthList[currentPosition]);
        setPieData();
    }

    public void preMonth() {
        if (currentPosition == 1) {
            currentPosition = 12;
        } else {
            currentPosition--;
        }
        monthNameText.setText(jaliliMonthList[currentPosition]);
        setPieData();
    }

    /////////////////////////////////
    /////////////////////////////////
    /////////////////////////////////

}
