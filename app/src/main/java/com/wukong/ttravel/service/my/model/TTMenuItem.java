package com.wukong.ttravel.service.my.model;

/**
 * Created by wukong on 3/11/16.
 */
public class TTMenuItem {

    private int iconResId;
    private String title;
    private int index;

    public TTMenuItem(int index, String title, int iconResId) {
        this.index = index;
        this.title = title;
        this.iconResId = iconResId;
    }

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
