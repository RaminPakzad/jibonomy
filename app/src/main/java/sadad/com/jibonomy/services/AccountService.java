package sadad.com.jibonomy.services;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import sadad.com.jibonomy.dao.JibonomyRoomDatabase;
import sadad.com.jibonomy.entities.Account;
import sadad.com.jibonomy.entities.Merchant;

public class AccountService {

    private Context context;

    public AccountService(Context context) {
        JibonomyRoomDatabase db = JibonomyRoomDatabase.getDatabase(context);
        this.context = context;
    }

    public List<Account> getAccounts() {

        List<Account> res = new ArrayList<>();

        Account a0 = new Account();
        a0.setAccountId((long)1);
        a0.setAccountName("کیف پول جیبونومی");
        a0.setAccountNumber((long)8800121);
        a0.setAccountType((short)1);
        res.add(a0);

        Account a1 = new Account();
        a1.setAccountId((long)2);
        a1.setAccountName("حساب بانک ملی");
        a1.setAccountNumber((long)800019992);
        a1.setAccountType((short)2);
        res.add(a1);

        return res;
    }

}
