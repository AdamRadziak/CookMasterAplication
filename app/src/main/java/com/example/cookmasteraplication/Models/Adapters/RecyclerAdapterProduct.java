package com.example.cookmasteraplication.Models.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookmasteraplication.Models.ProductModel;
import com.example.cookmasteraplication.R;

import java.util.ArrayList;

public class RecyclerAdapterProduct extends RecyclerView.Adapter<RecyclerAdapterProduct.MenuViewHolder> {

    private final ArrayList<ProductModel> productList;
    private final AppCompatActivity activity;

    public RecyclerAdapterProduct(ArrayList<ProductModel> productList, AppCompatActivity activity) {
        this.productList = productList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card, parent, false);

        return new MenuViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        ProductModel customMenuItem = productList.get(position);
        holder.name.setText(customMenuItem.getName());
        holder.amount.setText(customMenuItem.getAmount());
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
        private final LinearLayout layout;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.textViewProdName);
            amount = itemView.findViewById(R.id.textViewProdAmount);
            layout = itemView.findViewById(R.id.productRow);


        }
    }
}
