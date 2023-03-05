package com.example.fitzilla;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class CustomerAdapter extends BaseAdapter {

    Context context;
    int layout;

    public CustomerAdapter(Context context, int layout) {
        this.context = context;
        this.layout = layout;
    }


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater infl = LayoutInflater.from(context);
        View v = infl.inflate(layout,viewGroup,false);

        FirebaseDatabase realtime_database = FirebaseDatabase.getInstance();

        TextView tvNume = v.findViewById(R.id.ca_tv_nume);
        TextView tvGrupa = v.findViewById(R.id.ca_tv_grupa);

        return v;
    }
}
