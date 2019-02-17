
package com.infobite.stygian.model.banner_responce;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BannerDatum implements Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("bimage")
    @Expose
    private String bimage;
    @SerializedName("createdate")
    @Expose
    private String createdate;
    public final static Creator<BannerDatum> CREATOR = new Creator<BannerDatum>() {


        @SuppressWarnings({
            "unchecked"
        })
        public BannerDatum createFromParcel(Parcel in) {
            return new BannerDatum(in);
        }

        public BannerDatum[] newArray(int size) {
            return (new BannerDatum[size]);
        }

    }
    ;

    protected BannerDatum(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.bimage = ((String) in.readValue((String.class.getClassLoader())));
        this.createdate = ((String) in.readValue((String.class.getClassLoader())));
    }

    public BannerDatum() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BannerDatum withId(String id) {
        this.id = id;
        return this;
    }

    public String getBimage() {
        return bimage;
    }

    public void setBimage(String bimage) {
        this.bimage = bimage;
    }

    public BannerDatum withBimage(String bimage) {
        this.bimage = bimage;
        return this;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public BannerDatum withCreatedate(String createdate) {
        this.createdate = createdate;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(bimage);
        dest.writeValue(createdate);
    }

    public int describeContents() {
        return  0;
    }

}
