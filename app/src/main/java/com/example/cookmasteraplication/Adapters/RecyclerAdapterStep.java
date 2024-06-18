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
import com.example.cookmasteraplication.api.Models.Step;

import java.util.ArrayList;
import java.util.Locale;

public class RecyclerAdapterStep extends RecyclerView.Adapter<RecyclerAdapterStep.MenuViewHolder> {

    private final AppCompatActivity activity;
    private final ArrayList<Step> steps;

    public RecyclerAdapterStep(ArrayList<Step> steps, AppCompatActivity activity) {
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
        Step stepItem = steps.get(position);
        String stepDescription = stepItem.getDescription();
        Integer stepNumber = stepItem.getStepNum();
        String stepNumberString = " " + String.format(Locale.ENGLISH, "%d", stepNumber);
        holder.number.setText(stepNumberString);
        holder.desc.setText(stepDescription);
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
