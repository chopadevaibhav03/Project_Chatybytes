package com.makemoney2023.online.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.makemoney2023.online.MainActivity;
import com.makemoney2023.online.R;

public class MyFirebaseInstanceService extends FirebaseMessagingService {

    private static final String TAG = "FirebaseMessageService";
    Bitmap bitmap;
    private final String CHANNEL_ID = "CHANNEL_ID";
    private final int NOTIFICATION_ID = 1;
    String url;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d(TAG, "From: " + remoteMessage.getFrom());

        Map<String, String> dataMap = remoteMessage.getData();
        //Log.d("ddddd",dataMap.get("id"));

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {


        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {

            if (remoteMessage.getNotification().getImageUrl() != null) {

                url = remoteMessage.getNotification().getImageUrl().toString();

            }

            createNot();

            String body = remoteMessage.getNotification().getBody();
            String id = remoteMessage.getData().get("id");
            String title = remoteMessage.getData().get("title");

            addNot(title, body, url, id);


            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getTitle());
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getImageUrl());

            //Toast.makeText(this, title+" "+body, Toast.LENGTH_SHORT).show();


        }


    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }


    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }

    public void createNot() {

        // Toast.makeText(this, "Create Not", Toast.LENGTH_SHORT).show();

        CharSequence name = "Name";
        String desc = "Description";

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(desc);

            NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);

        }
    }


    public void addNot(String title, String body, String url, String id) {


        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        builder.setSmallIcon(R.mipmap.ic_launcher_round);
        builder.setLargeIcon(getBitmapfromUrl(url));
        builder.setContentTitle(title);
        builder.setContentText(body);
        builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(getBitmapfromUrl(url)));
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setAutoCancel(true);
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getApplicationContext());
        managerCompat.notify(NOTIFICATION_ID, builder.build());


    }

}