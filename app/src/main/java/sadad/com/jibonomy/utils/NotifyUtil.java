package sadad.com.jibonomy.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import sadad.com.jibonomy.MainActivity;
import sadad.com.jibonomy.R;

import static android.content.Context.NOTIFICATION_SERVICE;


public class NotifyUtil {

    public static void viewNotify(Context c) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(c, "NOTIFY")
                .setSmallIcon(R.drawable.cash_multiple)
                .setContentTitle("توجه")
                .setContentText("مراقب هزینه های خود باشید")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        NotificationManager notificationManager = (NotificationManager) c.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, mBuilder.build());


    }


    public static void notify(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.home)
                        .setContentTitle("Notification Title")
                        .setContentText("Notification ")
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, mBuilder.build());
    }

    public static void sendNotification(Context mContext) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(mContext.getApplicationContext(), "notify_001");
        Intent ii = new Intent(mContext.getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, ii, 0);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        //bigText.bigText(verseurl);
        bigText.setBigContentTitle("مراقب هزینه های خودت باش");
        bigText.setSummaryText("جیبونومی");

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.app_icon_s);
        mBuilder.setContentTitle("توجه");
//        mBuilder.setContentText("جیبونومی رو باز کن نگاهی به آرزوهات بکن");
        mBuilder.setContentText("این دختره فردا میره و تو با هزار تا آرزو میمونی");
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);

        NotificationManager mNotificationManager =
                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "YOUR_CHANNEL_ID";
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        mNotificationManager.notify(0, mBuilder.build());

    }


}
