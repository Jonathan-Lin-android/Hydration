package com.example.android.background.sync;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import com.example.android.background.utilities.NotificationUtils;
import com.example.android.background.utilities.PreferenceUtilities;

public class ReminderTasks {
    public static final String ACTION_INCREMENT_WATER_COUNT = "increment-water-count";

    public static final String ACTION_DISMISS_NOTIFICATION = "dismiss-notification";

    public static final String ACTION_CHARGING_REMINDER = "charging-reminder";


    public static void executeTask(Context context, String action) {
        if(ACTION_INCREMENT_WATER_COUNT.equals(action)) {
            incrementWaterCount(context);
        }
        else if (ACTION_DISMISS_NOTIFICATION.equals(action))
        {
            // dismisses notification
            NotificationUtils.clearAllNotifications(context);
        }
        else if (ACTION_CHARGING_REMINDER.equals(action)) {
            issueChargingReminder(context);
        }
    }

    // issue a notification and also increment the charging reminder count
    private static void issueChargingReminder(final Context context) {
        PreferenceUtilities.incrementChargingReminderCount(context);
        NotificationUtils.remindUserBecauseCharging(context);
    }

    private static void incrementWaterCount(Context context) {
        PreferenceUtilities.incrementWaterCount(context);
        // dismisses notification
        NotificationUtils.clearAllNotifications(context);
    }
}
