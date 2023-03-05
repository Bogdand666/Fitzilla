package com.example.fitzilla;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class DayAddFragment extends Fragment {

    Spinner spnGrupa, spnExercitiu;
    TextInputEditText tiedNrSerii;
    TextInputLayout tilNrSerii;

    FirebaseDatabase realtime_database = FirebaseDatabase.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_day_add, container, false);

        spnGrupa = v.findViewById(R.id.day_add_spn_grupa);
        spnExercitiu = v.findViewById(R.id.day_add_spn_nume);
        tiedNrSerii = v.findViewById(R.id.day_add_tied_serii);
        tilNrSerii = v.findViewById(R.id.day_add_til_serii);

        spnGrupa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(spnGrupa.getSelectedItemId()!=0) {
                    DatabaseReference fbExercitii = realtime_database.getReference("Exercitii");
                    DatabaseReference fbGrupa = fbExercitii.child(spnGrupa.getSelectedItem().toString());

                    fbGrupa.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //lista_exercitii = lista cu toate exercitiile de la grupa selectata. cu aceasta lista populam spinerul pentru selectarea exercitiului
                            ArrayList<String> lista_exercitii = new ArrayList<>();
                            lista_exercitii.add("Selectati exercitiu");
                            for(DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                                lista_exercitii.add(dataSnapshot1.getKey());
                            }

                            ArrayAdapter<String> adapter_exercitii = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,lista_exercitii);
                            spnExercitiu.setAdapter(adapter_exercitii);
                            spnExercitiu.setVisibility(View.VISIBLE);

                            spnExercitiu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    if(spnExercitiu.getSelectedItemId()!=0){
                                        tilNrSerii.setVisibility(View.VISIBLE);
                                        //creez perechi de edittexturi nrRepetari+greutate pentru fiecare serie introdusa in tiedNrSerii
                                        if(!tiedNrSerii.getText().toString().trim().equals("")) {
//                                            for (int j = 0; j < Integer.parseInt(tiedNrSerii.getText().toString()); j++) {
//                                                EditText etNrRepetari = new EditText();
//
//                                            }

                                        }
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return v;
    }
}