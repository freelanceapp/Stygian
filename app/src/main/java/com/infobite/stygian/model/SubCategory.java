package com.infobite.stygian.model;

public class SubCategory
{
    private String id;
    private String parentid;
    private String name;

    public SubCategory(String id, String parentid, String name) {
        this.id = id;
        this.parentid = parentid;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
