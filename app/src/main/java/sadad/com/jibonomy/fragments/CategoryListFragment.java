package sadad.com.jibonomy.fragments;

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

import java.util.ArrayList;
import java.util.List;

import sadad.com.jibonomy.R;
import sadad.com.jibonomy.biz.adapter.CategoryListAdapter;
import sadad.com.jibonomy.entities.Category;
import sadad.com.jibonomy.services.CategoryService;
import sadad.com.jibonomy.utils.NavigationUtil;
import sadad.com.jibonomy.utils.StringUtil;

/**
 * @author ramin pakzad (RPakzadmanesh@gmail.com) on 1/17/2019.
 */
public class CategoryListFragment extends Fragment {
    View rootView;

    private RecyclerView recyclerView;
    private CategoryListAdapter vlAdapter;
    private CategoryService categoryService;
    private FloatingActionButton floatingActionButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.category_list_fragment, container, false);
        categoryService = new CategoryService(rootView.getContext());

        recyclerView = rootView.findViewById(R.id.recycler_view);
        floatingActionButton = rootView.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationUtil.changeFragment(new CategoryFragment(), rootView);
            }
        });

        List<Category> categoriesFromDb = categoryService.getCategories();
        List<Category> categories = new ArrayList<>();
        for (Category item : categoriesFromDb) {
            if (!(item.getTag() != null && item.getTag().equals(StringUtil.UNDEFINED_TAG)))
                categories.add(item);
        }

        vlAdapter = new CategoryListAdapter(categories);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(rootView.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(vlAdapter);

        vlAdapter.notifyDataSetChanged();
        return rootView;
    }
}
