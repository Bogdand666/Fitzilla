package com.example.fitzilla;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.Inflater;

public class CustomerAdapter_Serii extends BaseAdapter {
    private Context context;
    private int layout;
    int[] serii;
    private TextInputEditText tiedRepetari;
    private TextInputEditText tiedGreutate;

    private ArrayList<HashMap<String,String>> data;

    public CustomerAdapter_Serii(Context context, int layout, ArrayList<HashMap<String, String>> data) {
        this.context = context;
        this.layout = layout;
        this.data = data;
    }

    public CustomerAdapter_Serii(Context context, int layout, int[] serii) {
        this.context = context;
        this.layout = layout;
        this.serii = serii;
    }


//    @Override
//    public int getCount() {
//        return serii.length;
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return serii[i];
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return serii[i];
//    }
//
//    public int get_nrRepetari(){
//        return Integer.parseInt(tiedRepetari.getText().toString());
//    }
//
//    public int get_Greutate(){
//        return Integer.parseInt(tiedGreutate.getText().toString());
//    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = LayoutInflater.from(context);

        View v = inflater.inflate(R.layout.customer_adapter_serii,viewGroup,false);


        TextView tv = v.findViewById(R.id.ca_tv_serii);
        tv.append("" + (i+1));
        tiedRepetari = v.findViewById(R.id.ca_tied_repetari);


        tiedGreutate = v.findViewById(R.id.ca_tied_greutate);




        return v;
    }
}
