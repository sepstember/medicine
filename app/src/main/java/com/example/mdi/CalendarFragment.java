package com.example.mdi;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Intent;
import java.text.SimpleDateFormat;

import android.text.method.ScrollingMovementMethod;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.Nullable;

import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */


public class CalendarFragment extends Fragment {

    private TextView txtV;
    private String date;
    private Calendar cal = Calendar.getInstance();
    private CalendarView calV;

    private int curYear = cal.get(Calendar.YEAR);
    private int curMonth = cal.get(Calendar.MONTH) + 1;
    private int curDayOfMonth = cal.get(Calendar.DATE);
    private long cur;

    private int year;
    private int month;
    private int dayOfMonth;

    private View view;


    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_calendar, container, false);
        calV = (CalendarView) view.findViewById(R.id.calV);
        cur = calV.getDate();

        calV.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year2, int month2, int dayOfMonth2) {
                year = year2;
                month = month2 + 1;
                dayOfMonth = dayOfMonth2;
            }
        });

        defaultDate();

        Button btn1 = (Button) view.findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate();
            }
        });

        Button btn2 = (Button) view.findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();
            }
        });

        Button btn3 = (Button) view.findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate2();
            }
        });

        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

        private void selectDate() {
        Intent intent = new Intent(getContext(), MemoActivity.class);
        intent.putExtra("year", year);
        intent.putExtra("month", month);
        intent.putExtra("dayOfMonth", dayOfMonth);
        startActivity(intent);
    }

    private void selectDate2() {
        Intent intent = new Intent(getContext(), ScheduleActivity.class);
        intent.putExtra("year", year);
        intent.putExtra("month", month);
        intent.putExtra("dayOfMonth", dayOfMonth);
        startActivity(intent);
    }

    private void send() {
        Intent intent = new Intent(getActivity(), SendActivity.class);
        startActivity(intent);
    }

    private void defaultDate() {
        //fragment 의 경우에는 그냥 getIntent를 사용할 수 없고, getActivity()가 먼저 와야 함
        Intent intent2 = getActivity().getIntent();

        if (intent2.getIntExtra("year", 0) != 0)
            year = intent2.getIntExtra("year", 0);
        else
            year = curYear;
        if (intent2.getIntExtra("month", 0) != 0)
            month = intent2.getIntExtra("month", 0);
        else
            month = curMonth;
        if (intent2.getIntExtra("dayOfMonth", 0) != 0)
            dayOfMonth = intent2.getIntExtra("dayOfMonth", 0);
        else
            dayOfMonth = curDayOfMonth;

        String form = "" + curYear + "-" + curMonth + "-" + curDayOfMonth;
        Date d = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            d = simpleDateFormat.parse(form);
        } catch (Exception e) {
        }

        String form2 = "" + year + "-" + month + "-" + dayOfMonth;

    }
}


