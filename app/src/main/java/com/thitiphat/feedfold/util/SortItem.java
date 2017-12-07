package com.thitiphat.feedfold.util;

import com.thitiphat.feedfold.util.FeedModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by phatm on 11/21/2017.
 */

public class SortItem {

    List<FeedModel> list = new ArrayList<>();

    public SortItem() {

    }

    public List<FeedModel> sortList(List<FeedModel> feedModelList) {

        for(int i = 0 ; i<feedModelList.size() ; i++) {
            list.add(feedModelList.get(i));
        }

        Collections.sort(list, new Comparator<FeedModel>() {
            @Override
            public int compare(FeedModel o1, FeedModel o2) {
                return o1.getPubDate().compareTo(o2.getPubDate());
            }
        });

        List<FeedModel> inverseList = new ArrayList<>();
        for (int i = list.size() - 1 ; i > 0 ; i--) {
            inverseList.add(list.get(i));
        }

        return inverseList;
    }

    public List<FeedModel> getList() {
        return list;
    }

    public void setList(List<FeedModel> list) {
        this.list = list;
    }

    public void clearList() {
        list.clear();
    }
}
