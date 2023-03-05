package com.example.fitzilla;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class DayDisplayFragment extends Fragment {


    TextView tvAntet;
    FloatingActionButton fabAdauga;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_day_display, container, false);

        tvAntet = v.findViewById(R.id.day_display_tv_antet);
        Bundle bundle = this.getArguments();
        if(bundle!=null){
            int an = bundle.getInt("an");
            int luna = bundle.getInt("luna");
            int zi = bundle.getInt("zi");
            tvAntet.setText("Exercitiile din data de " + zi + "/" + luna + "/" + an);
        }

        fabAdauga = v.findViewById(R.id.day_display_fab_adauga);
        fabAdauga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container,new DayAddFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return v;
    }

}