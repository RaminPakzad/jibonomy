package sadad.com.jibonomy.services;


import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.TextView;

import sadad.com.jibonomy.R;
import sadad.com.jibonomy.biz.dto.UserDto;
import sadad.com.jibonomy.dao.JibonomyRoomDatabase;

public class UserService {

    private Context context;

    public UserService(Context context) {
        this.context = context;
    }

    public UserDto login(){
        return new UserDto("f.pashaee","f.pashaee@yahoo.com","فرزین","پاشائی","1382678273" );
    }

    public long getUserDailyBudget(){
        SharedPreferences prefs = context.getSharedPreferences("user", context.MODE_PRIVATE);
        return prefs.getLong("dailyBudget", 20000);
    }


}
