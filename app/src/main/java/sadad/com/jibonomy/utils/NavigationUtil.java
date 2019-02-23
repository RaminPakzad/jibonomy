package sadad.com.jibonomy.utils;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import sadad.com.jibonomy.R;


public class NavigationUtil {


    public static void changeFragment(android.support.v4.app.Fragment fragment, View rootView) {
        FragmentManager fragmentManager = ((FragmentActivity) rootView.getContext()).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out, R.anim.abc_popup_enter, R.anim.abc_popup_exit);
        fragmentTransaction.replace(R.id.container_body, fragment).addToBackStack("tag").commit();
    }

    @NonNull
    public static TextWatcher getCommaSeparatedEditText() {
        return new TextWatcher() {
            boolean isEdiging;
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override public void afterTextChanged(Editable s) {
                if(isEdiging) return;
                isEdiging = true;

                String str = s.toString().replaceAll( "[^\\d]", "" );
                double s1 = Double.parseDouble(str);

                NumberFormat nf2 = NumberFormat.getInstance(Locale.ENGLISH);
                ((DecimalFormat)nf2).applyPattern("$ ###,###.###");
                s.replace(0, s.length(), nf2.format(s1));

                isEdiging = false;
            }
        };
    }

}
