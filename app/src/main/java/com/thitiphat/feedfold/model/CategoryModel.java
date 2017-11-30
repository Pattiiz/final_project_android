package com.thitiphat.feedfold.model;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phatm on 11/24/2017.
 */

public class CategoryModel {
    List<String> category = new ArrayList<>();
    List<String> imgSrc = new ArrayList<>();

    public CategoryModel() {
        this.category.add("Technology");
        this.category.add("Marketing");
        this.category.add("Design");
        this.category.add("Photograph");
    }

    public CategoryModel(List<String> category, List<String> imgSrc) {
        this.category = category;
        this.imgSrc = imgSrc;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public List<String> getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(List<String> imgSrc) {
        this.imgSrc = imgSrc;
    }
}
