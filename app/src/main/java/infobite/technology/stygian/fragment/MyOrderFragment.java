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
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import infobite.technology.stygian.R;
import infobite.technology.stygian.adapter.AdapterCart;
import infobite.technology.stygian.adapter.OrderItermAdapter;
import infobite.technology.stygian.adapter.SaveAddressAdapter;
import infobite.technology.stygian.model.AddtoCard;
import infobite.technology.stygian.model.Orders;
import infobite.technology.stygian.model.SaveAddress;
import infobite.technology.stygian.util.ConnectionDetector;
import infobite.technology.stygian.util.SessionManager;
import infobite.technology.stygian.util.Utility;
import infobite.technology.stygian.util.WebApi;


@SuppressLint("ValidFragment")
public class MyOrderFragment extends Fragment {

    Context ctx;
    RecyclerView recyclerView;
    TextView nodata_tv;

    ConnectionDetector connectionDetector;
    SessionManager sessionManager;

    @SuppressLint("ValidFragment")
    public MyOrderFragment(Context ctx) {
        this.ctx = ctx;
        sessionManager = new SessionManager(ctx);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_recyclerview, container, false);
        recyclerView = view.findViewById(R.id.layout_recyclerview);
        nodata_tv = view.findViewById(R.id.tv_layout_nodata);

        boolean internet = connectionDetector.isConnected();
        if (internet) {
            String id = sessionManager.getData(SessionManager.KEY_ID);
            getdata(id);
        } else {
            Utility.toastView(ctx, ctx.getString(R.string.no_internet));
        }
        return view;
    }

    private void getdata(String id) {
        Utility.showLoader(ctx);
        AndroidNetworking.get(WebApi.API_CUSTOMER_ORDER + id)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
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

    private void setResponse(JSONArray response) {
        ArrayList<Orders> list = new ArrayList<>();
        if (response.length() > 0) {
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject object = response.getJSONObject(i);
                    String id = object.getString("id");
                    String status = object.getString("status");
                    String ship_total = object.getString("total");
                    JSONArray lines_array = object.getJSONArray("line_items");
                    list.add(new Orders(id, ship_total, status, lines_array.toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (list.size() > 0) {
                nodata_tv.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                setAdapter(list);
            } else {
                recyclerView.setVisibility(View.GONE);
                nodata_tv.setVisibility(View.VISIBLE);
                nodata_tv.setText("No New Order");
            }
        }
    }

    private void setAdapter(ArrayList<Orders> list) {

        OrderItermAdapter adapter = new OrderItermAdapter(ctx, list);
        LinearLayoutManager manager = new LinearLayoutManager(ctx);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }


}
