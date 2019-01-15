package infobite.technology.stygian.model.database_modal;

import android.os.Parcel;
import android.os.Parcelable;

public class CartItemDetailModal implements Parcelable {

    private int _id = 0;
    private String extraStuffs;
    private String extraStuffsPrice;
    private String totalPrice;
    private String productName;
    private String productId;
    private String vendorId;
    private String productImage;
    private String productQuantity;

    public CartItemDetailModal() {

    }

    public CartItemDetailModal(String extraStuffs, String extraStuffsPrice, String totalPrice,
                               String productName, String productId, String vendorId, String productImage, String productQuantity) {
        this.extraStuffs = extraStuffs;
        this.extraStuffsPrice = extraStuffsPrice;
        this.totalPrice = totalPrice;
        this.productName = productName;
        this.productId = productId;
        this.vendorId = vendorId;
        this.productImage = productImage;
        this.productQuantity = productQuantity;
    }

    public CartItemDetailModal(int _id, String extraStuffs, String extraStuffsPrice, String totalPrice,
                               String productName, String productId, String vendorId, String productImage, String productQuantity) {
        this._id = _id;
        this.extraStuffs = extraStuffs;
        this.extraStuffsPrice = extraStuffsPrice;
        this.totalPrice = totalPrice;
        this.productName = productName;
        this.productId = productId;
        this.vendorId = vendorId;
        this.productImage = productImage;
        this.productQuantity = productQuantity;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    protected CartItemDetailModal(Parcel in) {
        extraStuffs = in.readString();
        extraStuffsPrice = in.readString();
        totalPrice = in.readString();
        productName = in.readString();
        productId = in.readString();
        vendorId = in.readString();
        productImage = in.readString();
        productQuantity = in.readString();
    }

    public static final Creator<CartItemDetailModal> CREATOR = new Creator<CartItemDetailModal>() {
        @Override
        public CartItemDetailModal createFromParcel(Parcel in) {
            return new CartItemDetailModal(in);
        }

        @Override
        public CartItemDetailModal[] newArray(int size) {
            return new CartItemDetailModal[size];
        }
    };

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getExtraStuffs() {
        return extraStuffs;
    }

    public void setExtraStuffs(String extraStuffs) {
        this.extraStuffs = extraStuffs;
    }

    public String getExtraStuffsPrice() {
        return extraStuffsPrice;
    }

    public void setExtraStuffsPrice(String extraStuffsPrice) {
        this.extraStuffsPrice = extraStuffsPrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(extraStuffs);
        dest.writeString(extraStuffsPrice);
        dest.writeString(totalPrice);
        dest.writeString(productName);
        dest.writeString(productId);
        dest.writeString(vendorId);
        dest.writeString(productQuantity);
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }
}
