package com.example.lumimonitor;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class NotificationsApp extends Application {

    public static final String CHANNEL_1_ID = "channel1";
    public static final String CHANNEL_2_ID = "channel2";
    long[] pattern = {0, 200};


    @Override
    public void onCreate() {
        super.onCreate();

        createNotoficationsChannels();
    }

    private void createNotoficationsChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel1 = new NotificationChannel(
                        CHANNEL_1_ID,
                        "Reminders",
                        NotificationManager.IMPORTANCE_HIGH
                );
                channel1.setDescription("Reminder Notifications From Lumi Monitor App");
                channel1.setVibrationPattern(pattern);

                NotificationChannel channel2 = new NotificationChannel(
                        CHANNEL_2_ID,
                        "Other",
                        NotificationManager.IMPORTANCE_LOW
                );
                channel2.setDescription("Misc. Notifications From Lumi Monitor App");
                channel2.setVibrationPattern(pattern);

                NotificationManager manager = getSystemService(NotificationManager.class);
                manager.createNotificationChannel(channel1);
                manager.createNotificationChannel(channel2);

            }
        }
    }
}
