package com.thitiphat.feedfold;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;


import com.thitiphat.feedfold.model.CategoryModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener {

    CategoryModel categoryModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Select Category");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        categoryModel = new CategoryModel();

        ImageView ivTech = findViewById(R.id.ivCategoryTech);
        ivTech.setOnClickListener(this);

        ImageView ivMark = findViewById(R.id.ivCategoryMark);
        ivMark.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivCategoryTech) {
            Intent intent = new Intent(this, SourceActivity.class);
            intent.putExtra("category", categoryModel.getCategory().get(0));
            startActivity(intent);
        }
        if (v.getId() == R.id.ivCategoryMark) {
            Intent intent = new Intent(this, SourceActivity.class);
            intent.putExtra("category", categoryModel.getCategory().get(1));
            startActivity(intent);
        }
    }
}
