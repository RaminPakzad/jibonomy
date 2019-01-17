package sadad.com.jibonomy.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import sadad.com.jibonomy.MainActivity;
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


    public static void notify(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.home)
                        .setContentTitle("Notification Title")
                        .setContentText("Notification ")
                        .setContentIntent(pendingIntent );

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, mBuilder.build());
    }



}
