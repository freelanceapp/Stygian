package infobite.technology.stygian.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import infobite.technology.stygian.model.ProductDetail;
import infobite.technology.stygian.model.SubCategory;

public class JsonParser {

    public static ArrayList<ProductDetail> getProducts(JSONArray response) {
        ArrayList<ProductDetail> list = new ArrayList<>();
        if (response.length() > 0) {
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject object = response.getJSONObject(i);
                    String id = object.getString("id");
                    String name = object.getString("name");
                    float price = object.getLong("price");
                    int roundprice = Math.round(price);
                    String reg_price = object.getString("regular_price");
                    String sale_price = object.getString("sale_price");
                    String html_price = object.getString("price_html");
                    String description = object.getString("description");
                    JSONArray image_array = object.getJSONArray("images");
                    String image = "";
                    if (image_array.length() > 0) {
                        JSONObject objectimg = image_array.getJSONObject(0);
                        image = objectimg.getString("src");
                    }
                    JSONArray attri_array = object.getJSONArray("attributes");
                    list.add(new ProductDetail(id, name,description, String.valueOf(roundprice), reg_price,
                            sale_price, html_price, image, image_array.toString(), attri_array.toString(), 1));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    public static ArrayList<SubCategory> getSubCategory(JSONArray response) {
        ArrayList<SubCategory> list = new ArrayList<>();
        if (response.length() > 0) {
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject object = response.getJSONObject(i);
                    String id = object.getString("id");
                    String name = object.getString("name");
                    String parent = object.getString("parent");
                    list.add(new SubCategory(id, parent, name));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }
}
