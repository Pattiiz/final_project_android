package com.thitiphat.feedfold.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thitiphat.feedfold.R;
import com.thitiphat.feedfold.util.FeedModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phatm on 12/2/2017.
 */

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.Holder> {

    List<FeedModel> bookmarkList = new ArrayList<>();
    View view;
    Context context;

    public BookmarkAdapter() {

    }

    public void setBookmarkList(List<FeedModel> bookmarkList, Context context) {
        this.bookmarkList = bookmarkList;
        this.context = context;
    }

    @Override
    public BookmarkAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.activity_feed_item, null, false);
        BookmarkAdapter.Holder holder = new BookmarkAdapter.Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BookmarkAdapter.Holder holder, int position) {
        TextView title = holder.tvTitle;
        TextView date = holder.tvDate;
        title.setText(bookmarkList.get(position).getTitle());
        date.setText(bookmarkList.get(position).getPubDate());
    }

    @Override
    public int getItemCount() {
        return bookmarkList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        public TextView tvTitle, tvDate;

        public Holder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}
