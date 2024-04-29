package com.example.cookmasteraplication.Controlers;

import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookmasteraplication.Models.Adapters.RecyclerAdapterMenu;
import com.example.cookmasteraplication.Models.MenuCardItemModel;
import com.example.cookmasteraplication.Models.MenuModel;
import com.example.cookmasteraplication.Models.ProductModel;
import com.example.cookmasteraplication.Models.RecipeModel;
import com.example.cookmasteraplication.Models.SavedMenuModel;
import com.example.cookmasteraplication.Models.ToolBarModel;
import com.example.cookmasteraplication.R;
import com.example.cookmasteraplication.Views.CreateMenuActivity;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class CreateMenuController {

    public static ArrayList<MenuModel> menuListView = new ArrayList<>();
    public static ArrayList<RecipeModel> createdRecipes = new ArrayList<>();
    public static ArrayList<SavedMenuModel> menuListSave = new ArrayList<>();
    public static ArrayList<Integer> imageListView = new ArrayList<>();
    public static ArrayList<Integer> imageListSave = new ArrayList<>();
    public static ArrayList<Integer> recipeImageList = new ArrayList<>();
    private final CreateMenuActivity activity;
    private RecyclerAdapterMenu adapter;


    public CreateMenuController(CreateMenuActivity activity) {
        this.activity = activity;
        LoadExampleRecipes();
    }

    private static <T> ArrayList<T> removeDuplicates(ArrayList<T> list) {

        // Create a new ArrayList
        ArrayList<T> newList = new ArrayList<T>();

        // Traverse through the first list
        for (T element : list) {

            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {

                newList.add(element);
            }
        }
        return newList;
    }

    public void setSpinnerArrayAdapter(@NonNull Spinner spinner, int string_list_id) {
        ArrayAdapter adapter = ArrayAdapter.createFromResource(activity.getApplicationContext(), string_list_id, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }

    public void setToolbarLogo(MaterialToolbar toolbar, String pageName) {
        ToolBarModel toolbarmodel = new ToolBarModel.Builder(activity,
                toolbar).create().setToolbarSubtitle(pageName).build();
    }

    public void setToolbarMenu(MaterialToolbar toolbar) {
        ToolBarModel toolbarmodel = new ToolBarModel.Builder(activity,
                toolbar).create().setMenuOItemOnClickListeners().build();
    }

    public void findMenus(@NonNull Intent intent, String daysCountIntentVar, String mealCountIntentVar, String prepareTimeIntentVar, String rateIntentVar, String popularityIntentVar) {
        /// this method send post request to find recipes with parameters
        // if server get code 200 then message and container results is displayed
        String daysCount = intent.getStringExtra(daysCountIntentVar);
        String mealCount = intent.getStringExtra(mealCountIntentVar);
        String prepareTime = intent.getStringExtra(prepareTimeIntentVar);
        String rate = intent.getStringExtra(rateIntentVar);
        String popularity = intent.getStringExtra(popularityIntentVar);
        createExampleMenu();
        String msg = "days " + daysCount + " mealCount " + mealCount + " prepare time " + prepareTime + " rate " + rate + " popularity " + popularity;
        Toast.makeText(activity.getApplicationContext(), msg, Toast.LENGTH_LONG).show();

    }

    public void saveMenus(String menuName) {
        /// this method send post request to find recipes with parameters
        // if server get code 200 then message and container results is displayed

        if (menuName.isEmpty()) {
            menuName = "domyślne";
        }
        if (menuListView.isEmpty()) {
            Toast.makeText(activity.getApplicationContext(), "Nie można zapisać pustego menu", Toast.LENGTH_LONG).show();
        } else {
            SavedMenuModel savedMenu = new SavedMenuModel();
            savedMenu.setName(menuName);
            // remove duplicates in menuList saved by user
            ArrayList<MenuModel> tempMenuListSave = new ArrayList<>();
            tempMenuListSave.addAll(menuListView);
            tempMenuListSave = removeDuplicates(tempMenuListSave);
            savedMenu.setMenu(tempMenuListSave);

            menuListSave.add(savedMenu);
            // remove duplicates in menulistSave
            menuListSave = removeDuplicates(menuListSave);

            imageListSave.addAll(imageListView);
            // remove duplicates in menulistSave
            imageListSave = removeDuplicates(imageListSave);
            Toast.makeText(activity.getApplicationContext(), "Zapisano menu o nazwie " + menuName, Toast.LENGTH_SHORT).show();
        }


    }

    public void setRecyclerView(RecyclerView recyclerView) {
        // crete example menu
        recyclerView.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext()));
        adapter = new RecyclerAdapterMenu(menuListView, imageListView, activity);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    public void LoadExampleRecipes(){
        RecipeModel breakfast1 = new RecipeModel();
        RecipeModel dinner1 = new RecipeModel();
        RecipeModel supper1 = new RecipeModel();

        ArrayList<ProductModel> breakfast1_products;
        ArrayList<ProductModel> dinner1_products;
        ArrayList<ProductModel> supper1_products;

        ArrayList<String> breakfast1_steps;
        ArrayList<String> dinner1_steps;
        ArrayList<String> supper1_steps;

        breakfast1_products = createProductsBreak1();
        breakfast1_steps = createStepsBreak1();
        dinner1_products = createProductsDinner1();
        dinner1_steps = createStepsDinn1();
        supper1_products = createProductsSupp1();
        supper1_steps = createStepsSup1();

        breakfast1.setProductList(breakfast1_products);
        breakfast1.setSteps(breakfast1_steps);
        breakfast1.setName("jajecznica");
        breakfast1.setMealCount("2 porcje");
        breakfast1.setPrepareTime("30 min");
        breakfast1.setRate("5");
        breakfast1.setPopularity("5");
        breakfast1.setDescription("Proste i szybkie śniadanie");

        dinner1.setProductList(dinner1_products);
        dinner1.setSteps(dinner1_steps);
        dinner1.setName("ryba po grecku");
        dinner1.setMealCount("8 porcji");
        dinner1.setPrepareTime("60 mmin");
        dinner1.setRate("5");
        dinner1.setPopularity("5");
        dinner1.setDescription("Obiad na niedzielę badź święta. Syty, smaczny i zdrowy");

        supper1.setProductList(supper1_products);
        supper1.setSteps(supper1_steps);
        supper1.setName("sałatka z batatów");
        supper1.setMealCount("2 porcje");
        supper1.setPrepareTime("40 min");
        supper1.setRate("5");
        supper1.setPopularity("5");
        supper1.setDescription("Lekka sałatka z batatów na letnią kolację");


        createdRecipes.add(breakfast1);
        createdRecipes.add(dinner1);
        createdRecipes.add(supper1);

        recipeImageList.add(R.drawable.jajecznica);
        recipeImageList.add(R.drawable.ryba_po_grecku);
        recipeImageList.add(R.drawable.salatka_z_batatow);

    }
    private void createExampleMenu() {
        MenuModel model1 = new MenuModel();
        MenuModel model2 = new MenuModel();
        MenuModel model3 = new MenuModel();
        MenuCardItemModel breakfast1 = new MenuCardItemModel();
        MenuCardItemModel dinner1 = new MenuCardItemModel();
        MenuCardItemModel supper1 = new MenuCardItemModel();
        model1.setName("1 dzień  - śniadanie");
        breakfast1.setCategory("śniadanie");
        breakfast1.setName("jajecznica");
        breakfast1.setPrepareTime("30 min");
        breakfast1.setRate("5");
        breakfast1.setPopularity("5");
        model1.setMeal(breakfast1);
        model2.setName("1 dzień  - obiad");
        dinner1.setCategory("obiad");
        dinner1.setName("ryba po grecku");
        dinner1.setPrepareTime("60 min");
        dinner1.setRate("5");
        dinner1.setPopularity("5");
        model2.setMeal(dinner1);
        model3.setName("1 dzień  - kolacja");
        supper1.setCategory("kolacja");
        supper1.setName("sałatka z batatów");
        supper1.setPrepareTime("40 min");
        supper1.setRate("5");
        supper1.setPopularity("5");
        model3.setMeal(supper1);
        menuListView.add(model1);
        menuListView.add(model2);
        menuListView.add(model3);
        createExampleRecipe();
        // add image to model
        imageListView.add(R.drawable.jajecznica);
        imageListView.add(R.drawable.ryba_po_grecku);
        imageListView.add(R.drawable.salatka_z_batatow);
        menuListView = removeDuplicates(menuListView);
        imageListView = removeDuplicates(imageListView);

    }


    private void createExampleRecipe() {
        RecipeModel breakfast1 = new RecipeModel();
        RecipeModel dinner1 = new RecipeModel();
        RecipeModel supper1 = new RecipeModel();

        ArrayList<ProductModel> breakfast1_products;
        ArrayList<ProductModel> dinner1_products;
        ArrayList<ProductModel> supper1_products;

        ArrayList<String> breakfast1_steps;
        ArrayList<String> dinner1_steps;
        ArrayList<String> supper1_steps;

        breakfast1_products = createProductsBreak1();
        breakfast1_steps = createStepsBreak1();
        dinner1_products = createProductsDinner1();
        dinner1_steps = createStepsDinn1();
        supper1_products = createProductsSupp1();
        supper1_steps = createStepsSup1();

        breakfast1.setProductList(breakfast1_products);
        breakfast1.setSteps(breakfast1_steps);
        breakfast1.setName(menuListView.get(0).getMeal().getName());
        breakfast1.setMealCount("2 porcje");
        breakfast1.setPrepareTime(menuListView.get(0).getMeal().getPrepareTime());
        breakfast1.setRate(menuListView.get(0).getMeal().getRate());
        breakfast1.setPopularity(menuListView.get(0).getMeal().getPopularity());
        breakfast1.setDescription("Proste i szybkie śniadanie");

        dinner1.setProductList(dinner1_products);
        dinner1.setSteps(dinner1_steps);
        dinner1.setName(menuListView.get(1).getMeal().getName());
        dinner1.setMealCount("8 porcji");
        dinner1.setPrepareTime(menuListView.get(1).getMeal().getPrepareTime());
        dinner1.setRate(menuListView.get(1).getMeal().getRate());
        dinner1.setPopularity(menuListView.get(1).getMeal().getPopularity());
        dinner1.setDescription("Obiad na niedzielę badź święta. Syty, smaczny i zdrowy");

        supper1.setProductList(supper1_products);
        supper1.setSteps(supper1_steps);
        supper1.setName(menuListView.get(2).getMeal().getName());
        supper1.setMealCount("2 porcje");
        supper1.setPrepareTime(menuListView.get(2).getMeal().getPrepareTime());
        supper1.setRate(menuListView.get(2).getMeal().getRate());
        supper1.setPopularity(menuListView.get(2).getMeal().getPopularity());
        supper1.setDescription("Lekka sałatka z batatów na letnią kolację");

        menuListView.get(0).setRecipe(breakfast1);
        menuListView.get(1).setRecipe(dinner1);
        menuListView.get(2).setRecipe(supper1);

        createdRecipes.add(breakfast1);
        createdRecipes.add(dinner1);
        createdRecipes.add(supper1);

    }

    private ArrayList<ProductModel> createProductsBreak1() {
        ArrayList<ProductModel> products = new ArrayList<>();
        ProductModel prod1 = new ProductModel();
        prod1.setName("Jajka");
        prod1.setCategory("Ogólne");
        prod1.setAmount("4");
        ProductModel prod2 = new ProductModel();
        prod2.setName("Bekon");
        prod2.setCategory("Produkty mięsne");
        prod2.setAmount("100g");
        ProductModel prod3 = new ProductModel();
        prod3.setName("Natka pietruszki");
        prod3.setCategory("Przyprawy");
        prod3.setAmount("1 pęczek");
        ProductModel prod4 = new ProductModel();
        prod4.setName("Masło klarowane");
        prod4.setCategory("Produkty mleczne");
        prod4.setAmount("1 łyżka");
        ProductModel prod5 = new ProductModel();
        prod5.setName("Pieprz");
        prod5.setCategory("Przyprawy");
        prod5.setAmount("szczypta");
        ProductModel prod6 = new ProductModel();
        prod6.setName("Sól");
        prod6.setCategory("Przyprawy");
        prod6.setAmount("szczypta");
        ProductModel prod7 = new ProductModel();
        prod7.setName("Cebula");
        prod7.setCategory("Warzywa");
        prod7.setAmount("2 małe");

        products.add(prod1);
        products.add(prod2);
        products.add(prod3);
        products.add(prod4);
        products.add(prod5);
        products.add(prod6);
        products.add(prod7);

        return products;
    }

    private ArrayList<ProductModel> createProductsDinner1() {
        ArrayList<ProductModel> products = new ArrayList<>();
        ProductModel prod1 = new ProductModel();
        prod1.setName("dorsz");
        prod1.setCategory("Ryby");
        prod1.setAmount("700g");
        ProductModel prod2 = new ProductModel();
        prod2.setName("Mąka pszenna");
        prod2.setCategory("Ogólne");
        prod2.setAmount("pół szklanki - 80g");
        ProductModel prod3 = new ProductModel();
        prod3.setName("Sól");
        prod3.setCategory("Przyprawy");
        prod3.setAmount("2 łyżeczki");
        ProductModel prod4 = new ProductModel();
        prod4.setName("Pieprz");
        prod4.setCategory("Przyprawy");
        prod4.setAmount("łyżeczka");
        ProductModel prod5 = new ProductModel();
        prod5.setName("Oregano");
        prod5.setCategory("Przyprawy");
        prod5.setAmount("łyżeczka");
        ProductModel prod6 = new ProductModel();
        prod6.setName("Marchewka");
        prod6.setCategory("Warzywa");
        prod6.setAmount("4 średnie - 500g");
        ProductModel prod7 = new ProductModel();
        prod7.setName("Korzeń pietruszki");
        prod7.setCategory("Warzywa");
        prod7.setAmount("2 średnie");
        ProductModel prod8 = new ProductModel();
        prod8.setName("Cebula");
        prod8.setCategory("Warzywa");
        prod8.setAmount("1 duża");
        ProductModel prod9 = new ProductModel();
        prod9.setName("Woda");
        prod9.setCategory("Ogólne");
        prod9.setAmount("pół szklanki");
        ProductModel prod10 = new ProductModel();
        prod10.setName("Przecier pomidorowy");
        prod10.setCategory("Ogólne");
        prod10.setAmount("4 łyżki");
        ProductModel prod11 = new ProductModel();
        prod11.setName("Ziele angielskie");
        prod11.setCategory("Przyprawy");
        prod11.setAmount("3 ziarna");
        ProductModel prod12 = new ProductModel();
        prod12.setName("Liść laurowy");
        prod12.setCategory("Przyprawy");
        prod12.setAmount("2 listki");
        ProductModel prod13 = new ProductModel();
        prod13.setName("Słodka paryka");
        prod13.setCategory("Przyprawy");
        prod13.setAmount("1 łyżeczka");


        products.add(prod1);
        products.add(prod2);
        products.add(prod3);
        products.add(prod4);
        products.add(prod5);
        products.add(prod6);
        products.add(prod7);
        products.add(prod8);
        products.add(prod9);
        products.add(prod10);
        products.add(prod11);
        products.add(prod12);
        products.add(prod13);

        return products;
    }

    private ArrayList<ProductModel> createProductsSupp1() {
        ArrayList<ProductModel> products = new ArrayList<>();
        ProductModel prod1 = new ProductModel();
        prod1.setName("batat");
        prod1.setCategory("Warzywa");
        prod1.setAmount("1 - 300g");
        ProductModel prod2 = new ProductModel();
        prod2.setName("Pomidory koktajlowe");
        prod2.setCategory("Warzywa");
        prod2.setAmount("2 garście - 150g");
        ProductModel prod3 = new ProductModel();
        prod3.setName("Oregano");
        prod3.setCategory("Przyprawy");
        prod3.setAmount("1 łyżeczka");
        ProductModel prod4 = new ProductModel();
        prod4.setName("Chili");
        prod4.setCategory("Przyprawy");
        prod4.setAmount("szczypta");
        ProductModel prod5 = new ProductModel();
        prod5.setName("Oliwa");
        prod5.setCategory("Oleje");
        prod5.setAmount("2 łyżki");
        ProductModel prod6 = new ProductModel();
        prod6.setName("Szpinak");
        prod6.setCategory("Warzywa");
        prod6.setAmount("50g świeżego umytego");
        ProductModel prod7 = new ProductModel();
        prod7.setName("Ser sałatkowy/feta");
        prod7.setCategory("Nabiał");
        prod7.setAmount("125 g");
        ProductModel prod8 = new ProductModel();
        prod8.setName("Awokado");
        prod8.setCategory("Owoce");
        prod8.setAmount("1 średnie");
        ProductModel prod9 = new ProductModel();
        prod9.setName("Cebula");
        prod9.setCategory("Warzywa");
        prod9.setAmount("ćwiartka średniej");
        ProductModel prod10 = new ProductModel();
        prod10.setName("Musztarda miodowa lub dijon");
        prod10.setCategory("Przyprawy");
        prod10.setAmount("2 łyżeczki");
        ProductModel prod11 = new ProductModel();
        prod11.setName("Miód");
        prod11.setCategory("Ogólne");
        prod11.setAmount("2 łyżeczki");
        ProductModel prod12 = new ProductModel();
        prod12.setName("Cytryna");
        prod12.setCategory("Owoce");
        prod12.setAmount("połówka na sok");


        products.add(prod1);
        products.add(prod2);
        products.add(prod3);
        products.add(prod4);
        products.add(prod5);
        products.add(prod6);
        products.add(prod7);
        products.add(prod8);
        products.add(prod9);
        products.add(prod10);
        products.add(prod11);
        products.add(prod12);

        return products;
    }

    private ArrayList<String> createStepsBreak1() {
        ArrayList<String> steps = new ArrayList<>();
        steps.add("Pokroić cebulkę i bekon w kosteczkę. Natkę pietruszki na drobno. Bekon w kosteczkę");
        steps.add("Roztopic masło na patelni. Po roztopieniu masła wrzucić cebulkę i bekon. Smażyć do zrumienienia bekonu");
        steps.add("Dodać jajka oraz doprawić sola i pieprzem. Cały czas mieszając aż do ścięcia jajek");
        steps.add("Dodać pokrojoną wcześniej natkę pietruszki. Smacznego");


        return steps;
    }

    private ArrayList<String> createStepsDinn1() {
        ArrayList<String> steps = new ArrayList<>();
        steps.add("Jeśli masz świeżą rybę, to wystarczy ją szybko przepłukać pod zimną, bieżącą wodą. " +
                "Następnie dobrze osuszyć ręcznikiem papierowym i pokroić na mniejsze kawałki. " +
                "Każdy kawałek oprósz z obu stron mieszanką soli, pieprzu i oregano. Kawałki ryby odłóż do miski. " +
                "Jeśli masz rybę mrożoną, to koniecznie wyjmij ją wcześniej z zamrażarki i zostaw ją do naturalnego rozmrożenia." +
                " Możesz przyspieszyć ten proces płucząc filety w zlewie. Woda ma być zimna, bieżąca. Glazura zejdzie z ryby." +
                " Ryba będzie gotowa do suszenia, gdy zrobi się elastyczna. Taką rybę przed osuszaniem na ręczniku delikatnie " +
                "odciskam dłońmi z nadmiaru wody. Dalej postępuj tak samo, jak w przypadku świeżej ryby.");
        steps.add("Pół szklanki mąki wysyp na talerz. Każdy kawałek ryby obtaczaj z obu stron w mące i układaj na nagrzanej patelni. " +
                "Wcześniej oczywiście wlej też olej odrobinę oleju roślinnego do smażenia. Kawałki ryby smaż z obu stron na średniej " +
                "mocy palnika." +
                " Powinno wystarczyć po 3-4 minuty z obu stron. Ryba powinna się tylko delikatnie zarumienić. " +
                "Jeśli się przypieka, zmniejsz moc palnika. Gotowe filety wyłóż na szeroki talerz." +
                "Porada: Po każdej partii smażonych kawałków ryby, polecam ostrożnie oczyścić patelnię z przypalonej mąki " +
                "(kawałkiem ręcznika papierowego), a następnie wlać nową porcję oleju i dalej smażyć rybę.");
        steps.add("Cebulę zeszklij na patelni z 1/5 szklanki oleju . ");
        steps.add("Po kilku minutach dodaj też starte na tarce, na grubych oczkach, marchewki i pietruszki." +
                " Warzywa warto zetrzeć siebie wcześniej. Dodaj też ziele angielskie, liście laurowe, " +
                "słodką paprykę i sól. Wlej pół szklanki wody, całość zamieszaj i przykryj przykrywką." +
                " Warzywa duś tak na małej mocy palnika przez około 20 minut. ");
        steps.add("Po tym czasie dodaj 4 łyżki przecieru pomidorowego." +
                " Wymieszaj całość i podsmażaj chwilę już bez przykrycia. Warzywa powinny być już miękkie.");
        steps.add("Do naczynia w którym planujesz podać rybę, umieść część duszonych warzyw. " +
                "Na warzywa wyłóż smażoną rybę. Na rybę zaś resztę warzyw.");
        steps.add("Całość można udekorować ziołami: oregano, pietruszką, koperkiem lub kolendrą." +
                " Ryba po grecku jest już gotowa do podania");


        return steps;
    }

    private ArrayList<String> createStepsSup1() {
        ArrayList<String> steps = new ArrayList<>();
        steps.add("Bataty obrać, pokroić w plasterki. Pomidorki pokroić na połówki.");
        steps.add("Bataty posmarować 1 łyżką oliwy i ułożyć na blaszce. Piec przez 20 minut w temp. 220 C");
        steps.add("Resztę warzyw doprawić solą, piperzem, oregano i chili. Wymieszać w salaterce");
        steps.add("Awokado obrać i pokroić na kawałki. Cebulę poszatkować na cienkie plasterki. ");
        steps.add("Do warzyw dodać szpinak, awokado, cebulę wraz z upieczonymi batatami ( sprawdzić wcześniej czy bataty są miękkie) ");
        steps.add("Połączyć składniki na sos: 2 łyżeczki musztardy, 2 łyżeczki miodu, 1 łyżka oliwy, sok z wyciśniętej połówki cytryny");
        steps.add("Posypać pokruszoną fetą i polać sosem. Wymieszać i zjeść ze smakiem");


        return steps;
    }
}
