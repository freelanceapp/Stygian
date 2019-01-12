
package infobite.technology.stygian.model.product_responce;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Attribute implements Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("position")
    @Expose
    private Integer position;
    @SerializedName("visible")
    @Expose
    private Boolean visible;
    @SerializedName("variation")
    @Expose
    private Boolean variation;
    @SerializedName("options")
    @Expose
    private List<String> options = new ArrayList<String>();
    public final static Creator<Attribute> CREATOR = new Creator<Attribute>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Attribute createFromParcel(Parcel in) {
            return new Attribute(in);
        }

        public Attribute[] newArray(int size) {
            return (new Attribute[size]);
        }

    }
    ;

    protected Attribute(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.position = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.visible = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.variation = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        in.readList(this.options, (String.class.getClassLoader()));
    }

    public Attribute() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Attribute withId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Attribute withName(String name) {
        this.name = name;
        return this;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Attribute withPosition(Integer position) {
        this.position = position;
        return this;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Attribute withVisible(Boolean visible) {
        this.visible = visible;
        return this;
    }

    public Boolean getVariation() {
        return variation;
    }

    public void setVariation(Boolean variation) {
        this.variation = variation;
    }

    public Attribute withVariation(Boolean variation) {
        this.variation = variation;
        return this;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public Attribute withOptions(List<String> options) {
        this.options = options;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(position);
        dest.writeValue(visible);
        dest.writeValue(variation);
        dest.writeList(options);
    }

    public int describeContents() {
        return  0;
    }

}
