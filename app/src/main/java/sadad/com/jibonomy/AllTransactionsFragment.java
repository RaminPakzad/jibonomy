package sadad.com.jibonomy;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import sadad.com.jibonomy.biz.adapter.TransactionGlimpseAdapter;
import sadad.com.jibonomy.entities.Transaction;
import sadad.com.jibonomy.services.TransactionService;

/**
 * @author ramin pakzad (RPakzadmanesh@gmail.com) on 1/13/2019.
 */
public class AllTransactionsFragment extends Fragment {
    View rootView;
    SharedPreferences prefs;
    private RecyclerView transactionGlimpseList;
    private TransactionGlimpseAdapter transactionGlimpseAdapter;
    private TransactionService transactionService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.all_transactions_fragment, container, false);

        prefs = getContext().getSharedPreferences("user", getContext().MODE_PRIVATE);
        DecimalFormat df = new DecimalFormat("#,###");

        transactionGlimpseList = rootView.findViewById(R.id.transactionGlimpseList);
        List<Transaction> transactions = new ArrayList<>();
        transactionService = new TransactionService(getContext());
        transactions = transactionService.getTransactiones();

        transactionGlimpseAdapter = new TransactionGlimpseAdapter(transactions, rootView.getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(rootView.getContext());
        transactionGlimpseList.setLayoutManager(mLayoutManager);
        transactionGlimpseList.setItemAnimator(new DefaultItemAnimator());
        transactionGlimpseList.setAdapter(transactionGlimpseAdapter);
        return rootView;
    }


}
