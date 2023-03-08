package com.example.fitzilla;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class DayDisplayFragment extends Fragment {


    TextView tvAntet;
    FloatingActionButton fabAdauga;
    FirebaseDatabase firebaseDatabase;
    int an,luna,zi;

    ArrayList<Exercitiu> listaMea= new ArrayList<Exercitiu>();
    ListView lvExercitii;
    CustomerAdapter_DayDisplay adapter_dayDisplay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_day_display, container, false);

        tvAntet = v.findViewById(R.id.day_display_tv_antet);
        Bundle bundle = this.getArguments();
        if(bundle!=null){
            an = bundle.getInt("an");
            luna = bundle.getInt("luna");
            zi = bundle.getInt("zi");
            tvAntet.setText("Exercitiile din data de " + zi + "/" + (luna+1) + "/" + an);
        }

        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ziua = firebaseDatabase.getReference("" +an).child(""+luna).child(""+zi);

        ziua.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot grupa : snapshot.getChildren()){
                    DatabaseReference grupaRef = ziua.child(grupa.getKey());
//                    Toast.makeText(getContext(),"" + grupa.getKey(),Toast.LENGTH_LONG).show();
                    grupaRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot exercitiu : snapshot.getChildren()){
//                                String greutateEx = exercitiu.child("Greutate").getValue().toString();
//                                String repetariEx = exercitiu.child("Repetari").getValue().toString();
                                String numeEx = exercitiu.getKey().toString();
                                int nrSerii = (int) exercitiu.getChildrenCount();
                                String[] greutate = new String[nrSerii];
                                String[] nrRepetari = new String[nrSerii];
                                for(int seriia = 0; seriia<nrSerii;seriia++){
                                    greutate[seriia] = exercitiu.child("seria " + (seriia+1)).child("Greutate").getValue().toString();
                                    nrRepetari[seriia] = exercitiu.child("seria " + (seriia+1)).child("Repetari").getValue().toString();
                                }
                                Exercitiu exercitiuDeListat = new Exercitiu(numeEx,grupa.getKey(),"",nrSerii,greutate,nrRepetari);
//                                Exercitiu exercitiuDeListat= new Exercitiu(numeEx,grupa.getKey(),"",greutateEx,repetariEx);
//                                Toast.makeText(getContext(),exercitiuDeListat.toString(),Toast.LENGTH_LONG).show();
                                listaMea.add(exercitiuDeListat);
                            }

//                            listaMea.add(new Exercitiu("flexii","Picioare","","20","100"));
//                            listaMea.add(new Exercitiu("flexii vaa","Picioare","","15","1220"));
                            Toast.makeText(getContext(),"" + listaMea.size(),Toast.LENGTH_LONG).show();
                            lvExercitii = v.findViewById(R.id.day_display_lv_exercitii);
                            adapter_dayDisplay = new CustomerAdapter_DayDisplay(getContext(),R.layout.customer_adapter_day_display,listaMea);

                            lvExercitii = v.findViewById(R.id.day_display_lv_exercitii);
                            lvExercitii.setAdapter(adapter_dayDisplay);

                            lvExercitii.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    StringBuilder stringBuilder = new StringBuilder();
                                    for(int seria1 = 0;seria1<listaMea.get(i).getNrSerii();seria1++) {
                                        stringBuilder.append("greutate seria " + (seria1+1) + ": " + listaMea.get(i).getGreutate()[seria1] + "\n");
                                        stringBuilder.append("repetari seria " + (seria1+1) + ": " + listaMea.get(i).getNrRepetari()[seria1] + "\n");
                                    }
                                    Toast.makeText(getContext(),stringBuilder,Toast.LENGTH_LONG).show();
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
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        listaMea.add(new Exercitiu("flexii","Picioare","","20","100"));
//        listaMea.add(new Exercitiu("flexii vaa","Picioare","","15","1220"));
//        Toast.makeText(getContext(),"" + listaMea.size(),Toast.LENGTH_LONG).show();
//        lvExercitii = v.findViewById(R.id.day_display_lv_exercitii);
//        adapter_dayDisplay = new CustomerAdapter_DayDisplay(getContext(),R.layout.customer_adapter_day_display,listaMea);
//
//        lvExercitii = v.findViewById(R.id.day_display_lv_exercitii);
//        lvExercitii.setAdapter(adapter_dayDisplay);



        fabAdauga = v.findViewById(R.id.day_display_fab_adauga);
        fabAdauga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DayAddFragment dayAddFragment = new DayAddFragment();
                dayAddFragment.setArguments(bundle);



//                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container,dayDisplayFragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();





                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container,dayAddFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return v;
    }

}