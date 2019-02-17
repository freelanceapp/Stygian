package com.infobite.stygian.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.infobite.stygian.activity.MainActivity;
import com.infobite.stygian.R;


public class MyPaymentFragment extends Fragment {

    Button payment_btn;
    public MyPaymentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mypayment, container, false);
        payment_btn = (Button)view.findViewById(R.id.payment_btn);
        MainActivity.tooltext_tv.setText("Checkout");

        payment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        return view;
    }

}
