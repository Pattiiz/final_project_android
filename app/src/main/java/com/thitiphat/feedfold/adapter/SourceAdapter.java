package com.thitiphat.feedfold.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.thitiphat.feedfold.R;
import com.thitiphat.feedfold.SourceActivity;
import com.thitiphat.feedfold.model.SourceModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phatm on 11/24/2017.
 */

public class SourceAdapter extends RecyclerView.Adapter<SourceAdapter.Holder> {

    View view;
    Context context;
    SourceModel sourceModel;
    String category;


    public void setContext(Context context) {
        this.context = context;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public SourceModel getSourceModel() {
        return sourceModel;
    }

    public void setSourceModel(SourceModel sourceModel) {
        this.sourceModel = sourceModel;
    }

    @Override
    public SourceAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.source_item, null, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(SourceAdapter.Holder holder, int position) {
        TextView name = holder.textView;
        ImageView image = holder.imageView;
        if (category.equals("Technology")) {
            name.setText(sourceModel.getTechList().get(position));
        }
        if (category.equals("Marketing")) {
            name.setText(sourceModel.getMarketingList().get(position));
        }

    }

    @Override
    public int getItemCount() {
        if (category.equals("Technology")) {
            return sourceModel.getTechList().size();
        }
        if (category.equals("Marketing")) {
            return sourceModel.getMarketingList().size();
        }
        return 0;
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView;
        ImageView imageView;

        public Holder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tvSrcName);
            imageView = itemView.findViewById(R.id.ivLogo);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int pos = getLayoutPosition();

            SharedPreferences sharedPreferences = context.getSharedPreferences("list", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            List<String> srcList = new ArrayList<>();
            String src = sharedPreferences.getString("json", null);
            if (src == null) {
                if (category.equals("Technology")) {
                    srcList.add(sourceModel.getTechList().get(pos));
                    Toast.makeText(context, sourceModel.getTechList().get(pos), Toast.LENGTH_SHORT).show();
                }
                if (category.equals("Marketing")) {
                    srcList.add(sourceModel.getMarketingList().get(pos));
                }
                String json = new Gson().toJson(srcList);
                editor.putString("json", json);
            } else {
                srcList = new Gson().fromJson(src, List.class);
                if (category.equals("Technology")) {
                    srcList.add(sourceModel.getTechList().get(pos));
                }
                if (category.equals("Marketing")) {
                    srcList.add(sourceModel.getMarketingList().get(pos));
                }
                String json = new Gson().toJson(srcList);
                editor.putString("json", json);
            }
            editor.apply();
        }
    }
}
