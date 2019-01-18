package sadad.com.jibonomy.biz.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import sadad.com.jibonomy.R;
import sadad.com.jibonomy.entities.Category;
import sadad.com.jibonomy.entities.Transaction;
import sadad.com.jibonomy.fragments.TransactionFragment;
import sadad.com.jibonomy.services.CategoryService;
import sadad.com.jibonomy.utils.NavigationUtil;
import sadad.com.jibonomy.utils.StringUtil;

public class TransactionGlimpseAdapter extends RecyclerView.Adapter<TransactionGlimpseAdapter.MyViewHolder> {

    private List<Transaction> transactionList;
    private Context context;
    private CategoryService categoryService;
    private View itemView;

    public TransactionGlimpseAdapter(List<Transaction> transactionList, Context context) {
        this.transactionList = transactionList;
        this.context = context;
        categoryService = new CategoryService(context);
    }

    @Override
    public TransactionGlimpseAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaction_glimpse_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TransactionGlimpseAdapter.MyViewHolder holder, int position) {
        final Transaction transaction = transactionList.get(position);


        Category c = categoryService.get(transaction.getSubCategoryType());
        DecimalFormat df = new DecimalFormat("#,###");
        String amount = df.format(transaction.getAmount()) + " ریال ";
        holder.amountOfTransaction.setText(amount);
        String dateTime = StringUtil.formatDate(transaction.getTransactionDate()) + " " + StringUtil.formatTime(transaction.getTransactionTime());
        holder.dateOfTransaction.setText(dateTime);

        holder.transactionType.setImageResource(context.getResources().getIdentifier((transaction.getTransactionType() == (byte) 1 ? "arrow_up" : "arrow_down"), "drawable", context.getPackageName()));
        if (c == null) {
            holder.relativeLayout.setBackground(context.getDrawable(R.color.tooLightGray));
            holder.categoryImage.setImageResource(context.getResources().getIdentifier("ic_help_circle_outline_grey600_24dp", "drawable", context.getPackageName()));
        } else {
            holder.categoryImage.setImageResource(context.getResources().getIdentifier(c.getIconName(), "drawable", context.getPackageName()));
        }


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransactionFragment fragment = new TransactionFragment();
                Bundle args = new Bundle();
                args.putLong(Transaction.TRANSACTION_ID_LABEL, transaction.getTransactionId());
                fragment.setArguments(args);
                NavigationUtil.changeFragment(fragment, itemView);
            }
        });

    }


    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView amountOfTransaction;
        public TextView dateOfTransaction;
        public ImageView transactionType;
        public ImageButton categoryImage;


        public RelativeLayout relativeLayout;

        public MyViewHolder(View view) {
            super(view);
            amountOfTransaction = view.findViewById(R.id.amountOfTransaction);
            dateOfTransaction = view.findViewById(R.id.dateOfTransaction);
            transactionType = view.findViewById(R.id.transactionType);
            categoryImage = view.findViewById(R.id.categoryImage);
            relativeLayout = view.findViewById(R.id.transactionGlimpseLayout);
        }
    }
}
