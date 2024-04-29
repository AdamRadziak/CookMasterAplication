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

import com.example.cookmasteraplication.R;

import java.util.ArrayList;

public class RecyclerAdapterStep extends RecyclerView.Adapter<RecyclerAdapterStep.MenuViewHolder> {

    private final ArrayList<String> steps;
    private final AppCompatActivity activity;

    public RecyclerAdapterStep(ArrayList<String> steps, AppCompatActivity activity) {
        this.steps = steps;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_card, parent, false);

        return new MenuViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        String step = steps.get(position);
        int step_num = position + 1;
        holder.number.setText(" " + step_num);
        holder.desc.setText(step);
        // set background color
        if (position % 2 == 0) {
            holder.layout.setBackgroundColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.colorOnPrimaryLight));
        } else {
            holder.layout.setBackgroundColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.colorPrimaryLight));
        }

    }


    public class MenuViewHolder extends RecyclerView.ViewHolder {

        private final TextView number;
        private final TextView desc;
        private final LinearLayout layout;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);

            number = itemView.findViewById(R.id.textViewStepNum);
            desc = itemView.findViewById(R.id.textViewStepDesc);
            layout = itemView.findViewById(R.id.Step_layout);


        }
    }
}
