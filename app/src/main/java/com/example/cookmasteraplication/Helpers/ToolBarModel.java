package com.example.cookmasteraplication.Helpers;

import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import com.example.cookmasteraplication.R;
import com.example.cookmasteraplication.Views.AccountSettingsActivity;
import com.example.cookmasteraplication.Views.CreateMenuActivity;
import com.example.cookmasteraplication.Views.FavouritesRecipesActivity;
import com.example.cookmasteraplication.Views.FindRecipeActivity;
import com.example.cookmasteraplication.Views.LoginActivity;
import com.example.cookmasteraplication.Views.SavedMenuActivity;
import com.google.android.material.appbar.MaterialToolbar;

final public class ToolBarModel {

    private final AppCompatActivity activity;
    private final MaterialToolbar toolbar;


    public ToolBarModel(Builder builder) {
        this.activity = builder.activity;
        this.toolbar = builder.toolbar;
    }

    public static class Builder {
        private final AppCompatActivity activity;
        private final MaterialToolbar toolbar;

        public Builder(AppCompatActivity activity, MaterialToolbar toolbar) {
            this.activity = activity;
            this.toolbar = toolbar;
        }

        public Builder setToolbarSubtitle(String pageName) {
            toolbar.setSubtitle(pageName);
            return this;
        }


        public Builder setOverflowIcon() {
            toolbar.setOverflowIcon(AppCompatResources.getDrawable(activity.getApplicationContext(), R.drawable.menu_icon));
            return this;
        }

        public Builder setNavigationIconOnClickListener(AppCompatActivity DestActivity) {
            toolbar.setNavigationOnClickListener(v -> {
                Intent intent = new Intent(activity.getApplicationContext(), FindRecipeActivity.class);
                activity.startActivity(intent);
            });
            return this;
        }

        public Builder setMenuOItemOnClickListeners() {
            toolbar.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.createMenuItem) {
                    Toast.makeText(activity.getApplicationContext(), "create menu item", Toast.LENGTH_SHORT).show();
                    Intent createMenuIntent = new Intent(activity.getApplicationContext(), CreateMenuActivity.class);
                    activity.startActivity(createMenuIntent);
                } else if (item.getItemId() == R.id.findRecipeMenuItem) {
                    Toast.makeText(activity.getApplicationContext(), "find recipe menu item", Toast.LENGTH_SHORT).show();
                    Intent findRecipeIntent = new Intent(activity.getApplicationContext(), FindRecipeActivity.class);
                    activity.startActivity(findRecipeIntent);

                } else if (item.getItemId() == R.id.savedMenusMenuItem) {
                    Toast.makeText(activity.getApplicationContext(), "saved menu item", Toast.LENGTH_SHORT).show();
                    Intent savedMenuIntent = new Intent(activity.getApplicationContext(), SavedMenuActivity.class);
                    activity.startActivity(savedMenuIntent);
                } else if (item.getItemId() == R.id.favouritesMenuItem) {
                    Toast.makeText(activity.getApplicationContext(), "favourites menu item", Toast.LENGTH_SHORT).show();
                    Intent favouritesIntent = new Intent(activity.getApplicationContext(), FavouritesRecipesActivity.class);
                    activity.startActivity(favouritesIntent);
                } else if (item.getItemId() == R.id.accountSettingsMenuItem) {
                    Toast.makeText(activity.getApplicationContext(), "account settings menu item", Toast.LENGTH_SHORT).show();
                    Intent accountSettIntent = new Intent(activity.getApplicationContext(), AccountSettingsActivity.class);
                    activity.startActivity(accountSettIntent);

                } else if (item.getItemId() == R.id.signOutMenuItem) {
                    Toast.makeText(activity.getApplicationContext(), "logout menu item", Toast.LENGTH_SHORT).show();
                    Intent loginIntent = new Intent(activity.getApplicationContext(), LoginActivity.class);
                    activity.startActivity(loginIntent);
                } else if (item.getItemId() == R.id.backMenuItem) {
                    Toast.makeText(activity.getApplicationContext(), "Back menu item", Toast.LENGTH_SHORT).show();
                    activity.finish();

                }
                return true;
            });
            return this;
        }

        public Builder create() {
            return new Builder(this.activity, this.toolbar);
        }

        public ToolBarModel build() {
            return new ToolBarModel(this);
        }
    }


}




