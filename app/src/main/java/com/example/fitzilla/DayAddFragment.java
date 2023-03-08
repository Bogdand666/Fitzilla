package com.example.fitzilla;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class DayAddFragment extends Fragment {

    Spinner spnGrupa, spnExercitiu;


    Button btnSalveaza;
    FloatingActionButton btnAdaugaSerie;
    FirebaseDatabase realtime_database = FirebaseDatabase.getInstance();

    TextView tvAntet, tvSeria;
    LinearLayout llSerie;
    int an,luna,zi;
    TextInputEditText tiedRepetari, tiedGreutate;

    int seria = 1;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_day_add, container, false);

        spnGrupa = v.findViewById(R.id.day_add_spn_grupa);
        spnExercitiu = v.findViewById(R.id.day_add_spn_nume);
        btnSalveaza = v.findViewById(R.id.day_add_btn_salveaza);
        btnAdaugaSerie = v.findViewById(R.id.day_add_btn_addSerie);
        llSerie = v.findViewById(R.id.day_add_ll_serie);
        tiedRepetari = v.findViewById(R.id.day_add_tied_repetari);
        tiedGreutate = v.findViewById(R.id.day_add_tied_greutate);
        tvSeria = v.findViewById(R.id.day_add_tv_serii);


        tvAntet = v.findViewById(R.id.day_add_tv_antet);
        Bundle bundle = this.getArguments();
        if(bundle!=null){
            an = bundle.getInt("an");
            luna = bundle.getInt("luna");
            zi = bundle.getInt("zi");
            tvAntet.setText("Introduceti exercitii pentru data de " + zi + "/" + (luna+1) + "/" + an);
        }


        spnGrupa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spnGrupa.getSelectedItemId() != 0) {
                    DatabaseReference fbExercitii = realtime_database.getReference("Exercitii");
                    DatabaseReference fbGrupa = fbExercitii.child(spnGrupa.getSelectedItem().toString());

                    fbGrupa.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //lista_exercitii = lista cu toate exercitiile de la grupa selectata. cu aceasta lista populam spinerul pentru selectarea exercitiului
                            ArrayList<String> lista_exercitii = new ArrayList<>();
                            lista_exercitii.add("Selectati exercitiu");
                            for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                                lista_exercitii.add(dataSnapshot1.getKey());
                            }

                            ArrayAdapter<String> adapter_exercitii = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, lista_exercitii);
                            spnExercitiu.setAdapter(adapter_exercitii);
                            spnExercitiu.setVisibility(View.VISIBLE);

                            spnExercitiu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    if (spnExercitiu.getSelectedItemId() != 0) {
                                        llSerie.setVisibility(View.VISIBLE);
                                        tvSeria.setVisibility(View.VISIBLE);
                                        tvSeria.setText("Seria " + seria);
                                        btnAdaugaSerie.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                DatabaseReference ziua = realtime_database.getReference("" +an).child(""+luna).child(""+zi);
                                                ziua.child(spnGrupa.getSelectedItem().toString()).child(spnExercitiu.getSelectedItem().toString()).child("seria "+seria)
                                                        .child("Greutate").setValue(tiedGreutate.getText().toString());
                                                ziua.child(spnGrupa.getSelectedItem().toString()).child(spnExercitiu.getSelectedItem().toString()).child("seria "+seria)
                                                        .child("Repetari").setValue(tiedRepetari.getText().toString());

                                                tiedGreutate.setText("");
                                                tiedRepetari.setText("");
                                                seria = seria+1;
                                                tvSeria.setText("Seria " + seria);
                                            }
                                        });

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