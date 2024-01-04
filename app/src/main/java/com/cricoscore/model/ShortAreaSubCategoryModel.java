package com.cricoscore.model;

import java.io.Serializable;

public class ShortAreaSubCategoryModel implements Serializable {

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    private String text="";
    private int image;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    boolean isChecked=false;

    public ShortAreaSubCategoryModel(String value,int imageValue){
        this.text = value;
        this.image = imageValue;
    }


}
