package com.example.fitzilla;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;


public class AddFragment extends Fragment {


    Uri imageUri;
    GifImageView gifImageView;

    TextInputEditText tied_nume, tied_descriere;
    Spinner spnGrupa;
    Button btnAdauga;

    FirebaseDatabase realtime_database = FirebaseDatabase.getInstance();

    FirebaseStorage storage = FirebaseStorage.getInstance();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
       initializare();

       gifImageView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent();
               intent.setType("image/*");
               intent.setAction(Intent.ACTION_GET_CONTENT);
               startActivityForResult(intent,100);
           }
       });

       btnAdauga.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(isValid()) {
                   String nume = tied_nume.getText().toString();
                   String grupa = spnGrupa.getSelectedItem().toString();
                   String descriere = tied_descriere.getText().toString();
                   Exercitiu exercitiu = new Exercitiu(nume,grupa,descriere);

                   //scriere exercitii in realtime database.b
                   DatabaseReference fbExercitii = realtime_database.getReference("Exercitii");
                   DatabaseReference fbGrupa = fbExercitii.child(grupa);
                   DatabaseReference fbExercitiu = fbGrupa.child(nume);

                   fbExercitiu.child("Descriere").setValue(descriere);


                   if(imageUri!=null) {
                       StorageReference fbGIFS = storage.getReference("GIFS").child(nume);
                       fbGIFS.putFile(imageUri);
                   }



               }
           }


       });





    }


    private boolean isValid() {
        if(tied_nume.getText().toString().trim().equals("")){
            Toast.makeText(getContext(),"Introduceti nume exercitiu!",Toast.LENGTH_LONG).show();
            return false;
        }
        if(spnGrupa.getSelectedItemId()==0){
            Toast.makeText(getContext(),"Selectati o grupa!",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;


    }
    private void initializare() {
        gifImageView = getView().findViewById(R.id.add_gif);
        tied_nume = getView().findViewById(R.id.add_tied_exercitiu);
        spnGrupa = getView().findViewById(R.id.add_spn_grupa);
        tied_descriere = getView().findViewById(R.id.add_tied_descriere);
        btnAdauga = getView().findViewById(R.id.add_btn_adauga);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100 && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();
            gifImageView.setImageURI(imageUri);
        }
    }
}