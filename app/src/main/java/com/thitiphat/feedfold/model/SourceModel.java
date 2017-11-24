package com.thitiphat.feedfold.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phatm on 11/24/2017.
 */

public class SourceModel {

    List<String> techList = new ArrayList<>();
    List<String> MarketingList = new ArrayList<>();

    public SourceModel() {
        techList.add("a");
        techList.add("b");
        techList.add("c");
        techList.add("A");
    }

    public List<String> getTechList() {
        return techList;
    }

    public void setTechList(List<String> techList) {
        this.techList = techList;
    }

    public List<String> getMarketingList() {
        return MarketingList;
    }

    public void setMarketingList(List<String> marketingList) {
        MarketingList = marketingList;
    }
}
