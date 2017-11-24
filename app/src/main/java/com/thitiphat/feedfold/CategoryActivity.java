package com.thitiphat.feedfold;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;


import com.thitiphat.feedfold.adapter.CategoryAdapter;
import com.thitiphat.feedfold.model.CategoryModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        CategoryModel categoryModel = new CategoryModel();

        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");

        CategoryAdapter categoryAdapter = new CategoryAdapter();
        categoryAdapter.setCategoryModel(categoryModel);
        categoryAdapter.setContext(this);
//        categoryAdapter.setList(list);

        RecyclerView recyclerView = findViewById(R.id.rvCategoryList);
        recyclerView.setLayoutManager(new LinearLayoutManager(CategoryActivity.this));
        recyclerView.setAdapter(categoryAdapter);
    }
}
