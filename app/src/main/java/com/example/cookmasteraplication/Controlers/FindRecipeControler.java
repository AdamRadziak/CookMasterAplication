package com.example.cookmasteraplication.Controlers;

import static com.example.cookmasteraplication.Controlers.CreateMenuController.createdRecipes;
import static com.example.cookmasteraplication.Controlers.CreateMenuController.imageListView;
import static com.example.cookmasteraplication.Controlers.CreateMenuController.menuListView;
import static com.example.cookmasteraplication.Controlers.CreateMenuController.recipeImageList;

import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookmasteraplication.Models.Adapters.GridAdapterProductsFindRecipe;
import com.example.cookmasteraplication.Models.Adapters.RecyclerAdapterMenu;
import com.example.cookmasteraplication.Models.Adapters.RecyclerAdapterRecipe;
import com.example.cookmasteraplication.Models.MenuCardItemModel;
import com.example.cookmasteraplication.Models.MenuModel;
import com.example.cookmasteraplication.Models.ProductModel;
import com.example.cookmasteraplication.Models.RecipeModel;
import com.example.cookmasteraplication.Models.ToolBarModel;
import com.example.cookmasteraplication.Views.FindRecipeActivity;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class FindRecipeControler {
    public FindRecipeActivity activity;
    public ArrayList<RecipeModel> recipes = new ArrayList<>();
    public ArrayList<String> productsAdapter = new ArrayList<>();
    public ArrayList<String> addingProducts = new ArrayList<>();
    public ArrayList<MenuModel> findingMenus = new ArrayList<>();
    public ArrayList<Integer> findingImages = new ArrayList<>();
    public RecyclerAdapterRecipe recyclerAdapter;
    public GridAdapterProductsFindRecipe gridAdapter;


    public FindRecipeControler(FindRecipeActivity activity) {
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

    public void setRecyclerView(RecyclerView recyclerView) {
        // crete example menu
        recyclerView.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext()));
        recyclerAdapter = new RecyclerAdapterRecipe(findingMenus, findingImages, activity);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.notifyDataSetChanged();

    }

    public void setAutoCompleteTextAdapter(AutoCompleteTextView textView){
        // get from recipes productsAdapter to list
        for(RecipeModel menuItem: createdRecipes){
            ArrayList<ProductModel> productList = menuItem.getProductList();
            for(ProductModel productItem: productList){
                productsAdapter.add(productItem.getName());
            }
        }
        // create array recyclerAdapter to autocomplete text
        ArrayAdapter<String> textAdapter=new ArrayAdapter<String>(activity.getApplicationContext(),android.R.layout.simple_dropdown_item_1line, productsAdapter);
        textView.setThreshold(3);
        textView.setAdapter(textAdapter);
    }

    public void addProductToGridView(Intent intent){
        String productName = intent.getStringExtra("productName");
        addingProducts.add(productName);
    }
    public void setGridView(GridView gridView){
        gridAdapter = new GridAdapterProductsFindRecipe(activity,addingProducts);
        gridView.setAdapter(gridAdapter);
        gridAdapter.notifyDataSetChanged();
    }

    public void searchProducts() {
        // first search in menu model
        for (RecipeModel menu : createdRecipes) {
            ArrayList<ProductModel> productsModel = menu.getProductList();
            // second search in addingProducts
            for (String product : addingProducts) {
                // second search in recipe product list
                for (ProductModel productItem : productsModel) {
                    String name = productItem.getName();
                    if (product.equals(name)) {
                        Integer index = createdRecipes.indexOf(menu);
                        MenuModel findingMenu = new MenuModel();
                        findingMenu.setRecipe(createdRecipes.get(index));
                        findingMenus.add(findingMenu);
                        findingImages.add(recipeImageList.get(index));
                    }
                }
            }
        }
    }

}
