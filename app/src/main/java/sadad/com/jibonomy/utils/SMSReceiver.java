package sadad.com.jibonomy.utils;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsMessage;

import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.math.BigDecimal;
import java.util.Date;

import sadad.com.jibonomy.R;
import sadad.com.jibonomy.biz.dto.smsDto;
import sadad.com.jibonomy.entities.Transaction;
import sadad.com.jibonomy.services.SmsService;
import sadad.com.jibonomy.services.TransactionService;

public class SMSReceiver extends BroadcastReceiver {
    private Bundle bundle;
    private SmsMessage currentSMS;
    private String message;
    private TransactionService transactionService;

    @Override
    public void onReceive(Context context, Intent intent) {
        transactionService = new TransactionService(context);
//        NotifyUtil.notify(context);

        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdu_Objects = (Object[]) bundle.get("pdus");
                if (pdu_Objects != null) {

                    for (Object aObject : pdu_Objects) {

                        currentSMS = getIncomingMessage(aObject, bundle);

                        String senderNo = currentSMS.getDisplayOriginatingAddress();

                        message = currentSMS.getDisplayMessageBody();
//                        Toast.makeText(context, "senderNum: " + senderNo + " :\n message: " + message, Toast.LENGTH_LONG).show();
                        NotifyUtil.viewNotify(context);
                        String sender = senderNo.substring(senderNo.length() - 5, senderNo.length());
                        if (sender.equals("86201")) {
                            smsDto smsDto = SmsService.decodeSms(message);
                            Transaction transaction = new Transaction();
                            String amount = smsDto.getAmount().replace("+", "").replace("-", "").replace(",", "");
                            transaction.setAmount(new BigDecimal(amount));
                            transaction.setSubCategoryType(999999L);
                            transaction.setDescription("SMS");
                            PersianCalendar persianCalendar = new PersianCalendar(new Date().getTime());
                            transaction.setTransactionDate(persianCalendar.getPersianShortDate().replace("/", ""));
                            Integer hour = persianCalendar.get(PersianCalendar.HOUR_OF_DAY);
                            Integer min = persianCalendar.get(PersianCalendar.MINUTE);
                            transaction.setTransactionTime(hour.toString() + min.toString());

                            if (smsDto.getAmount().contains("-"))
                                transaction.setTransactionType(Transaction.EXPENSE);
                            else
                                transaction.setTransactionType(Transaction.INCOME);

                            transactionService.insert(transaction);

                        }

                        NotificationCompat.Builder mBuilder =
                                new NotificationCompat.Builder(context)
                                        .setSmallIcon(R.drawable.ic_launcher)
                                        .setContentTitle("My notification")
                                        .setContentText("Hello World!");

                        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                        mNotificationManager.notify(1, mBuilder.build());
                        NotifyUtil.notify(context);


                    }
                    this.abortBroadcast();
                    // End of loop
                }
            }
        } // bundle null
    }


    private SmsMessage getIncomingMessage(Object aObject, Bundle bundle) {
        SmsMessage currentSMS;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String format = bundle.getString("format");
            currentSMS = SmsMessage.createFromPdu((byte[]) aObject, format);
        } else {
            currentSMS = SmsMessage.createFromPdu((byte[]) aObject);
        }
        return currentSMS;
    }
}