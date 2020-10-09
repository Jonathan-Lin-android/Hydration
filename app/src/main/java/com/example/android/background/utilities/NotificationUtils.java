package com.example.android.background.utilities;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import com.example.android.background.MainActivity;
import com.example.android.background.R;

public class NotificationUtils {

    // notification ID used to access our notification after we've displayed it. Usage for when notification is canceled
    // or being updated.
    private static final int WATER_REMINDER_NOTIFICATION_ID = 1138;

    // pending intent id used to uniquely refernce the pending intent.
    private static final int WATER_REMINDER_PENDING_INTENT_ID = 3417;

    // notification channel id is used to link notifications to this channel
    private static final String WATER_REMINDER_NOTIFICATION_CHANNEL_ID = "reminder_notification_channel";

    public static void clearAllNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();

    }

    //creating the notification, notification channel and displaying the notification
    public static void remindUserBecauseCharging(Context context) {
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        //notification must belong in a specific notification channel

        // NotificationChannel(string_id, channel_name, importance level)
        //high importance to force this notification to pop up on the device using the heads-up display
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(WATER_REMINDER_NOTIFICATION_CHANNEL_ID,
                    context.getString(R.string.main_notification_channel_name), NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }

        // build notification
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context,
                WATER_REMINDER_NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_drink_notification)
                .setLargeIcon(largeIcon(context))
                .setContentTitle(context.getString(R.string.charging_reminder_notification_title))
                .setContentText(context.getString(R.string.charging_reminder_notification_body))
                /*
                  Style
                  https://developer.android.com/reference/android/app/Notification.BigPictureStyle.html
                  Using Big View Styles
                  https://developer.android.com/training/notify-user/expanded.html
                  */
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(context.getString(R.string.charging_reminder_notification_body)))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                //pending intent of MainActivity
                .setContentIntent(contentIntent(context))
                //notification will go away when its been clicked.
                .setAutoCancel(true);

        //heads up pop-up display
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }

        notificationManager.notify(WATER_REMINDER_NOTIFICATION_ID, notificationBuilder.build());
    }

    //creates a pending intent which will trigger when the notification is pressed.
    private static PendingIntent contentIntent(Context context) {
        Intent startActivityIntent = new Intent(context, MainActivity.class);

        // not expecting to recreate the same PendingIntent so FLAG_UPDATE_CURRENT
        return PendingIntent.getActivity(context, WATER_REMINDER_PENDING_INTENT_ID, startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    // decodes a bitmap needed for the notification
    private static Bitmap largeIcon(Context context) {
        Resources res = context.getResources();

        Bitmap largeIcon = BitmapFactory.decodeResource(res, R.drawable.ic_local_drink_black_24px);
        return largeIcon;
    }
}
