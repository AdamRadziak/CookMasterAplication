package com.example.cookmasteraplication.Adapters;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookmasteraplication.Api.Models.PageDatumUserMenu;
import com.example.cookmasteraplication.Api.Models.Recipe;
import com.example.cookmasteraplication.Api.Models.UpdateUserMenu;
import com.example.cookmasteraplication.Api.RetrofitClients.BaseClient;
import com.example.cookmasteraplication.Api.Services.IUserMenuService;
import com.example.cookmasteraplication.Helpers.CommonTools;
import com.example.cookmasteraplication.Helpers.SharedPreferencesActivities;
import com.example.cookmasteraplication.R;
import com.example.cookmasteraplication.Views.SavedMenuDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RecyclerAdapterSavedMenu extends RecyclerView.Adapter<RecyclerAdapterSavedMenu.MenuViewHolder> {

    private final ArrayList<PageDatumUserMenu> menuList;
    private final AppCompatActivity activity;
    SharedPreferencesActivities sharedPref;
    String email;
    String password;
    Integer userId;


    public RecyclerAdapterSavedMenu(ArrayList<PageDatumUserMenu> menuList, AppCompatActivity activity, SharedPreferencesActivities sharedPref) {
        this.menuList = menuList;
        this.activity = activity;
        this.sharedPref = sharedPref;
        // get by sharedPreferences login data
        this.userId = sharedPref.getUserId();
        this.email = sharedPref.getUserEmail();
        this.password = sharedPref.getUserPass();
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

    private Drawable convert_from_bytes_2_image(PageDatumUserMenu customMenuItem, int index){
        String imagebyte = customMenuItem.getRecipes().get(index)
                .getPhotos().get(0).getData();
        return CommonTools.createImagefromBytes(imagebyte,activity);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        PageDatumUserMenu customMenuItem = menuList.get(position);
        holder.savedMenuTitle.setText(customMenuItem.getName());
        // menu titles
        List<Recipe> recipes = customMenuItem.getRecipes();
        if(recipes.size() == 1){
        holder.menu1Title.setText(customMenuItem.getRecipes().get(0).getCategory());
        holder.menu2Title.setText("");
        holder.menu3Title.setText("");
        holder.imageMenu1.setImageDrawable(convert_from_bytes_2_image(customMenuItem,0));
        holder.imageMenu2.setImageDrawable(null);
        holder.imageMenu3.setImageDrawable(null);}
        else if (recipes.size() == 2)
        {
            holder.menu1Title.setText(customMenuItem.getRecipes().get(0).getCategory());
            holder.menu2Title.setText(customMenuItem.getRecipes().get(1).getCategory());
            holder.menu3Title.setText("");
            holder.imageMenu1.setImageDrawable(convert_from_bytes_2_image(customMenuItem,0));
            holder.imageMenu2.setImageDrawable(convert_from_bytes_2_image(customMenuItem,1));
            holder.imageMenu3.setImageDrawable(null);
        }
        else if (recipes.size() == 3){
            holder.menu1Title.setText(customMenuItem.getRecipes().get(0).getCategory());
            holder.menu2Title.setText(customMenuItem.getRecipes().get(1).getCategory());
            holder.menu3Title.setText(customMenuItem.getRecipes().get(2).getCategory());
            holder.imageMenu1.setImageDrawable(convert_from_bytes_2_image(customMenuItem,0));
            holder.imageMenu2.setImageDrawable(convert_from_bytes_2_image(customMenuItem,1));
            holder.imageMenu3.setImageDrawable(convert_from_bytes_2_image(customMenuItem,2));
        }

        //images
        holder.cardViewSavedMenu.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), SavedMenuDetailsActivity.class);
            intent.putExtra("cardPosition", position);
            activity.startActivity(intent);
        });
        holder.EditButton.setOnClickListener(v -> {
            String menuNameText = holder.menuName.getText().toString();
            if (!(menuNameText.isEmpty())) {

                // get list of id Recipes
                List<Integer> idRecipes = new ArrayList<>();
                for(Recipe recipe:recipes){
                    idRecipes.add(recipe.getId());
                }
                // set menu name by api
                UpdateUserMenu updateUserMenu = new UpdateUserMenu(menuNameText,userId,
                        customMenuItem.getCategory(),idRecipes);
                updateMenuName(updateUserMenu,position);
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

    private void updateMenuName(UpdateUserMenu updateUserMenu, int position){
        // get from shared preferences IdMenu
        Retrofit retrofitClient = BaseClient.get_AuthClient(email, password);
        IUserMenuService client = retrofitClient.create(IUserMenuService.class);
        Call<PageDatumUserMenu> call = client.UpdateMenu(updateUserMenu,menuList.get(position).getId());
        call.enqueue(new Callback<PageDatumUserMenu>() {
            @Override
            public void onResponse(Call<PageDatumUserMenu> call, Response<PageDatumUserMenu> response) {
                if(response.code() == 200){
                    menuList.set(position,response.body());
                    Toast.makeText(activity.getApplicationContext(),"Pomyślnie zmieniono nazwę menu na "
                            + updateUserMenu.getName(),
                            Toast.LENGTH_LONG).show();
                    notifyDataSetChanged();
                }
                else {
                    Toast.makeText(activity.getApplicationContext(),"Błąd " + response.message(),
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PageDatumUserMenu> call, Throwable throwable) {
                Toast.makeText(activity.getApplicationContext(),"Błąd " + throwable.getMessage(),
                        Toast.LENGTH_LONG).show();

            }
        });

    }

    private void deleteMenuName(int position){
        // get from shared preferences IdMenu
        Retrofit retrofitClient = BaseClient.get_AuthClient(email, password);
        IUserMenuService client = retrofitClient.create(IUserMenuService.class);
        Call<PageDatumUserMenu> call = client.DeleteMenu(menuList.get(position).getId());
        call.enqueue(new Callback<PageDatumUserMenu>() {
            @Override
            public void onResponse(Call<PageDatumUserMenu> call, Response<PageDatumUserMenu> response) {
                if (response.code()==204){
                menuList.remove(position);
                }else{
                    Toast.makeText(activity.getApplicationContext(),
                            "Błąd " + response.message(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PageDatumUserMenu> call, Throwable throwable) {
                Toast.makeText(activity.getApplicationContext(),
                        "Błąd " + throwable.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

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
                        deleteMenuName(position);
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
