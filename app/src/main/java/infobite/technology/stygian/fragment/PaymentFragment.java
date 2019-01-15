package infobite.technology.stygian.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import org.json.JSONObject;

import infobite.technology.stygian.R;
import infobite.technology.stygian.activity.CheckOutActivity;
import infobite.technology.stygian.database.DatabaseHandler;
import infobite.technology.stygian.database.HelperManager;
import infobite.technology.stygian.util.SessionManager;
import infobite.technology.stygian.util.Utility;

@SuppressLint("ValidFragment")
public class PaymentFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    Context ctx;
    LinearLayout next_ll;
    TextView total_tv;
    RadioButton cob_rb, online_rb;
    Activity activity;

    SessionManager sessionManager;
    public DatabaseHandler databaseCart;
    private String DATABASE_CART = "cart.db";

    @SuppressLint("ValidFragment")
    public PaymentFragment(Context ctx, Activity activity) {
        this.ctx = ctx;
        this.activity = activity;
        sessionManager = new SessionManager(ctx);
        databaseCart = new DatabaseHandler(ctx, DATABASE_CART);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_payment, null);
        initXml(view);
        return view;
    }

    private void initXml(View view) {
        next_ll = view.findViewById(R.id.ll_payment_next);
        total_tv = view.findViewById(R.id.tv_payment_total);
        online_rb = view.findViewById(R.id.rb_payment_credit);
        cob_rb = view.findViewById(R.id.rb_payment_cod);
        next_ll.setOnClickListener(this);
        online_rb.setOnClickListener(this);
        cob_rb.setOnClickListener(this);
        total_tv.setText(Utility.getCartTotal(databaseCart));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_payment_next:
                String paytype = sessionManager.getData(SessionManager.KEY_PAYMENT_TYPE);
                if (paytype.equals("")) {
                    Utility.toastView(ctx, "Please select Payment method");
                } else {
                    ((CheckOutActivity) getActivity()).setPosition(2);
                }
                break;

            case R.id.rb_payment_cod:
                sessionManager.setData(SessionManager.KEY_PAYMENT_TYPE, "COD");
                ((CheckOutActivity) getActivity()).setPosition(2);
                break;

            case R.id.rb_payment_credit:
                sessionManager.setData(SessionManager.KEY_PAYMENT_TYPE, "online");
                ((CheckOutActivity) getActivity()).setPosition(2);
                break;
        }
    }
}
