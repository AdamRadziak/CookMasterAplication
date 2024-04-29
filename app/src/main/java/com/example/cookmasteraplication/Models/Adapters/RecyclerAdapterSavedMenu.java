package com.example.cookmasteraplication.Models.Adapters;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookmasteraplication.Models.SavedMenuModel;
import com.example.cookmasteraplication.R;
import com.example.cookmasteraplication.Views.SavedMenuDetailsActivity;

import java.util.ArrayList;

public class RecyclerAdapterSavedMenu extends RecyclerView.Adapter<RecyclerAdapterSavedMenu.MenuViewHolder> {

    private final ArrayList<SavedMenuModel> menuList;
    private final ArrayList<Integer> imageList;
    private final AppCompatActivity activity;


    public RecyclerAdapterSavedMenu(ArrayList<SavedMenuModel> menuList, ArrayList<Integer> imageList, AppCompatActivity activity) {
        this.menuList = menuList;
        this.imageList = imageList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_menu_card, parent, false);

        return new MenuViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        SavedMenuModel customMenuItem = menuList.get(position);
        holder.savedMenuTitle.setText(customMenuItem.getName());
        // menu titles
        holder.menu1Title.setText(customMenuItem.getMenu().get(position).getName());
        holder.menu2Title.setText(customMenuItem.getMenu().get(position + 1).getName());
        holder.menu3Title.setText(customMenuItem.getMenu().get(position + 2).getName());
        //images
        holder.imageMenu1.setImageResource(imageList.get(position));
        holder.imageMenu2.setImageResource(imageList.get(position + 1));
        holder.imageMenu3.setImageResource(imageList.get(position + 2));
        holder.cardViewSavedMenu.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), SavedMenuDetailsActivity.class);
            intent.putExtra("cardPosition", position);
            activity.startActivity(intent);
        });
        holder.EditButton.setOnClickListener(v -> {
            String menuNameText = holder.menuName.getText().toString();
            if (!(menuNameText.isEmpty())) {
                customMenuItem.setName(menuNameText);
                notifyDataSetChanged();
            } else {
                holder.menuName.setBackgroundColor(Color.RED);
                holder.menuName.setHint("Wprowadż właściwą nazwę");

            }
        });
        holder.deleteButton.setOnClickListener(v -> {
            showDialogMsg(holder.getAdapterPosition());
            // refresh recycler view
        });
        // set backgorund for card
        if (position % 2 == 0) {
            holder.cardViewSavedMenu.setBackgroundColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.colorOnPrimaryLight));
        } else {
            holder.cardViewSavedMenu.setBackgroundColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.colorPrimaryLight));
        }

    }

    private void showDialogMsg(int position) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity, R.style.DialogTheme);
        alertDialog.setTitle("Usuwanie menu").setMessage("Czy chcesz usunąć całe zapisane menu?")
                .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // close dialoge message
                        dialog.dismiss();
                    }
                }).setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // delete text in result textview
                        menuList.remove(position);
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                }).show();
        alertDialog.create();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {

        private final TextView savedMenuTitle, menu1Title, menu2Title, menu3Title;
        private final ImageView imageMenu1, imageMenu2, imageMenu3;
        private final ImageButton deleteButton, EditButton;
        private final EditText menuName;
        private final CardView cardViewSavedMenu;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);

            savedMenuTitle = itemView.findViewById(R.id.textViewSavedMenuTitle);
            menu1Title = itemView.findViewById(R.id.textViewMenu1Name);
            menu2Title = itemView.findViewById(R.id.textViewMenu2Name);
            menu3Title = itemView.findViewById(R.id.textViewMenu3Name);
            imageMenu1 = itemView.findViewById(R.id.imageViewMenu1);
            imageMenu2 = itemView.findViewById(R.id.imageViewMenu2);
            imageMenu3 = itemView.findViewById(R.id.imageViewMenu3);
            menuName = itemView.findViewById(R.id.editTextMenuNameSavedMenuCard);
            deleteButton = itemView.findViewById(R.id.imageButtondelMenu);
            EditButton = itemView.findViewById(R.id.imageButtonEditMenu);
            cardViewSavedMenu = itemView.findViewById(R.id.saveMenuCard);


        }
    }
}
