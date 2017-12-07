package com.thitiphat.feedfold.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.thitiphat.feedfold.util.FeedModel;
import com.thitiphat.feedfold.activity.ItemDetailActivity;
import com.thitiphat.feedfold.R;
import com.thitiphat.feedfold.Interface.RecyclerViewClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phatm on 11/18/2017.
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.Holder> {

    List<FeedModel> feedList = new ArrayList<>();
    View view;
    Context context;
    RecyclerViewClickListener itemListener;

    public FeedAdapter() {

    }

    public FeedAdapter(Context context, RecyclerViewClickListener itemListener) {
        this.context = context;
        this.itemListener = itemListener;
    }

    public void setFeedList(List<FeedModel> feedList, Context context) {
        this.feedList = feedList;
        this.context = context;
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public TextView tvTitle, tvDate;

        public Holder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDate = itemView.findViewById(R.id.tvDate);
        }

        @Override
        public void onClick(View v) {
            int pos = getLayoutPosition();
            if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                Intent intent = new Intent(context, ItemDetailActivity.class);
                intent.putExtra("title", feedList.get(pos).getTitle());
                intent.putExtra("description", feedList.get(pos).getDescription());
                intent.putExtra("link", feedList.get(pos).getLink());
                intent.putExtra("date", feedList.get(pos).getPubDate());
                intent.putExtra("creator", feedList.get(pos).getCreator());
                context.startActivity(intent);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            int pos = getLayoutPosition();

            SharedPreferences sharedPreferences = context.getSharedPreferences("list", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            List<FeedModel> bookmarkList = new ArrayList<>();
            final String bookmark = sharedPreferences.getString("bookmark", null);

            if (getAdapterPosition() != RecyclerView.NO_POSITION) {

                if (bookmark == null) {
                    bookmarkList.add(feedList.get(pos));
                } else {
                    bookmarkList = new Gson().fromJson(bookmark, bookmarkList.getClass());
                    bookmarkList.add(feedList.get(pos));
                }

                final List<FeedModel> finalBookmarkList = bookmarkList;
                Snackbar.make(itemView, "add " + feedList.get(pos).getTitle(), Snackbar.LENGTH_LONG)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finalBookmarkList.remove(finalBookmarkList.size() - 1);
                            }
                        }).show();

                String bm = new Gson().toJson(bookmarkList);
                editor.putString("bookmark", bm);
                editor.apply();
                return true;
            }

            return false;
        }
    }

    @Override
    public FeedAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.activity_feed_item, null, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        TextView title = holder.tvTitle;
        TextView date = holder.tvDate;
        title.setText(feedList.get(position).getTitle());
        date.setText(feedList.get(position).getPubDate());
    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }
}
