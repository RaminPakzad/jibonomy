package sadad.com.jibonomy.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import sadad.com.jibonomy.MapsActivity;
import sadad.com.jibonomy.R;
import sadad.com.jibonomy.biz.adapter.MerchantListAdapter;
import sadad.com.jibonomy.entities.Merchant;
import sadad.com.jibonomy.services.MerchantService;

public class MerchantListFragment extends Fragment {
    View rootView;

    private RecyclerView recyclerView;
    private MerchantListAdapter vlAdapter;
    private MerchantService merchantService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.merchant_list_fragment, container, false);
        recyclerView = rootView.findViewById(R.id.merchant_list_view);
        merchantService = new MerchantService(rootView.getContext());

        List<Merchant> merchants = merchantService.getMerchants();

        vlAdapter = new MerchantListAdapter(merchants);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(rootView.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(vlAdapter);

        vlAdapter.notifyDataSetChanged();


        Button floatingActionButton = rootView.findViewById(R.id.location);
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
