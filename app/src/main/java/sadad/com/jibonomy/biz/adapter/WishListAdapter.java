package sadad.com.jibonomy.biz.adapter;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import sadad.com.jibonomy.R;
import sadad.com.jibonomy.WishFragment;
import sadad.com.jibonomy.entities.Wish;
import sadad.com.jibonomy.utils.ImageSaver;
import sadad.com.jibonomy.utils.NavigationUtil;


public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.MyViewHolder> {

    private List<Wish> wishList;
    private BigDecimal currentSaving;
    private View itemView;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, progressPercent;
        public ProgressBar progressBar;
        public RelativeLayout relativeLayout;
        public Button editWish;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            progressBar = view.findViewById(R.id.progress_bar);
            progressPercent = view.findViewById(R.id.progress_percent);
            relativeLayout = view.findViewById(R.id.main_layout);
            editWish = view.findViewById(R.id.editWish);
        }
    }

    public WishListAdapter(List<Wish> wishList, BigDecimal currentSaving) {
        this.wishList = wishList;
        this.currentSaving = currentSaving;
    }

    @Override
    public WishListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wish_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WishListAdapter.MyViewHolder holder, int position) {
        final Wish wish = wishList.get(position);
        holder.title.setText(wish.getName());
        BigDecimal leftAmount = wish.getBudget().subtract(currentSaving);

        Bitmap wishImage = new ImageSaver(itemView.getContext())
                .setFileName(wish.getPicName())
                .setExternal(false)
                .load();
        Drawable dr = new BitmapDrawable(wishImage);
        holder.relativeLayout.setBackgroundDrawable(dr);


        BigDecimal progressPercent;
        if (leftAmount.compareTo(BigDecimal.ZERO) > 0) {
            progressPercent = currentSaving.multiply(BigDecimal.valueOf(100)).divide(wish.getBudget(), RoundingMode.CEILING);
            holder.progressBar.setProgress(progressPercent.intValue());
        } else {
            progressPercent = BigDecimal.valueOf(100);
            holder.progressBar.setProgress(100);
        }
        holder.progressPercent.setText(progressPercent + "%");
        holder.editWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WishFragment fragment = new WishFragment();
                Bundle args = new Bundle();
                args.putLong(Wish.WISH_ID_LABLE, wish.getWishId());
                fragment.setArguments(args);
                NavigationUtil.changeFragment(fragment, itemView);
            }
        });
    }

    @Override
    public int getItemCount() {
        return wishList.size();
    }
}
