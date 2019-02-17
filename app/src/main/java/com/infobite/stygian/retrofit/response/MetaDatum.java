package com.infobite.stygian.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MetaDatum
{
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("value")
    @Expose
    private List<Object> value = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<Object> getValue() {
        return value;
    }

    public void setValue(List<Object> value) {
        this.value = value;
    }
}
