package sadad.com.jibonomy.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import sadad.com.jibonomy.R;
import sadad.com.jibonomy.entities.Merchant;

public class MerchantDetailFragment extends android.support.v4.app.Fragment {

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.merchant_detail_fragment, container, false);
        //TextView name = (TextView) rootView.findViewById(R.id.merchant_name);

        //name.setText(getArguments().getString(Merchant.MERCHANT_NAME_LABEL));


        return rootView;
    }
}
