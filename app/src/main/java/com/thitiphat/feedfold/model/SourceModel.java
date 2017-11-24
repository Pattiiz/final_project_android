package com.thitiphat.feedfold.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phatm on 11/24/2017.
 */

public class SourceModel {

    List<String> techUrl = new ArrayList<>();
    List<String> techName = new ArrayList<>();
    List<String> marketingUrl = new ArrayList<>();
    List<String> marketingName = new ArrayList<>();

    public SourceModel() {
        techUrl.add("https://www.blognone.com/atom.xml");
        techUrl.add("https://www.engadget.com/rss.xml");
        techUrl.add("http://feed.androidauthority.com");

        techName.add("Blognone");
        techName.add("Engadget");
        techName.add("Android Authority");
    }

    public List<String> getTechUrl() {
        return techUrl;
    }

    public void setTechUrl(List<String> techUrl) {
        this.techUrl = techUrl;
    }

    public List<String> getMarketingUrl() {
        return marketingUrl;
    }

    public void setMarketingUrl(List<String> marketingUrl) {
        this.marketingUrl = marketingUrl;
    }
}
