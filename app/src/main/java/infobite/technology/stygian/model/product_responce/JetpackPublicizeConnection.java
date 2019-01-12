
package infobite.technology.stygian.model.product_responce;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JetpackPublicizeConnection implements Parcelable
{

    @SerializedName("service_name")
    @Expose
    private String serviceName;
    @SerializedName("display_name")
    @Expose
    private String displayName;
    @SerializedName("id")
    @Expose
    private String id;
    public final static Creator<JetpackPublicizeConnection> CREATOR = new Creator<JetpackPublicizeConnection>() {


        @SuppressWarnings({
            "unchecked"
        })
        public JetpackPublicizeConnection createFromParcel(Parcel in) {
            return new JetpackPublicizeConnection(in);
        }

        public JetpackPublicizeConnection[] newArray(int size) {
            return (new JetpackPublicizeConnection[size]);
        }

    }
    ;

    protected JetpackPublicizeConnection(Parcel in) {
        this.serviceName = ((String) in.readValue((String.class.getClassLoader())));
        this.displayName = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((String) in.readValue((String.class.getClassLoader())));
    }

    public JetpackPublicizeConnection() {
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public JetpackPublicizeConnection withServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public JetpackPublicizeConnection withDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public JetpackPublicizeConnection withId(String id) {
        this.id = id;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(serviceName);
        dest.writeValue(displayName);
        dest.writeValue(id);
    }

    public int describeContents() {
        return  0;
    }

}
