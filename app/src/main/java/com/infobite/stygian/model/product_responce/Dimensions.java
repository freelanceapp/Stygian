
package com.infobite.stygian.model.product_responce;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dimensions implements Parcelable
{

    @SerializedName("length")
    @Expose
    private String length;
    @SerializedName("width")
    @Expose
    private String width;
    @SerializedName("height")
    @Expose
    private String height;
    public final static Creator<Dimensions> CREATOR = new Creator<Dimensions>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Dimensions createFromParcel(Parcel in) {
            return new Dimensions(in);
        }

        public Dimensions[] newArray(int size) {
            return (new Dimensions[size]);
        }

    }
    ;

    protected Dimensions(Parcel in) {
        this.length = ((String) in.readValue((String.class.getClassLoader())));
        this.width = ((String) in.readValue((String.class.getClassLoader())));
        this.height = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Dimensions() {
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public Dimensions withLength(String length) {
        this.length = length;
        return this;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public Dimensions withWidth(String width) {
        this.width = width;
        return this;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public Dimensions withHeight(String height) {
        this.height = height;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(length);
        dest.writeValue(width);
        dest.writeValue(height);
    }

    public int describeContents() {
        return  0;
    }

}
