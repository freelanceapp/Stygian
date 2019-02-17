package com.infobite.stygian.model.category_modal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductCategoryList implements Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("parent")
    @Expose
    private Integer parent;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("display")
    @Expose
    private String display;
    @SerializedName("image")
    @Expose
    private CategoryImage categoryImage;
    public final static Creator<ProductCategoryList> CREATOR = new Creator<ProductCategoryList>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ProductCategoryList createFromParcel(Parcel in) {
            return new ProductCategoryList(in);
        }

        public ProductCategoryList[] newArray(int size) {
            return (new ProductCategoryList[size]);
        }

    }
            ;

    protected ProductCategoryList(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.slug = ((String) in.readValue((String.class.getClassLoader())));
        this.parent = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.display = ((String) in.readValue((String.class.getClassLoader())));
        this.categoryImage = ((CategoryImage) in.readValue((CategoryImage.class.getClassLoader())));
    }

    public ProductCategoryList() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public CategoryImage getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(CategoryImage categoryImage) {
        this.categoryImage = categoryImage;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(slug);
        dest.writeValue(parent);
        dest.writeValue(description);
        dest.writeValue(display);
        dest.writeValue(categoryImage);
    }

    public int describeContents() {
        return 0;
    }

}
