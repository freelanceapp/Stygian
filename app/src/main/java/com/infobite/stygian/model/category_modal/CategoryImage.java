package com.infobite.stygian.model.category_modal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryImage implements Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("date_created")
    @Expose
    private String dateCreated;
    @SerializedName("date_created_gmt")
    @Expose
    private String dateCreatedGmt;
    @SerializedName("date_modified")
    @Expose
    private String dateModified;
    @SerializedName("date_modified_gmt")
    @Expose
    private String dateModifiedGmt;
    @SerializedName("src")
    @Expose
    private String src;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("alt")
    @Expose
    private String alt;
    public final static Creator<CategoryImage> CREATOR = new Creator<CategoryImage>() {


        @SuppressWarnings({
                "unchecked"
        })
        public CategoryImage createFromParcel(Parcel in) {
            return new CategoryImage(in);
        }

        public CategoryImage[] newArray(int size) {
            return (new CategoryImage[size]);
        }

    }
            ;

    protected CategoryImage(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.dateCreated = ((String) in.readValue((String.class.getClassLoader())));
        this.dateCreatedGmt = ((String) in.readValue((String.class.getClassLoader())));
        this.dateModified = ((String) in.readValue((String.class.getClassLoader())));
        this.dateModifiedGmt = ((String) in.readValue((String.class.getClassLoader())));
        this.src = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.alt = ((String) in.readValue((String.class.getClassLoader())));
    }

    public CategoryImage() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateCreatedGmt() {
        return dateCreatedGmt;
    }

    public void setDateCreatedGmt(String dateCreatedGmt) {
        this.dateCreatedGmt = dateCreatedGmt;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getDateModifiedGmt() {
        return dateModifiedGmt;
    }

    public void setDateModifiedGmt(String dateModifiedGmt) {
        this.dateModifiedGmt = dateModifiedGmt;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(dateCreated);
        dest.writeValue(dateCreatedGmt);
        dest.writeValue(dateModified);
        dest.writeValue(dateModifiedGmt);
        dest.writeValue(src);
        dest.writeValue(name);
        dest.writeValue(alt);
    }

    public int describeContents() {
        return 0;
    }

}