package sadad.com.jibonomy.utils;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import sadad.com.jibonomy.R;


public class NotifyUtil {

    public static void viewNotify(Context c){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(c, "NOTIFY")
                .setSmallIcon(R.drawable.cash_multiple)
                .setContentTitle("توجه")
                .setContentText("مراقب هزینه های خود باشید")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        NotificationManager notificationManager = (NotificationManager) c.getSystemService(c.NOTIFICATION_SERVICE);
        notificationManager.notify(1, mBuilder.build());


    }

}
