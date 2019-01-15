package infobite.technology.stygian.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import infobite.technology.stygian.R;
import infobite.technology.stygian.activity.CheckOutActivity;
import infobite.technology.stygian.model.SaveAddress;
import infobite.technology.stygian.util.ConnectionDetector;
import infobite.technology.stygian.util.GPSTracker;
import infobite.technology.stygian.util.SessionManager;
import infobite.technology.stygian.util.Utility;


@SuppressLint("ValidFragment")
public class ShoppingFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    Context ctx;
    LinearLayout continue_pay_ll;

    EditText name_et, mobile_et, address_et, country_et, state_et, city_et, zipcode_et;

    String address = "";
    ConnectionDetector connectionDetector;
    SessionManager sessionManager;

    ArrayList<SaveAddress> address_list;

    @SuppressLint("ValidFragment")
    public ShoppingFragment(Context ctx) {
        this.ctx = ctx;
        connectionDetector = new ConnectionDetector();
        sessionManager = new SessionManager(ctx);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping, null);
        initXml(view);
        setData();
        return view;
    }

    private void setData() {
        GPSTracker gpsTracker = new GPSTracker(ctx);

        address_et.setText(gpsTracker.getAddressLine(ctx));
        city_et.setText(gpsTracker.getCityName(ctx));
        state_et.setText(gpsTracker.getStateName(ctx));
        country_et.setText(gpsTracker.getCountryName(ctx));
        zipcode_et.setText(gpsTracker.getPostalCode(ctx));
    }

    private void initXml(View view) {

        continue_pay_ll = view.findViewById(R.id.ll_shopping_continuepay);
        address_et = view.findViewById(R.id.et_newaddress_adress);
        country_et = view.findViewById(R.id.et_newaddress_country);
        state_et = view.findViewById(R.id.et_newaddress_state);
        city_et = view.findViewById(R.id.et_newaddress_city);
        zipcode_et = view.findViewById(R.id.et_newaddress_zipcode);
        name_et = view.findViewById(R.id.et_newaddress_name);
        mobile_et = view.findViewById(R.id.et_newaddress_number);
        continue_pay_ll.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_shopping_continuepay:
                getData();
                break;
        }
    }

    private void getData() {
        String name = name_et.getText().toString();
        String mobile = mobile_et.getText().toString();
        String address = address_et.getText().toString();
        String country = country_et.getText().toString();
        String state = state_et.getText().toString();
        String city = city_et.getText().toString();
        String zipcode = zipcode_et.getText().toString();

        if (name.equals("") || mobile.equals("") || address.equals("") || country.equals("") || state.equals("") ||
                city.equals("") || zipcode.equals("")) {

            Utility.toastView(ctx, "Enter all details");
        } else {
            sessionManager.setData(SessionManager.KEY_ORDER_NAME, name);
            sessionManager.setData(SessionManager.KEY_ORDER_MOBILE, mobile);
            sessionManager.setData(SessionManager.KEY_ORDER_ADDRESS, address);
            sessionManager.setData(SessionManager.KEY_ORDER_STATE, state);
            sessionManager.setData(SessionManager.KEY_ORDER_COUNTRY, country);
            sessionManager.setData(SessionManager.KEY_ORDER_ZIPCODE, zipcode);
            sessionManager.setData(SessionManager.KEY_ORDER_CITY, city);

            ((CheckOutActivity) getActivity()).setPosition(1);
        }
    }
}
