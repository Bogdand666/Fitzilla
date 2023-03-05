package com.example.fitzilla;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;


public class HomeFragment extends Fragment {

    private CalendarView calendarView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        calendarView = v.findViewById(R.id.home_calendar);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

                Bundle bundle = new Bundle();
                bundle.putInt("an",i);
                bundle.putInt("luna",i1);
                bundle.putInt("zi",i2);
                DayDisplayFragment dayDisplayFragment = new DayDisplayFragment();
                dayDisplayFragment.setArguments(bundle);



                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container,dayDisplayFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        calendarView = getView().findViewById(R.id.home_calendar);
//        calendarView.setDate(System.currentTimeMillis());


//        calendarView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getLayoutInflater().inflate(R.layout.fragment_day_display,)
//
//            }
//        });
    }


}