package infobite.technology.stygian.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import infobite.technology.stygian.R;
import infobite.technology.stygian.adapter.SaveAddressAdapter;
import infobite.technology.stygian.model.SaveAddress;
import infobite.technology.stygian.util.ConnectionDetector;
import infobite.technology.stygian.util.SessionManager;
import infobite.technology.stygian.util.Utility;
import infobite.technology.stygian.util.WebApi;


@SuppressLint("ValidFragment")
public class MyAddressFragment extends Fragment {

    Context ctx;
    RecyclerView recyclerView;
    ConnectionDetector connectionDetector;
    SessionManager sessionManager;

    @SuppressLint("ValidFragment")
    public MyAddressFragment(Context ctx) {
        this.ctx = ctx;
        sessionManager = new SessionManager(ctx);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_recyclerview, container, false);
        recyclerView = view.findViewById(R.id.layout_recyclerview);
        boolean internet = connectionDetector.isConnected();
        if (internet) {
            getData();
        } else {
            Utility.toastView(ctx, ctx.getString(R.string.no_internet));
        }
        return view;
    }

    private void getData() {
        String id = sessionManager.getData(SessionManager.KEY_ID);
        Utility.showLoader(ctx);
        AndroidNetworking.get(WebApi.API_EDIT_PROFILE + id)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Utility.hideLoader();
                        setResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Utility.hideLoader();
                        Utility.toastView(ctx, anError.toString());
                    }
                });
    }

    private void setResponse(JSONObject response) {

        try {
            JSONObject billing_obj = response.getJSONObject("billing");
            String address = billing_obj.getString("address_1");
            String city = billing_obj.getString("city");
            String state = billing_obj.getString("state");
            String county = billing_obj.getString("country");
            String code = billing_obj.getString("postcode");

            setAdapter(address, city, state, county, code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setAdapter(String address, String city, String state, String county, String code) {
        ArrayList<SaveAddress> list = new ArrayList<>();
        list.add(new SaveAddress("01", "51", address, county, state, city, code, "12/07/2018"));
        SaveAddressAdapter adapter = new SaveAddressAdapter(ctx, list);
        LinearLayoutManager manager = new LinearLayoutManager(ctx);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }


}
