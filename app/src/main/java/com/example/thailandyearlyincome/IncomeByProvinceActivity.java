package com.example.thailandyearlyincome;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class IncomeByProvinceActivity extends AppCompatActivity {

    private ArrayList<ProvinceInfo> provinceInfo;
    private ArrayList<ProvinceInfo> filteredProvinceInfo;
    private ArrayAdapter<ProvinceInfo> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_by_province);

        // Create array from raw file
        InputStream inputStream = getResources().openRawResource(R.raw.bmn_58_income_simplified);
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream));
        Gson gson = new Gson();
        ProvinceInfo[] tempProvinceInfo = gson.fromJson(reader, ProvinceInfo[].class);
        provinceInfo = new ArrayList<>(Arrays.asList(tempProvinceInfo));
        filteredProvinceInfo = new ArrayList<>(provinceInfo);

        // Create array adapter
        arrayAdapter = new ArrayAdapter<ProvinceInfo>(this,
                android.R.layout.simple_list_item_2, android.R.id.text1, filteredProvinceInfo) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView1 = view.findViewById(android.R.id.text1);
                TextView textView2 = view.findViewById(android.R.id.text2);

                String incomeText = filteredProvinceInfo.get(position).getIncome() + ' ' + getString(R.string.baht_per_year);
                textView1.setText(filteredProvinceInfo.get(position).getProvince());
                textView2.setText(incomeText);

                return view;
            }

            @Override
            public int getCount() {
                return filteredProvinceInfo.size();
            }
        };

        // Handle intent
        handleIntent(getIntent());

        // Display list
        ListView listView = findViewById(R.id.listview_province);
        listView.setAdapter(arrayAdapter);

        // Click an item to view its detail
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Gson gson = new Gson();
                String detail = gson.toJson(filteredProvinceInfo.get(i));
                Intent intent = new Intent(getApplicationContext(), ProvinceDetailActivity.class);
                intent.putExtra("province_detail", detail);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            ArrayList<ProvinceInfo> tempFilteredProvinceInfo = new ArrayList<>();
            for (ProvinceInfo item : provinceInfo) {
                if (item.getProvince().contains(query)) {
                    tempFilteredProvinceInfo.add(item);
                }
            }
            filteredProvinceInfo = new ArrayList<>(tempFilteredProvinceInfo);
            arrayAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            SearchView searchView = (SearchView)
                    menu.findItem(R.id.app_bar_search).getActionView();
            searchView.setSearchableInfo(
                    searchManager.getSearchableInfo(getComponentName()));
            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    filteredProvinceInfo = new ArrayList<>(provinceInfo);
                    arrayAdapter.notifyDataSetChanged();
                    Log.d("searchView", "closed");
                    return false;
                }
            });
        }

        return true;
    }
}
