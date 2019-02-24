package sadad.com.jibonomy.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import sadad.com.jibonomy.MapsActivity;
import sadad.com.jibonomy.R;

public class MerchantDetailFragment extends android.support.v4.app.Fragment {

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.merchant_detail_fragment, container, false);
        //TextView name = (TextView) rootView.findViewById(R.id.merchant_name);

        //name.setText(getArguments().getString(Merchant.MERCHANT_NAME_LABEL));
        FloatingActionButton floatingActionButton = rootView.findViewById(R.id.location);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(rootView.getContext(), MapsActivity.class);
                startActivity(intent);
            }
        });


        return rootView;
    }
}
