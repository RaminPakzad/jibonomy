package sadad.com.jibonomy.biz.adapter;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import sadad.com.jibonomy.R;
import sadad.com.jibonomy.entities.Merchant;
import sadad.com.jibonomy.services.MerchantService;

public class MerchantListAdapter extends RecyclerView.Adapter<MerchantListAdapter.MyViewHolder> {
    private List<Merchant> merchants;
    private View itemView;
    private MerchantService merchantService;

    public MerchantListAdapter(List<Merchant> merchants) {

        this.merchants = merchants;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.merchant_list_item, parent, false);
        merchantService = new MerchantService(itemView.getContext());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(itemView.getContext(), "onclick merchant", Toast.LENGTH_SHORT).show();
            }
        });
        return new MerchantListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Merchant merchant = merchants.get(position);
        holder.title.setText(merchant.getTitle());
        holder.description.setText(merchant.getDescription());
        holder.percent.setText(merchant.getPercent().toString() + "%");
        int id = itemView.getContext().getResources().getIdentifier("sadad.com.jibonomy:drawable/" + merchant.getPicName(), null, null);
        Drawable myDrawable = itemView.getContext().getResources().getDrawable(id);
        holder.relativeLayout.setBackground(myDrawable);

        //imageview.setImageDrawable(myDrawable);
        /*Bitmap merchantImage = new ImageSaver(itemView.getContext())
                .setFileName(merchant.getPicName())
                .setExternal(false)
                .load();
        Drawable dr = new BitmapDrawable(merchantImage);
        holder.relativeLayout.setBackgroundDrawable(dr);*/


    }

    @Override
    public int getItemCount() {
        return merchants.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView description;
        public TextView percent;

        public RelativeLayout relativeLayout;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            percent = view.findViewById(R.id.percent);
            description = view.findViewById(R.id.description);
            relativeLayout = view.findViewById(R.id.main_layout);
        }
    }
}
