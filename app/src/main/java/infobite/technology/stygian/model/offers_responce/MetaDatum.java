
package infobite.technology.stygian.model.offers_responce;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MetaDatum implements Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("value")
    @Expose
    private String value;
    public final static Creator<MetaDatum> CREATOR = new Creator<MetaDatum>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MetaDatum createFromParcel(Parcel in) {
            return new MetaDatum(in);
        }

        public MetaDatum[] newArray(int size) {
            return (new MetaDatum[size]);
        }

    }
    ;

    protected MetaDatum(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.key = ((String) in.readValue((String.class.getClassLoader())));
        this.value = ((String) in.readValue((String.class.getClassLoader())));
    }

    public MetaDatum() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MetaDatum withId(Integer id) {
        this.id = id;
        return this;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public MetaDatum withKey(String key) {
        this.key = key;
        return this;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public MetaDatum withValue(String value) {
        this.value = value;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(key);
        dest.writeValue(value);
    }

    public int describeContents() {
        return  0;
    }

}
