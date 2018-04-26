package com.example.thailandyearlyincome;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.InputStream;
import java.io.InputStreamReader;

public class IncomeByProvinceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_by_province);

        // Create array from raw file
        InputStream inputStream = getResources().openRawResource(R.raw.bmn_58_income_simplified);
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream));
        Gson gson = new Gson();
        final ProvinceInfo[] provinceInfo = gson.fromJson(reader, ProvinceInfo[].class);

        // Create array adapter
        ArrayAdapter<ProvinceInfo> arrayAdapter = new ArrayAdapter<ProvinceInfo>(this,
                android.R.layout.simple_list_item_2, android.R.id.text1, provinceInfo) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView1 = view.findViewById(android.R.id.text1);
                TextView textView2 = view.findViewById(android.R.id.text2);

                String incomeText = provinceInfo[position].getIncome() + ' ' + getString(R.string.baht_per_year);
                textView1.setText(provinceInfo[position].getProvince());
                textView2.setText(incomeText);

                return view;
            }
        };

        // Display list
        ListView listView = findViewById(R.id.listview_province);
        listView.setAdapter(arrayAdapter);

        // Click an item to view its detail
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Gson gson = new Gson();
                String detail = gson.toJson(provinceInfo[i]);
                Intent intent = new Intent(getApplicationContext(), ProvinceDetailActivity.class);
                intent.putExtra("province_detail", detail);
                startActivity(intent);
            }
        });
    }
}
