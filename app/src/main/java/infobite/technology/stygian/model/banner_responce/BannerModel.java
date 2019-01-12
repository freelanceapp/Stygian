
package infobite.technology.stygian.model.banner_responce;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BannerModel implements Parcelable
{

    @SerializedName("data")
    @Expose
    private List<BannerDatum> data = new ArrayList<BannerDatum>();
    public final static Creator<BannerModel> CREATOR = new Creator<BannerModel>() {


        @SuppressWarnings({
            "unchecked"
        })
        public BannerModel createFromParcel(Parcel in) {
            return new BannerModel(in);
        }

        public BannerModel[] newArray(int size) {
            return (new BannerModel[size]);
        }

    }
    ;

    protected BannerModel(Parcel in) {
        in.readList(this.data, (BannerDatum.class.getClassLoader()));
    }

    public BannerModel() {
    }

    public List<BannerDatum> getData() {
        return data;
    }

    public void setData(List<BannerDatum> data) {
        this.data = data;
    }

    public BannerModel withData(List<BannerDatum> data) {
        this.data = data;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(data);
    }

    public int describeContents() {
        return  0;
    }

}
