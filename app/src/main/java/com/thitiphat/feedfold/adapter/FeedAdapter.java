package com.thitiphat.feedfold.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thitiphat.feedfold.model.FeedModel;
import com.thitiphat.feedfold.ItemDetailActivity;
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

    public List<FeedModel> getFeedList() {
        return feedList;
    }

    public void setFeedList(List<FeedModel> feedList, Context context) {
        this.feedList = feedList;
        this.context = context;
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvTitle, tvLink, tvDescription;

        public Holder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvLink = itemView.findViewById(R.id.tvLink);
        }

        @Override
        public void onClick(View v) {
            int pos = getLayoutPosition();
            if (getAdapterPosition() != RecyclerView.NO_POSITION) {
//                itemListener.reyclerViewListClicked(v, pos);
//                Toast.makeText(context, "U click + " + feedList.get(pos).getTitle(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ItemDetailActivity.class);
                intent.putExtra("title", feedList.get(pos).getTitle());
                intent.putExtra("description", feedList.get(pos).getDescription());
                intent.putExtra("link", feedList.get(pos).getLink());
                intent.putExtra("date", feedList.get(pos).getPubDate());
                intent.putExtra("creator", feedList.get(pos).getCreator());
                context.startActivity(intent);
            }

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
        TextView link = holder.tvLink;
//        if (position != 0) {
        title.setText(feedList.get(position).getTitle().toString());
        link.setText(feedList.get(position).getLink().toString());
//        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            description.setText(Html.fromHtml(feedList.get(position).getDescription().toString(), Html.FROM_HTML_MODE_COMPACT) + "\n");
//        } else {
//            description.setText(Html.fromHtml(feedList.get(position).getDescription().toString()) + "\n");
//        }
    }



    @Override
    public int getItemCount() {
        return feedList.size();
    }
}
