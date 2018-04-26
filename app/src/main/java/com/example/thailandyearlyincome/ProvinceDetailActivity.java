package com.example.thailandyearlyincome;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

public class ProvinceDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_province_detail);

        Intent intent = getIntent();
        Gson gson = new Gson();

        ProvinceInfo provinceInfo = gson.fromJson(intent.getStringExtra("province_detail"), ProvinceInfo.class);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(provinceInfo.getProvince());
        }

        String populationString = provinceInfo.getPerson() + ' ' + getString(R.string.person);
        String familiesString = provinceInfo.getFamily() + ' ' + getString(R.string.families);
        String incomeString = provinceInfo.getIncome() + ' ' + getString(R.string.baht_per_year);

        TextView textViewPopulation = findViewById(R.id.textview_population_count);
        TextView textViewFamilies = findViewById(R.id.textview_families_count);
        TextView textViewIncome = findViewById(R.id.textview_income_count);

        textViewPopulation.setText(populationString);
        textViewFamilies.setText(familiesString);
        textViewIncome.setText(incomeString);
    }
}
