package infobite.technology.stygian.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import infobite.technology.stygian.model.database_modal.CartItemDetailModal;

public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "url.db";
    // Url table name
    private static final String TABLE_URL = "url_table";
    // Url Table Columns names

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PRODUCT_ID = "product_id";
    private static final String KEY_STUFFS = "stuffs";
    private static final String KEY_STUFFS_PRICE = "stuffs_price";
    private static final String KEY_TOTAL_PRICE = "total_price";
    private static final String KEY_VENDOR_ID = "vendor_id";
    private static final String KEY_QUANTITY = "quantity";
    private static final String KEY_IMAGE = "image";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_URL_TABLE = "CREATE TABLE " + TABLE_URL + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_PRODUCT_ID + " TEXT," + KEY_STUFFS + " TEXT,"
                + KEY_STUFFS_PRICE + " TEXT," + KEY_TOTAL_PRICE + " TEXT," + KEY_VENDOR_ID + " TEXT," + KEY_QUANTITY + " TEXT,"
                + KEY_IMAGE + " TEXT" + ")";
        db.execSQL(CREATE_URL_TABLE);
        Log.e("Table", CREATE_URL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_URL);
        onCreate(db);
    }

    //Adding new Url
    public void addItemCart(CartItemDetailModal urlModal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, urlModal.getProductName());
        values.put(KEY_PRODUCT_ID, urlModal.getProductId());
        values.put(KEY_STUFFS, urlModal.getExtraStuffs());
        values.put(KEY_STUFFS_PRICE, urlModal.getExtraStuffsPrice());
        values.put(KEY_TOTAL_PRICE, urlModal.getTotalPrice());
        values.put(KEY_VENDOR_ID, urlModal.getVendorId());
        values.put(KEY_QUANTITY, urlModal.getProductQuantity());
        values.put(KEY_IMAGE, urlModal.getProductImage());

        // Inserting InstructionsRow
        db.insert(TABLE_URL, null, values);
        db.close(); // Closing database connection
    }

    // Getting single url
    public CartItemDetailModal getSingleItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_URL, new String[]{KEY_ID, KEY_NAME, KEY_PRODUCT_ID, KEY_STUFFS, KEY_STUFFS_PRICE
                        , KEY_TOTAL_PRICE, KEY_VENDOR_ID, KEY_QUANTITY, KEY_IMAGE}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        return new CartItemDetailModal(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5),
                cursor.getString(6), cursor.getString(7), cursor.getString(8));
    }

    //Getting all Url list
    public List<CartItemDetailModal> getAllUrlList() {
        List<CartItemDetailModal> urlModalList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_URL;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CartItemDetailModal urlModal = new CartItemDetailModal();
                urlModal.set_id(Integer.parseInt(cursor.getString(0)));
                urlModal.setProductName(cursor.getString(1));
                urlModal.setProductId(cursor.getString(2));
                urlModal.setExtraStuffs(cursor.getString(3));
                urlModal.setExtraStuffsPrice(cursor.getString(4));
                urlModal.setTotalPrice(cursor.getString(5));
                urlModal.setVendorId(cursor.getString(6));
                urlModal.setProductQuantity(cursor.getString(7));
                urlModal.setProductImage(cursor.getString(8));
                urlModalList.add(urlModal);
            } while (cursor.moveToNext());
        }

        return urlModalList;
    }

    // Updating single urlModal
    public int updateUrl(CartItemDetailModal urlModal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, urlModal.get_id());
        values.put(KEY_NAME, urlModal.getProductName());
        values.put(KEY_PRODUCT_ID, urlModal.getProductId());
        values.put(KEY_STUFFS, urlModal.getExtraStuffs());
        values.put(KEY_STUFFS_PRICE, urlModal.getExtraStuffsPrice());
        values.put(KEY_TOTAL_PRICE, urlModal.getTotalPrice());
        values.put(KEY_VENDOR_ID, urlModal.getVendorId());
        values.put(KEY_QUANTITY, urlModal.getProductQuantity());
        values.put(KEY_IMAGE, urlModal.getProductImage());

        int updateValue = db.update(TABLE_URL, values, KEY_ID + " = ?", new String[]{String.valueOf(urlModal.get_id())});
        db.close();
        return updateValue;
    }

    // Deleting single url
    public void deleteContact(CartItemDetailModal urlModal) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_URL, KEY_ID + " = ?",
                new String[]{String.valueOf(urlModal.get_id())});
        db.close();
    }

    // Getting url Count
    public boolean getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_URL;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int i = cursor.getCount();
        cursor.close();
        if (i > 0) {
            return true;
        } else {
            return false;
        }
    }

    //check exist data
    public boolean verification(String _username) {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = -1;
        Cursor c = null;
        try {
            String query = "SELECT COUNT(*) FROM " + TABLE_URL + " WHERE " + KEY_NAME + " = ?";
            c = db.rawQuery(query, new String[]{_username});
            if (c.moveToFirst()) {
                count = c.getInt(0);
            }
            return count > 0;
        } finally {
            if (c != null) {
                c.close();
            }
        }
    }
}