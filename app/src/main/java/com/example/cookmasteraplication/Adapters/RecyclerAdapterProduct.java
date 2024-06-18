package com.example.cookmasteraplication.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookmasteraplication.R;
import com.example.cookmasteraplication.api.Models.Product;

import java.util.ArrayList;
import java.util.Locale;

public class RecyclerAdapterProduct extends RecyclerView.Adapter<RecyclerAdapterProduct.MenuViewHolder> {

    private final AppCompatActivity activity;
    private final ArrayList<Product> products;

    public RecyclerAdapterProduct( ArrayList<Product> products,
                                   AppCompatActivity activity) {
        this.activity = activity;
        this.products = products;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card, parent, false);

        return new MenuViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        Product productItem = products.get(position);
        String productAmountString = String.format(Locale.ENGLISH, "%.1f", productItem.getAmount());
        // get data of products
        holder.name.setText(productItem.getName());
        holder.amount.setText(productAmountString);
        holder.unit.setText(productItem.getUnit());
        // set background color
        if (position % 2 == 0) {
            holder.layout.setBackgroundColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.colorOnPrimaryLight));
        } else {
            holder.layout.setBackgroundColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.colorPrimaryLight));
        }

    }


    public class MenuViewHolder extends RecyclerView.ViewHolder {

        private final TextView name;
        private final TextView amount;
        private final TextView unit;
        private final LinearLayout layout;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.textViewProdName);
            amount = itemView.findViewById(R.id.textViewProdAmount);
            unit = itemView.findViewById(R.id.textViewProductAmountUnit);
            layout = itemView.findViewById(R.id.productRow);


        }
    }
}
