//package com.anish.expirydatereminder;
//
//import android.annotation.SuppressLint;
//import android.app.PendingIntent;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//
//import androidx.core.app.NotificationCompat;
//import androidx.core.app.NotificationManagerCompat;
//
//import java.time.LocalDate;
//
//public class WakeUpReceiver extends BroadcastReceiver {
//    @SuppressLint("MissingPermission")
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        DatabaseHandler dbh = new DatabaseHandler(context);
//        NotificationDatabase ndb = new NotificationDatabase(context);
//        int z=0;
//        for (ItemModel a:dbh.getAllItems()) {
//            LocalDate ld = LocalDate.of(a.getYear(), a.getMonth(), a.getDate());
//            LocalDate ld2 = ld.minusDays(14);
//            LocalDate today = LocalDate.now();
//
//            if (today.isAfter(ld2) || today.isEqual(ld2)) {
//                z++;
//            }
//        }
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "edr_channel_1")
//                .setSmallIcon(R.drawable.logo_transparent_background)
//                .setContentTitle("Claim Alert")
//                .setContentText("You have "+z+" items expiring within 14 days!")
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
//
//        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,new Intent(context, SplashActivity.class), PendingIntent.FLAG_IMMUTABLE);
//        builder.setContentIntent(contentIntent);
//
//        if(ndb.getCurrentSetting()==1 && z>0) {
//            notificationManager.notify(13, builder.build());
//        }
//    }
//}
package com.anish.expirydatereminder;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class WakeUpReceiver extends BroadcastReceiver {
    @SuppressLint("MissingPermission")
    @Override
    public void onReceive(Context context, Intent intent) {
        DatabaseHandler dbh = new DatabaseHandler(context);
        NotificationDatabase ndb = new NotificationDatabase(context);
        int z = 0;
        List<String> expiringItemsDetails = new ArrayList<>();

        LocalDate today = LocalDate.now();

        for (ItemModel a : dbh.getAllItems()) {
            LocalDate expiryDate = LocalDate.of(a.getYear(), a.getMonth(), a.getDate());
            LocalDate alertDate = expiryDate.minusDays(14);

            if (today.isAfter(alertDate) || today.isEqual(alertDate)) {
                z++;
                // Format the item details as "category - item - expiry_date"
                String itemDetail = z +") "+ a.getCategory() + " - " + a.getItem() + " - " + expiryDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                expiringItemsDetails.add(itemDetail);
            }
        }

        // Build the main notification message
        StringBuilder notificationContent = new StringBuilder("You have " + z + " items expiring within 14 days!");

        // Append each expiring item in the required format
        for (String itemDetail : expiringItemsDetails) {
            notificationContent.append("\n").append(itemDetail);
        }

        // Set up the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "edr_channel_1")
                .setSmallIcon(R.drawable.logo_transparent_background)
                .setContentTitle("Claim Alert Remainder!!")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationContent.toString()))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(context, SplashActivity.class), PendingIntent.FLAG_IMMUTABLE);
        builder.setContentIntent(contentIntent);

        // Display the notification if enabled in settings and items are expiring soon
        if (ndb.getCurrentSetting() == 1 && z > 0) {
            notificationManager.notify(13, builder.build());
        }
    }
}
