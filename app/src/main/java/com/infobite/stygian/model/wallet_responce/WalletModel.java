
package com.infobite.stygian.model.wallet_responce;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalletModel implements Parcelable
{

    @SerializedName("transaction_id")
    @Expose
    private String transactionId;
    @SerializedName("blog_id")
    @Expose
    private String blogId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("balance")
    @Expose
    private String balance;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("details")
    @Expose
    private String details;
    @SerializedName("deleted")
    @Expose
    private String deleted;
    @SerializedName("date")
    @Expose
    private String date;
    public final static Creator<WalletModel> CREATOR = new Creator<WalletModel>() {


        @SuppressWarnings({
            "unchecked"
        })
        public WalletModel createFromParcel(Parcel in) {
            return new WalletModel(in);
        }

        public WalletModel[] newArray(int size) {
            return (new WalletModel[size]);
        }

    }
    ;

    public WalletModel(String transactionId, String blogId, String userId, String type, String amount, String balance, String currency, String details, String deleted, String date) {
        this.transactionId = transactionId;
        this.blogId = blogId;
        this.userId = userId;
        this.type = type;
        this.amount = amount;
        this.balance = balance;
        this.currency = currency;
        this.details = details;
        this.deleted = deleted;
        this.date = date;
    }

    protected WalletModel(Parcel in) {
        this.transactionId = ((String) in.readValue((String.class.getClassLoader())));
        this.blogId = ((String) in.readValue((String.class.getClassLoader())));
        this.userId = ((String) in.readValue((String.class.getClassLoader())));
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.amount = ((String) in.readValue((String.class.getClassLoader())));
        this.balance = ((String) in.readValue((String.class.getClassLoader())));
        this.currency = ((String) in.readValue((String.class.getClassLoader())));
        this.details = ((String) in.readValue((String.class.getClassLoader())));
        this.deleted = ((String) in.readValue((String.class.getClassLoader())));
        this.date = ((String) in.readValue((String.class.getClassLoader())));
    }

    public WalletModel() {
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public WalletModel withTransactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }

    public WalletModel withBlogId(String blogId) {
        this.blogId = blogId;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public WalletModel withUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public WalletModel withType(String type) {
        this.type = type;
        return this;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public WalletModel withAmount(String amount) {
        this.amount = amount;
        return this;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public WalletModel withBalance(String balance) {
        this.balance = balance;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public WalletModel withCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public WalletModel withDetails(String details) {
        this.details = details;
        return this;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public WalletModel withDeleted(String deleted) {
        this.deleted = deleted;
        return this;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public WalletModel withDate(String date) {
        this.date = date;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(transactionId);
        dest.writeValue(blogId);
        dest.writeValue(userId);
        dest.writeValue(type);
        dest.writeValue(amount);
        dest.writeValue(balance);
        dest.writeValue(currency);
        dest.writeValue(details);
        dest.writeValue(deleted);
        dest.writeValue(date);
    }

    public int describeContents() {
        return  0;
    }

}
