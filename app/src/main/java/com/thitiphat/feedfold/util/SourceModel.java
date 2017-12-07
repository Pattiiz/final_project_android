package com.thitiphat.feedfold.util;

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
    List<String> designUrl = new ArrayList<>();
    List<String> designName = new ArrayList<>();
    List<String> ptgUrl = new ArrayList<>();
    List<String> ptgName = new ArrayList<>();

    public SourceModel() {
        techUrl.add("https://www.blognone.com/atom.xml");
        techUrl.add("https://www.engadget.com/rss.xml");
        techUrl.add("http://feed.androidauthority.com");
        techUrl.add("https://feeds.feedburner.com/laptopmag");
        techUrl.add("http://www.tomshardware.com/feeds/rss2/articles.xml");
        techUrl.add("http://feeds.feedburner.com/tonymacx86rss");

        techName.add("Blognone");
        techName.add("Engadget");
        techName.add("Android Authority");
        techName.add("Laptopmag");
        techName.add("Tom's Hardware");
        techName.add("tonymacx86");

        marketingName.add("CNN Money");
        marketingName.add("Mashable");
        marketingName.add("Copyblogger");
        marketingName.add("Digital Marketer");

        marketingUrl.add("http://rss.cnn.com/rss/money_topstories.rss");
        marketingUrl.add("http://feeds.mashable.com/Mashable");
        marketingUrl.add("http://feeds.copyblogger.com/copyblogger");
        marketingUrl.add("https://www.digitalmarketer.com/feed/");

        designName.add("Fubiz");
        designName.add("DesignMilk Art");
        designName.add("DesignMilk Architecture");
        designName.add("Yanko Design");
        designName.add("Fresh Home");
        designName.add("Dezeen");

        designUrl.add("http://feeds.feedburner.com/fubiz");
        designUrl.add("http://feeds.feedburner.com/DesignMilkArt");
        designUrl.add("http://feeds.feedburner.com/DesignMilkArchitecture");
        designUrl.add("http://feeds.feedburner.com/yankodesign");
        designUrl.add("http://feeds.feedburner.com/FreshInspirationForYourHome");
        designUrl.add("http://feeds.feedburner.com/dezeen");

        ptgName.add("500px: Editor's Choice");
        ptgName.add("Daily Puppy");
        ptgName.add("Fubiz");
        ptgName.add("Outdoor Photographer");

        ptgUrl.add("https://500px.com/editors.rss");
        ptgUrl.add("http://feeds.feedburner.com/TheDailyPuppy");
        ptgUrl.add("http://feeds.feedburner.com/fubiz");
        ptgUrl.add("https://www.outdoorphotographer.com/feed/");
    }

    public List<String> getTechName() {
        return techName;
    }

    public void setTechName(List<String> techName) {
        this.techName = techName;
    }

    public List<String> getMarketingName() {
        return marketingName;
    }

    public void setMarketingName(List<String> marketingName) {
        this.marketingName = marketingName;
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

    public List<String> getDesignUrl() {
        return designUrl;
    }

    public void setDesignUrl(List<String> designUrl) {
        this.designUrl = designUrl;
    }

    public List<String> getDesignName() {
        return designName;
    }

    public void setDesignName(List<String> designName) {
        this.designName = designName;
    }

    public List<String> getPtgUrl() {
        return ptgUrl;
    }

    public void setPtgUrl(List<String> ptgUrl) {
        this.ptgUrl = ptgUrl;
    }

    public List<String> getPtgName() {
        return ptgName;
    }

    public void setPtgName(List<String> ptgName) {
        this.ptgName = ptgName;
    }
}
