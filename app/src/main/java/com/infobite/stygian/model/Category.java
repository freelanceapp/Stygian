package com.infobite.stygian.model;

import android.graphics.drawable.Drawable;

public class Category {
    String cid;
    String cname;
    Drawable color;

    public Drawable getColor() {
        return color;
    }

    public void setColor(Drawable color) {
        this.color = color;
    }

    public Category() {
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
}
