package com.infobite.stygian.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.infobite.stygian.R;
import com.infobite.stygian.constant.Constant;
import com.infobite.stygian.model.SaveAddress;
import com.infobite.stygian.util.AppPreference;
import com.infobite.stygian.util.ConnectionDetector;
import com.infobite.stygian.util.ConstantData;
import com.infobite.stygian.util.GpsTracker;
import com.infobite.stygian.util.SessionManager;
import com.infobite.stygian.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


@SuppressLint("ValidFragment")
public class ShoppingFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    Context ctx;
    Activity activity;
    LinearLayout continue_pay_ll;

    EditText name_et, mobile_et, address_et, country_et, state_et, city_et, zipcode_et;
    EditText name_et1, mobile_et1, address_et1, country_et1, state_et1, city_et1, zipcode_et1;

    String address = "";
    ConnectionDetector connectionDetector;
    SessionManager sessionManager;

    ArrayList<SaveAddress> address_list;

    double latitude; // latitude
    double longitude; // longitude

    @SuppressLint("ValidFragment")
    public ShoppingFragment(Context ctx) {
        connectionDetector = new ConnectionDetector();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping, null);
        ctx = getActivity();
        sessionManager = new SessionManager(ctx);
        activity = getActivity();
        //requestLocationPermission();
        initXml(view);
        setData();
        return view;
    }

    private void initXml(View view) {

        ctx = getActivity();
        continue_pay_ll = view.findViewById(R.id.ll_shopping_continuepay);
        address_et = view.findViewById(R.id.et_newaddress_adress);
        country_et = view.findViewById(R.id.et_newaddress_country);
        state_et = view.findViewById(R.id.et_newaddress_state);
        city_et = view.findViewById(R.id.et_newaddress_city);
        zipcode_et = view.findViewById(R.id.et_newaddress_zipcode);
        name_et = view.findViewById(R.id.et_newaddress_name);
        mobile_et = view.findViewById(R.id.et_newaddress_number);

        address_et1 = view.findViewById(R.id.et_newaddress_adress1);
        country_et1 = view.findViewById(R.id.et_newaddress_country1);
        state_et1 = view.findViewById(R.id.et_newaddress_state1);
        city_et1 = view.findViewById(R.id.et_newaddress_city1);
        zipcode_et1 = view.findViewById(R.id.et_newaddress_zipcode1);
        name_et1 = view.findViewById(R.id.et_newaddress_name1);
        mobile_et1 = view.findViewById(R.id.et_newaddress_number1);

        continue_pay_ll.setOnClickListener(this);
    }

    private void setData() {
        String name = AppPreference.getStringPreference(ctx, Constant.USERNAME);
        name_et.setText(name);

        GpsTracker gpsTracker = new GpsTracker(ctx);
        latitude = gpsTracker.getLatitude();
        longitude = gpsTracker.getLongitude();

        Geocoder geocoder = new Geocoder(ctx, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            address_et.setText(addresses.get(0).getAddressLine(0));
            city_et.setText(addresses.get(0).getLocality());
            state_et.setText(addresses.get(0).getAdminArea());
            country_et.setText(addresses.get(0).getCountryName());
            zipcode_et.setText(addresses.get(0).getPostalCode());

        } catch (IOException e) {
            e.printStackTrace();
        }
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

        String name1 = name_et1.getText().toString();
        String mobile1 = mobile_et1.getText().toString();
        String address1 = address_et1.getText().toString();
        String country1 = country_et1.getText().toString();
        String state1 = state_et1.getText().toString();
        String city1 = city_et1.getText().toString();
        String zipcode1 = zipcode_et1.getText().toString();

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


            sessionManager.setData(SessionManager.KEY_ORDER_NAME1, name1);
            sessionManager.setData(SessionManager.KEY_ORDER_MOBILE1, mobile1);
            sessionManager.setData(SessionManager.KEY_ORDER_ADDRESS1, address1);
            sessionManager.setData(SessionManager.KEY_ORDER_STATE1, state1);
            sessionManager.setData(SessionManager.KEY_ORDER_COUNTRY1, country1);
            sessionManager.setData(SessionManager.KEY_ORDER_ZIPCODE1, zipcode1);
            sessionManager.setData(SessionManager.KEY_ORDER_CITY1, city1);

            PaymentFragment fragment = new PaymentFragment(ctx);
            Utility.setFragment1(fragment, ctx, ConstantData.HOME);
            // ((CheckOutActivity) getActivity()).setPosition(1);
        }
    }
}
