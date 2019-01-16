package sadad.com.jibonomy.utils;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import sadad.com.jibonomy.R;


public class NavigationUtil {


    public static void changeFragment(android.support.v4.app.Fragment fragment, View rootView) {
        FragmentManager fragmentManager = ((FragmentActivity) rootView.getContext()).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out, R.anim.abc_popup_enter, R.anim.abc_popup_exit);
        fragmentTransaction.replace(R.id.container_body, fragment).addToBackStack("tag").commit();
    }

}
