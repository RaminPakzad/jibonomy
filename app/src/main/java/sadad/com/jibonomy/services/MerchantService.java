package sadad.com.jibonomy.services;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import sadad.com.jibonomy.entities.Merchant;

public class MerchantService {
    public MerchantService(Context context) {
    }

    public List<Merchant> getMerchants() {
        List<Merchant> merchants = new ArrayList<>();

        Merchant merchant1 = new Merchant();
        merchant1.setTitle("فروشگاه عصر نو");
        merchant1.setDescription("پوشاک ورزشی");
        merchant1.setPercent((byte) 20);
        merchant1.setPicName("store1");

        Merchant merchant2 = new Merchant();
        merchant2.setTitle("کافی شاپ هفت");
        merchant2.setDescription("رستوران و کافی شاب");
        merchant2.setPercent((byte) 15);
        merchant2.setPicName("store2");

        Merchant merchant3 = new Merchant();
        merchant3.setTitle("بوتیک رضا");
        merchant3.setDescription("پوشاک ");
        merchant3.setPercent((byte) 22);
        merchant3.setPicName("store3");

        merchants.add(merchant1);
        merchants.add(merchant2);
        merchants.add(merchant3);
        return merchants;
    }
}
