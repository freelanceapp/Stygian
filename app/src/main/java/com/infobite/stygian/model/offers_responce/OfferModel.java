
package com.infobite.stygian.model.offers_responce;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfferModel implements Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("amount")
    @Expose
    private String amount;
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
    @SerializedName("discount_type")
    @Expose
    private String discountType;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("date_expires")
    @Expose
    private String dateExpires;
    @SerializedName("date_expires_gmt")
    @Expose
    private String dateExpiresGmt;
    @SerializedName("usage_count")
    @Expose
    private Integer usageCount;
    @SerializedName("individual_use")
    @Expose
    private Boolean individualUse;
    @SerializedName("product_ids")
    @Expose
    private List<Object> productIds = new ArrayList<Object>();
    @SerializedName("excluded_product_ids")
    @Expose
    private List<Object> excludedProductIds = new ArrayList<Object>();
    @SerializedName("usage_limit")
    @Expose
    private Integer usageLimit;
    @SerializedName("usage_limit_per_user")
    @Expose
    private Integer usageLimitPerUser;
    @SerializedName("limit_usage_to_x_items")
    @Expose
    private Object limitUsageToXItems;
    @SerializedName("free_shipping")
    @Expose
    private Boolean freeShipping;
    @SerializedName("product_categories")
    @Expose
    private List<Integer> productCategories = new ArrayList<Integer>();
    @SerializedName("excluded_product_categories")
    @Expose
    private List<Integer> excludedProductCategories = new ArrayList<Integer>();
    @SerializedName("exclude_sale_items")
    @Expose
    private Boolean excludeSaleItems;
    @SerializedName("minimum_amount")
    @Expose
    private String minimumAmount;
    @SerializedName("maximum_amount")
    @Expose
    private String maximumAmount;
    @SerializedName("email_restrictions")
    @Expose
    private List<Object> emailRestrictions = new ArrayList<Object>();
    @SerializedName("used_by")
    @Expose
    private List<Object> usedBy = new ArrayList<Object>();
    @SerializedName("meta_data")
    @Expose
    private List<MetaDatum> metaData = new ArrayList<MetaDatum>();
    @SerializedName("_links")
    @Expose
    private Links links;
    public final static Creator<OfferModel> CREATOR = new Creator<OfferModel>() {


        @SuppressWarnings({
            "unchecked"
        })
        public OfferModel createFromParcel(Parcel in) {
            return new OfferModel(in);
        }

        public OfferModel[] newArray(int size) {
            return (new OfferModel[size]);
        }

    }
    ;

    protected OfferModel(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.code = ((String) in.readValue((String.class.getClassLoader())));
        this.amount = ((String) in.readValue((String.class.getClassLoader())));
        this.dateCreated = ((String) in.readValue((String.class.getClassLoader())));
        this.dateCreatedGmt = ((String) in.readValue((String.class.getClassLoader())));
        this.dateModified = ((String) in.readValue((String.class.getClassLoader())));
        this.dateModifiedGmt = ((String) in.readValue((String.class.getClassLoader())));
        this.discountType = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.dateExpires = ((String) in.readValue((String.class.getClassLoader())));
        this.dateExpiresGmt = ((String) in.readValue((String.class.getClassLoader())));
        this.usageCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.individualUse = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        in.readList(this.productIds, (Object.class.getClassLoader()));
        in.readList(this.excludedProductIds, (Object.class.getClassLoader()));
        this.usageLimit = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.usageLimitPerUser = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.limitUsageToXItems = ((Object) in.readValue((Object.class.getClassLoader())));
        this.freeShipping = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        in.readList(this.productCategories, (Integer.class.getClassLoader()));
        in.readList(this.excludedProductCategories, (Integer.class.getClassLoader()));
        this.excludeSaleItems = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.minimumAmount = ((String) in.readValue((String.class.getClassLoader())));
        this.maximumAmount = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.emailRestrictions, (Object.class.getClassLoader()));
        in.readList(this.usedBy, (Object.class.getClassLoader()));
        in.readList(this.metaData, (com.infobite.stygian.model.offers_responce.MetaDatum.class.getClassLoader()));
        this.links = ((Links) in.readValue((Links.class.getClassLoader())));
    }

    public OfferModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OfferModel withId(Integer id) {
        this.id = id;
        return this;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public OfferModel withCode(String code) {
        this.code = code;
        return this;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public OfferModel withAmount(String amount) {
        this.amount = amount;
        return this;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public OfferModel withDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public String getDateCreatedGmt() {
        return dateCreatedGmt;
    }

    public void setDateCreatedGmt(String dateCreatedGmt) {
        this.dateCreatedGmt = dateCreatedGmt;
    }

    public OfferModel withDateCreatedGmt(String dateCreatedGmt) {
        this.dateCreatedGmt = dateCreatedGmt;
        return this;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public OfferModel withDateModified(String dateModified) {
        this.dateModified = dateModified;
        return this;
    }

    public String getDateModifiedGmt() {
        return dateModifiedGmt;
    }

    public void setDateModifiedGmt(String dateModifiedGmt) {
        this.dateModifiedGmt = dateModifiedGmt;
    }

    public OfferModel withDateModifiedGmt(String dateModifiedGmt) {
        this.dateModifiedGmt = dateModifiedGmt;
        return this;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public OfferModel withDiscountType(String discountType) {
        this.discountType = discountType;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OfferModel withDescription(String description) {
        this.description = description;
        return this;
    }

    public String getDateExpires() {
        return dateExpires;
    }

    public void setDateExpires(String dateExpires) {
        this.dateExpires = dateExpires;
    }

    public OfferModel withDateExpires(String dateExpires) {
        this.dateExpires = dateExpires;
        return this;
    }

    public String getDateExpiresGmt() {
        return dateExpiresGmt;
    }

    public void setDateExpiresGmt(String dateExpiresGmt) {
        this.dateExpiresGmt = dateExpiresGmt;
    }

    public OfferModel withDateExpiresGmt(String dateExpiresGmt) {
        this.dateExpiresGmt = dateExpiresGmt;
        return this;
    }

    public Integer getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(Integer usageCount) {
        this.usageCount = usageCount;
    }

    public OfferModel withUsageCount(Integer usageCount) {
        this.usageCount = usageCount;
        return this;
    }

    public Boolean getIndividualUse() {
        return individualUse;
    }

    public void setIndividualUse(Boolean individualUse) {
        this.individualUse = individualUse;
    }

    public OfferModel withIndividualUse(Boolean individualUse) {
        this.individualUse = individualUse;
        return this;
    }

    public List<Object> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Object> productIds) {
        this.productIds = productIds;
    }

    public OfferModel withProductIds(List<Object> productIds) {
        this.productIds = productIds;
        return this;
    }

    public List<Object> getExcludedProductIds() {
        return excludedProductIds;
    }

    public void setExcludedProductIds(List<Object> excludedProductIds) {
        this.excludedProductIds = excludedProductIds;
    }

    public OfferModel withExcludedProductIds(List<Object> excludedProductIds) {
        this.excludedProductIds = excludedProductIds;
        return this;
    }

    public Integer getUsageLimit() {
        return usageLimit;
    }

    public void setUsageLimit(Integer usageLimit) {
        this.usageLimit = usageLimit;
    }

    public OfferModel withUsageLimit(Integer usageLimit) {
        this.usageLimit = usageLimit;
        return this;
    }

    public Integer getUsageLimitPerUser() {
        return usageLimitPerUser;
    }

    public void setUsageLimitPerUser(Integer usageLimitPerUser) {
        this.usageLimitPerUser = usageLimitPerUser;
    }

    public OfferModel withUsageLimitPerUser(Integer usageLimitPerUser) {
        this.usageLimitPerUser = usageLimitPerUser;
        return this;
    }

    public Object getLimitUsageToXItems() {
        return limitUsageToXItems;
    }

    public void setLimitUsageToXItems(Object limitUsageToXItems) {
        this.limitUsageToXItems = limitUsageToXItems;
    }

    public OfferModel withLimitUsageToXItems(Object limitUsageToXItems) {
        this.limitUsageToXItems = limitUsageToXItems;
        return this;
    }

    public Boolean getFreeShipping() {
        return freeShipping;
    }

    public void setFreeShipping(Boolean freeShipping) {
        this.freeShipping = freeShipping;
    }

    public OfferModel withFreeShipping(Boolean freeShipping) {
        this.freeShipping = freeShipping;
        return this;
    }

    public List<Integer> getProductCategories() {
        return productCategories;
    }

    public void setProductCategories(List<Integer> productCategories) {
        this.productCategories = productCategories;
    }

    public OfferModel withProductCategories(List<Integer> productCategories) {
        this.productCategories = productCategories;
        return this;
    }

    public List<Integer> getExcludedProductCategories() {
        return excludedProductCategories;
    }

    public void setExcludedProductCategories(List<Integer> excludedProductCategories) {
        this.excludedProductCategories = excludedProductCategories;
    }

    public OfferModel withExcludedProductCategories(List<Integer> excludedProductCategories) {
        this.excludedProductCategories = excludedProductCategories;
        return this;
    }

    public Boolean getExcludeSaleItems() {
        return excludeSaleItems;
    }

    public void setExcludeSaleItems(Boolean excludeSaleItems) {
        this.excludeSaleItems = excludeSaleItems;
    }

    public OfferModel withExcludeSaleItems(Boolean excludeSaleItems) {
        this.excludeSaleItems = excludeSaleItems;
        return this;
    }

    public String getMinimumAmount() {
        return minimumAmount;
    }

    public void setMinimumAmount(String minimumAmount) {
        this.minimumAmount = minimumAmount;
    }

    public OfferModel withMinimumAmount(String minimumAmount) {
        this.minimumAmount = minimumAmount;
        return this;
    }

    public String getMaximumAmount() {
        return maximumAmount;
    }

    public void setMaximumAmount(String maximumAmount) {
        this.maximumAmount = maximumAmount;
    }

    public OfferModel withMaximumAmount(String maximumAmount) {
        this.maximumAmount = maximumAmount;
        return this;
    }

    public List<Object> getEmailRestrictions() {
        return emailRestrictions;
    }

    public void setEmailRestrictions(List<Object> emailRestrictions) {
        this.emailRestrictions = emailRestrictions;
    }

    public OfferModel withEmailRestrictions(List<Object> emailRestrictions) {
        this.emailRestrictions = emailRestrictions;
        return this;
    }

    public List<Object> getUsedBy() {
        return usedBy;
    }

    public void setUsedBy(List<Object> usedBy) {
        this.usedBy = usedBy;
    }

    public OfferModel withUsedBy(List<Object> usedBy) {
        this.usedBy = usedBy;
        return this;
    }

    public List<MetaDatum> getMetaData() {
        return metaData;
    }

    public void setMetaData(List<MetaDatum> metaData) {
        this.metaData = metaData;
    }

    public OfferModel withMetaData(List<MetaDatum> metaData) {
        this.metaData = metaData;
        return this;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public OfferModel withLinks(Links links) {
        this.links = links;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(code);
        dest.writeValue(amount);
        dest.writeValue(dateCreated);
        dest.writeValue(dateCreatedGmt);
        dest.writeValue(dateModified);
        dest.writeValue(dateModifiedGmt);
        dest.writeValue(discountType);
        dest.writeValue(description);
        dest.writeValue(dateExpires);
        dest.writeValue(dateExpiresGmt);
        dest.writeValue(usageCount);
        dest.writeValue(individualUse);
        dest.writeList(productIds);
        dest.writeList(excludedProductIds);
        dest.writeValue(usageLimit);
        dest.writeValue(usageLimitPerUser);
        dest.writeValue(limitUsageToXItems);
        dest.writeValue(freeShipping);
        dest.writeList(productCategories);
        dest.writeList(excludedProductCategories);
        dest.writeValue(excludeSaleItems);
        dest.writeValue(minimumAmount);
        dest.writeValue(maximumAmount);
        dest.writeList(emailRestrictions);
        dest.writeList(usedBy);
        dest.writeList(metaData);
        dest.writeValue(links);
    }

    public int describeContents() {
        return  0;
    }

}
