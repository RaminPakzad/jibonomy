package sadad.com.jibonomy.biz.adapter;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import sadad.com.jibonomy.R;
import sadad.com.jibonomy.entities.Account;
import sadad.com.jibonomy.entities.Merchant;
import sadad.com.jibonomy.services.MerchantService;

public class AccountListAdapter extends RecyclerView.Adapter<AccountListAdapter.MyViewHolder> {

    private List<Account> accounts;
    private View itemView;

    public AccountListAdapter(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public AccountListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.account_list_item, parent, false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(itemView.getContext(), "Account clicked", Toast.LENGTH_SHORT).show();
            }
        });
        return new AccountListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Account account = accounts.get(position);
        holder.accountName.setText(account.getAccountName());

        String accountDescription = "";
        if( account.getAccountType() == 1 ){
            accountDescription = "کیف پول جیبونومی به شماره " + account.getAccountNumber();
        } else {
            accountDescription = "حساب به شماره " + account.getAccountNumber();
        }
        holder.accountDescription.setText(accountDescription);
        int id = itemView.getContext().getResources().getIdentifier("sadad.com.jibonomy:drawable/account_type_" + account.getAccountType(), null, null);
        Drawable myDrawable = itemView.getContext().getResources().getDrawable(id);
        holder.accountImage.setBackground(myDrawable);
    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView accountName;
        public TextView accountDescription;
        public ImageView accountImage;
        public MyViewHolder(View view) {
            super(view);
            accountName = view.findViewById(R.id.account_name);
            accountDescription = view.findViewById(R.id.account_description);
            accountImage = view.findViewById(R.id.account_image);
        }
    }

}
