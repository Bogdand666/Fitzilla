package com.example.fitzilla;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;


public class DisplayExerciseFragment extends Fragment {

    Spinner spnGrupa, spnExercitiu;
    TextView tvDescriere, tvHintDescriere;

    FirebaseDatabase realtime_database = FirebaseDatabase.getInstance();

    FirebaseStorage storage = FirebaseStorage.getInstance();
    Uri imageUri;
    GifImageView gifImageView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_display_exercise, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        initializare();

        spnGrupa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(spnGrupa.getSelectedItemId()!=0){
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


                            //la selectare element din spnExercitiu se populeaza descrierea exercitiului si GIF-ul
                            spnExercitiu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    if(spnExercitiu.getSelectedItemId()!=0){
                                        DatabaseReference fbExercitiu = fbGrupa.child(spnExercitiu.getSelectedItem().toString());
                                        //pentru citire descriere
                                        fbExercitiu.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                String descriere = snapshot.child("Descriere").getValue().toString();
                                                tvDescriere.setText(descriere);
                                                tvHintDescriere.setVisibility(View.VISIBLE);
                                                tvDescriere.setVisibility(View.VISIBLE);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                                        //pentru citire GIF
                                        StorageReference GIFS = storage.getReference("GIFS").child(spnExercitiu.getSelectedItem().toString());

                                        try {
                                            final File localFile = File.createTempFile(spnExercitiu.getSelectedItem().toString(),"gif");


                                            GIFS.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                                    imageUri = Uri.fromFile(localFile);
                                                    gifImageView.setImageURI(imageUri);
                                                    gifImageView.setVisibility(View.VISIBLE);


                                                }
                                            });
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
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


    }

    private void initializare() {
        spnGrupa = getView().findViewById(R.id.display_exercise_spn_grupa);
        spnExercitiu = getView().findViewById(R.id.display_exercise_spn_nume);

        tvDescriere = getView().findViewById(R.id.display_exercise_tv_descriere);
        tvHintDescriere = getView().findViewById(R.id.display_exercise_tv_hint_descriere);

        gifImageView = getView().findViewById(R.id.display_exercise_gif);
    }
}