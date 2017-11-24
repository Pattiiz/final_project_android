package com.thitiphat.feedfold.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thitiphat.feedfold.CategoryActivity;
import com.thitiphat.feedfold.R;
import com.thitiphat.feedfold.SourceActivity;
import com.thitiphat.feedfold.model.CategoryModel;

import java.util.List;

/**
 * Created by phatm on 11/24/2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Holder> {

    CategoryModel categoryModel;
    View view;
    List<String> list;
    Context  context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public CategoryAdapter() {
    }

    public CategoryModel getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(CategoryModel categoryModel) {
        this.categoryModel = categoryModel;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvCategoryName;
        ImageView ivCategory;

        public Holder(View itemView) {
            super(itemView);
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
            ivCategory = itemView.findViewById(R.id.ivCategory);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getLayoutPosition();
            if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                Intent intent = new Intent(context, SourceActivity.class);
                intent.putExtra("category", categoryModel.getCategory().get(pos));
                context.startActivity(intent);
            }
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.category_item, null, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        TextView name = holder.tvCategoryName;
        ImageView image = holder.ivCategory;

        name.setText(categoryModel.getCategory().get(position));
    }

    @Override
    public int getItemCount() {
        return categoryModel.getCategory().size();
    }
}
