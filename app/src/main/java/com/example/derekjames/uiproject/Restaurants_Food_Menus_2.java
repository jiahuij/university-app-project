package com.example.derekjames.uiproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.FormatFlagsConversionMismatchException;
import java.util.List;
import java.util.concurrent.ExecutionException;




public class Restaurants_Food_Menus_2 extends AppCompatActivity {


    ArrayList<ArrayList<String>> FoodItems = new ArrayList<ArrayList<String>>();


    String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants_food_menus2);


        /////Switch between tabs
        BottomNavigationView tabBar = (BottomNavigationView) findViewById(R.id.tabBar);
        disableTabBarShift(tabBar);

        tabBar.setSelectedItemId(R.id.restaurantsicon);
        tabBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.chimesicon: ///////Go to chimes page
                        Intent intent1 = new Intent(Restaurants_Food_Menus_2.this,
                                com.example.derekjames.uiproject.Chime_Main_Tab.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent1);
                        return true;
                    case R.id.mapicon:
                        Intent intent2 = new Intent(Restaurants_Food_Menus_2.this,
                                com.example.derekjames.uiproject.Map.class);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent2);
                        return true;
                    case R.id.buildinghoursicon:
                        Intent intent3 = new Intent(Restaurants_Food_Menus_2.this,
                                com.example.derekjames.uiproject.Hours.class);
                        intent3.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent3);
                        return true;
                    case R.id.restaurantsicon:
                        Intent intent4 = new Intent(Restaurants_Food_Menus_2.this,
                                com.example.derekjames.uiproject.Restaurants_Main.class);
                        intent4.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent4);
                        return true;
                }
                return true;
            }
        });

        Intent startIntent = getIntent();
        int ItemClicked = startIntent.getIntExtra("position", 0);
        url = startIntent.getStringExtra("url");



        ////////////////adds array into the listview
        final String[] restaurantsArray3 = new String[] {
                "Menu Item 1: jskldfjskldajkiojs",
                "Menu Item 3: jskldfjskldajfksds",
                "Menu Item 4: jskldfjskldajfeiojs",
                "Menu Item 5: jskldfjskiojs",
                "Menu Item 6: jskldfjskojs",
                "Menu Item 7: jskldfjskldajfksiojs",
        };
        final ListView restaurantListView = (ListView) findViewById(R.id.RestaurantsMenuListView2);
        final List<String> myList = new ArrayList<String>(Arrays.asList(restaurantsArray3));
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, myList) {
            @Override public View getView(int pos, View convertView, ViewGroup parent) {
                View view = super.getView(pos, convertView,parent);
                ViewGroup.LayoutParams parameters = view.getLayoutParams();
                parameters.height = 300;
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                view.setLayoutParams(parameters);
                text.setTextSize(20);
                return  view;
            }
        };
        //restaurantListView.setAdapter(arrayAdapter);

        try {
            Void ab = new jsoupconnect().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String ItemClickedString = startIntent.getStringExtra("type");
        TextView restaurantNameTextView = (TextView) findViewById(R.id.RestaurantTitle);
        restaurantNameTextView.setText(ItemClickedString);
        ArrayList<String> items = new ArrayList<String>(FoodItems.get(ItemClicked));

            final ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items) {
                @Override
                public View getView(int pos, View convertView, ViewGroup parent) {
                    View view = super.getView(pos, convertView, parent);
                    ViewGroup.LayoutParams parameters = view.getLayoutParams();
                    parameters.height = 300;
                    TextView text = (TextView) view.findViewById(android.R.id.text1);
                    view.setLayoutParams(parameters);
                    text.setTextSize(20);
                    return view;
                }
            };
            restaurantListView.setAdapter(arrayAdapter2);


    }

    @SuppressLint("RestrictedApi")
    private void  disableTabBarShift(BottomNavigationView view) {
        BottomNavigationMenuView tabBar = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = tabBar.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(tabBar, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < tabBar.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) tabBar.getChildAt(i);
                item.setShiftingMode(false);
                item.setChecked(item.getItemData().isChecked());
            }
        }catch (NoSuchFieldException e)
        {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        }catch (IllegalAccessException e)
        {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }










    public class jsoupconnect extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            org.jsoup.nodes.Document doc = null;
            //url = "https://www.tapingo.com/order/restaurant/eagles-nest-biola/";

            //String x = "";
            /*try {
                doc = Jsoup.connect(url).get();
                for (org.jsoup.nodes.Element row: doc.select("ul.categories li"))
                {

                   // FoodItem.add(row.select("> .name").text());
                    String temp = row.select("> .name").text();
                    Log.d("name",temp );
                }
            }catch (IOException e) {

            }*/
            try {
                doc = Jsoup.connect(url).get();
                int count = 0;
                int count2 = 0;
                List<String> tempList = new ArrayList<String>();
                for (org.jsoup.nodes.Element row: doc.select("ul.categories ul"))
                {
                    //tempList.clear();
                    //FoodItems.add(new ArrayList<String>());

                    for (org.jsoup.nodes.Element row2: row.select("li")) {
                        // FoodItem.add(row.select("> .name").text());
                        String temp = row2.select("> .name").text();
                        //Log.d("category:", String.valueOf(count));
                        //Log.d("name", temp);
                        //tempList.add(temp);
                        tempList.add(temp);
                        count2++;
                    }
                    //count++;
                    FoodItems.add(new ArrayList<String>(tempList));
                    tempList.clear();
                }
            }catch (IOException e) {

            }




            return null;
        }
    }








}
