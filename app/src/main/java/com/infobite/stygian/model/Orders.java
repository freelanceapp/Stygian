package com.infobite.stygian.model;

public class Orders {

    private String id;
    private String total;
    private String status;
    private String item_array;

    public Orders(String id, String total, String status, String item_array) {
        this.id = id;
        this.total = total;
        this.status = status;
        this.item_array = item_array;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getItem_array() {
        return item_array;
    }

    public void setItem_array(String item_array) {
        this.item_array = item_array;
    }
}
