package infobite.technology.stygian.util;

public class WebApi {
    // BASE URL
    public static final String BASE_URL = "https://stygianstore.com/";

    public static final String API_LOGIN = BASE_URL + "api/auth/generate_auth_cookie/";
    public static final String API_REGISTER = BASE_URL + "wp-json/wc/v3/customers/";

    // for woman id - 2179
    // for kids id -

    //men listing
    public static final String API_MENS = "https://stygianstore.com/wp-json/wc/v3/products?category=1832&page=";
    public static final String API_WOMENS = "https://stygianstore.com/wp-json/wc/v3/products?category=2179&page=";
    public static final String API_KIDS = "https://stygianstore.com/wp-json/wc/v3/products?category=11520&page=";
    public static final String API_MOBILE = "https://stygianstore.com/wp-json/wc/v3/products?category=6719&page=";

    public static final String API_MEN_SUB_CATE = "https://stygianstore.com/wp-json/wc/v3/products/categories?parent=1832";

    // for sub category - GET
    public static final String API_SUB_CATEGORY = "https://stygianstore.com/wp-json/wc/v3/products/categories?parent=";
    // for products - GET
    public static final String API_PRODUCTS = "https://stygianstore.com/wp-json/wc/v3/products?category=";
    //    for user details - GET
    public static final String API_USER_PROFILE = "https://stygianstore.com/wp-json/wc/v3/customers/";
    // for edit profile
    public static final String API_EDIT_PROFILE = "https://stygianstore.com//wp-json/wc/v3/customers/";
    // for customer order

    public static final String API_CUSTOMER_ORDER = "https://stygianstore.com//wp-json/wc/v3/orders?customer=";
    // for submit order
    public static final String API_SUBMIT_ORDER = "wp-json/wc/v3/orders";


    // IDS
    public static final String ID_SUB_MEN = "1832";
    public static final String ID_SUB_WOMEN = "2179";
    public static final String ID_PRODUCTS_KIDS = "11520";
    public static final String ID_PRODUCTS_HOME = "1795";
}
