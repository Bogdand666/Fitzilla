package com.example.fitzilla;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CustomerAdapter_DayDisplay extends BaseAdapter {

    Context context;
    int layout;
    ArrayList<Exercitiu> listaMea;

    public CustomerAdapter_DayDisplay(Context context, int layout, ArrayList<Exercitiu> listaMea) {
        this.context = context;
        this.layout = layout;
        this.listaMea = listaMea;
    }



    @Override
    public int getCount() {
        return listaMea.size();
    }

    @Override
    public Object getItem(int i) {
        return listaMea.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater infl = LayoutInflater.from(context);
        View v = infl.inflate(layout,viewGroup,false);

        Exercitiu exercitiu = listaMea.get(i);

        TextView tvNrSerii = v.findViewById(R.id.ca_tv_nrSerii);
        TextView tvNume = v.findViewById(R.id.ca_tv_nume);
        TextView tvGrupa = v.findViewById(R.id.ca_tv_grupa);

        tvNume.setText(exercitiu.getNume());
        tvGrupa.append(exercitiu.getGrupa());
        tvNrSerii.append("" + exercitiu.getNrSerii());

        return v;
    }
}
