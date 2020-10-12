/*
* Project Name: Expense Tracker
Project Description: Expense Tracker is an application that allows its users to save and track their daily expenses. It takes inputs from user such as annual income, desired savings and maximum daily expense
* and notifies user by changing the respective items on the home screen. It also allows user to add their own categories to add expenses and delete if not needed by providing more flexibility to user.
*  User can also track the expenses or savings by using reports feature that generates bar graphs according to the range of dates provided. There are also few functionalities to change password,
*  to remember user login state by eliminating the need of logging in every time.
* Team members:
	Haritha Nimmagadda
	Kiran Panjam
Refereneces: The calculator functionality in this app is based on "https://technobyte.org/simple-calculator-app-in-android-studio/"

*/
package com.example.finalprojectc.ui.reports;

import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.example.finalprojectc.DatabaseHelper;
import com.example.finalprojectc.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DailyExpensesReportFragment extends Fragment {

    int user_id;

    public DailyExpensesReportFragment() {
        // Required empty public constructor
    }

    public static DailyExpensesReportFragment newInstance(String param1, String param2) {
        DailyExpensesReportFragment fragment = new DailyExpensesReportFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String start_date = getArguments().getString("start date");
        String end_date = getArguments().getString("end date");

        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_daily_expenses_report, container, false);

        List<DataEntry> data = new ArrayList<>();

        DatabaseHelper myDatabaseHelper = new DatabaseHelper(getActivity());

        LocalDate start = LocalDate.parse(start_date);
        LocalDate end = LocalDate.parse(end_date);
        List<LocalDate> totalDates = new ArrayList<>();

        while (!start.isAfter(end)) {
            totalDates.add(start);
            start = start.plusDays(1);
        }

        String[] dates_array = new String[totalDates.size()];

        for (int counter = 0; counter < totalDates.size(); counter++) {
            dates_array[counter] = totalDates.get(counter).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        double[] expenses_array = new double[totalDates.size()];
        for (int counter = 0; counter < totalDates.size(); counter++) {
            expenses_array[counter] = 0;
        }

        user_id = myDatabaseHelper.getActiveUserId();

        for (int counter = 0; counter < totalDates.size(); counter++) {
            expenses_array[counter] = myDatabaseHelper.getDailyExpenses(myDatabaseHelper, dates_array[counter], user_id);
            data.add(new ValueDataEntry(dates_array[counter], (expenses_array[counter])));
        }

        //String xyz = text[0];
        myDatabaseHelper.close();

        AnyChartView anyChartView = view.findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(view.findViewById(R.id.progress_bar));

        Cartesian cartesian = AnyChart.column();

        Column column = cartesian.column(data);

        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("${%Value}{groupsSeparator: }");

        cartesian.animation(true);
        cartesian.title("Daily expenses for selected period.");

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("${%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.xAxis(0).title("Date");
        cartesian.yAxis(0).title("Expense");

        anyChartView.setChart(cartesian);



        return view;
    }
}