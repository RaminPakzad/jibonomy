package sadad.com.jibonomy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.math.BigDecimal;
import java.util.List;

import sadad.com.jibonomy.biz.adapter.WishListAdapter;
import sadad.com.jibonomy.entities.Wish;
import sadad.com.jibonomy.services.WishService;
import sadad.com.jibonomy.utils.NavigationUtil;

/**
 * @author ramin pakzad (RPakzadmanesh@gmail.com) on 1/13/2019.
 */
public class WishListFragment extends Fragment {
    View rootView;

    private RecyclerView recyclerView;
    private WishListAdapter vlAdapter;
    private WishService wishService;
    private FloatingActionButton floatingActionButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.wish_list_fragment, container, false);
        wishService = new WishService(rootView.getContext());
        recyclerView = rootView.findViewById(R.id.recycler_view);
        floatingActionButton = rootView.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationUtil.changeFragment(new WishFragment(),rootView);
            }
        });
//        wishService.deleteAll();
//        wishService.insert(new Wish("wish desc", "wishName", "pic1", BigDecimal.TEN));
//        wishService.insert(new Wish("wish desc", "wishName", "pic1", BigDecimal.TEN));
//        wishService.insert(new Wish("wish desc", "wishName", "pic1", BigDecimal.TEN));
//        wishService.insert(new Wish("wish desc", "wishName", "pic1", BigDecimal.TEN));
//        wishService.insert(new Wish("wish desc", "wishName", "pic1", BigDecimal.TEN));
        List<Wish> wishes = wishService.getWishes();

        vlAdapter = new WishListAdapter(wishes, BigDecimal.valueOf(10000000));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(rootView.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(vlAdapter);

        vlAdapter.notifyDataSetChanged();
        return rootView;
    }

}
