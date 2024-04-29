package com.example.cookmasteraplication.Models.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cookmasteraplication.R;

import java.util.ArrayList;

public class GridAdapterProductsFindRecipe extends BaseAdapter {

    AppCompatActivity activity;
    ArrayList<String> products = new ArrayList<>();

    public GridAdapterProductsFindRecipe(AppCompatActivity activity, ArrayList<String> products) {
        this.activity = activity;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_grid_card,parent,false);
        TextView textView = view.findViewById(R.id.textViewGridProd);
        ImageButton button = view.findViewById(R.id.imageButtondelProd);

        textView.setText(products.get(position));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                products.remove(position);
                notifyDataSetChanged();
            }
        });

        return view;
    }
}
