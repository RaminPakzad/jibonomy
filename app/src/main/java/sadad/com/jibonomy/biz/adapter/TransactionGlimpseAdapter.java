package sadad.com.jibonomy.biz.adapter;

import android.content.Context;
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
import sadad.com.jibonomy.biz.entity.Transaction;

public class TransactionGlimpseAdapter extends RecyclerView.Adapter<TransactionGlimpseAdapter.MyViewHolder> {

    private List<Transaction> transactionList;
    private Context context;

    public TransactionGlimpseAdapter(List<Transaction> transactionList, Context context) {
        this.transactionList = transactionList;
        this.context = context;

    }

    @Override
    public TransactionGlimpseAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaction_glimpse_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TransactionGlimpseAdapter.MyViewHolder holder, int position) {
        Transaction transaction = transactionList.get(position);
        DecimalFormat df = new DecimalFormat("#,###");
        holder.amountOfTransaction.setText(df.format(transaction.getAmount()));
        holder.dateOfTransaction.setText("1397/01/02 14:22");
        holder.categoryImage.setImageResource(context.getResources().getIdentifier("store", "drawable", context.getPackageName()));

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
