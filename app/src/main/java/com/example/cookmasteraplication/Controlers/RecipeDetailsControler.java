package com.example.cookmasteraplication.Controlers;

import static com.example.cookmasteraplication.Controlers.CreateMenuController.imageListView;
import static com.example.cookmasteraplication.Controlers.CreateMenuController.menuListView;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookmasteraplication.Models.Adapters.RecyclerAdapterProduct;
import com.example.cookmasteraplication.Models.Adapters.RecyclerAdapterStep;
import com.example.cookmasteraplication.Models.MenuModel;
import com.example.cookmasteraplication.Models.ProductModel;
import com.example.cookmasteraplication.Models.RecipeModel;
import com.example.cookmasteraplication.Models.ToolBarModel;
import com.example.cookmasteraplication.Views.RecipeDetailsActivity;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class RecipeDetailsControler {

    public static ArrayList<MenuModel> favouriteMenuItem = new ArrayList<>();
    public static ArrayList<Integer> favouriteRecipeImage = new ArrayList<>();
    private final RecipeDetailsActivity activity;
    public RecipeModel recipeView = new RecipeModel();
    public ArrayList<Integer> imageList = new ArrayList<>();
    public ArrayList<MenuModel> menuList = new ArrayList<>();
    private int cardItemPosition = 0;

    public RecipeDetailsControler(RecipeDetailsActivity activity) {
        this.activity = activity;
    }

    public void setToolbarLogo(MaterialToolbar toolbar, String pageName) {
        ToolBarModel toolbarmodel = new ToolBarModel.Builder(activity,
                toolbar).create().setToolbarSubtitle(pageName).build();
    }

    public void setToolbarMenu(MaterialToolbar toolbar) {
        ToolBarModel toolbarmodel = new ToolBarModel.Builder(activity,
                toolbar).create().setMenuOItemOnClickListeners().build();
    }

    public void getRecipeFromCard(@NonNull Intent intent) {

        cardItemPosition = intent.getExtras().getInt("cardPosition", 0);
        imageList = intent.getExtras().getIntegerArrayList("imageList");
        recipeView.setName(intent.getExtras().getString("recipeName"));
        recipeView.setMealCount(intent.getExtras().getString("mealCount"));
        recipeView.setPrepareTime(intent.getExtras().getString("prepareTime"));
        recipeView.setRate(intent.getExtras().getString("rate"));
        recipeView.setPopularity(intent.getExtras().getString("popularity"));
        recipeView.setDescription(intent.getExtras().getString("recipeDesc"));
        recipeView.setSteps(intent.getExtras().getStringArrayList("steps"));
        ArrayList<String> productsNames = intent.getExtras().getStringArrayList("productsNames");
        ArrayList<String> productsAmounts = intent.getExtras().getStringArrayList("productsAmounts");
        ArrayList<ProductModel> products = join2ArrayListString2ProductListArray(productsNames,productsAmounts);
        recipeView.setProductList(products);
        MenuModel menu = new MenuModel();
        menu.setRecipe(recipeView);
        menuList.add(menu);
    }

    private ArrayList<ProductModel> join2ArrayListString2ProductListArray(ArrayList<String> productsNames, ArrayList<String> productsAmounts){
        ArrayList<ProductModel> products = new ArrayList<>();
        // first add productsNames to arrayList
        for(String name:productsNames){
            ProductModel product = new ProductModel();
            product.setName(name);
            products.add(product);
        }
        // next get item from productslist and set amount of products
        for(int i=0;i< products.size();i++){
            ProductModel product = products.get(i);
            product.setAmount(productsAmounts.get(i));
        }
        return products;
    }
    public void setProductList(@NonNull RecyclerView recyclerView) {

        recyclerView.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext()));
        RecyclerAdapterProduct adapter = new RecyclerAdapterProduct(recipeView.getProductList(), activity);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void setStepList(@NonNull RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext()));
        RecyclerAdapterStep adapter = new RecyclerAdapterStep(recipeView.getSteps(), activity);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void setName(@NonNull TextView textView) {
        textView.setText(recipeView.getName());
    }

    public void setMealCount(TextView textView) {
        textView.setText("Ilość porcji " + recipeView.getMealCount());
    }

    public void setPrepareTime(TextView textView) {
        textView.setText("Czas " + recipeView.getPrepareTime());
    }

    public void setRate(TextView textView) {
        textView.setText("Ocena " + recipeView.getRate() + "/5");
    }

    public void setPopularity(TextView textView) {
        textView.setText("Popularność " + recipeView.getPopularity() + "/5");
    }

    public void setDescription(TextView textView) {
        textView.setText("Opis\n" + recipeView.getDescription());
    }


    public void saveToFavourites() {
//        favouriteRecipes.add(recipeView);
        favouriteMenuItem.add(menuList.get(cardItemPosition));
        favouriteRecipeImage.add(imageList.get(cardItemPosition));
        Toast.makeText(activity.getApplicationContext(), "Dodałeś przepis  " + recipeView.getName() + " do ulubionych", Toast.LENGTH_LONG).show();
    }

    public void rate(float rate) {
        // this funtion sends by api rate by user
        Toast.makeText(activity.getApplicationContext(), "Oceniłeś przepis na  " + rate + " gwiazdek", Toast.LENGTH_LONG).show();
    }

    public void setMealImage(ImageView image) {
        image.setImageResource(imageList.get(cardItemPosition));
    }


}
