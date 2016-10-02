package com.jackleeentertainment.oq.firebase.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.Ram;
import com.jackleeentertainment.oq.debug.L;
import com.jackleeentertainment.oq.generalutil.StringGenerator;
import com.jackleeentertainment.oq.object.Chat;
import com.jackleeentertainment.oq.object.Post;
import com.jackleeentertainment.oq.object.Profile;
import com.jackleeentertainment.oq.ui.layout.activity.MainActivity;

/**
 * Created by Jacklee on 16. 5. 29..
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    String TAG = this.getClass().getSimpleName();

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.

        L.pojo(remoteMessage);
        L.pojo(remoteMessage.getNotification());

        Gson gson = new Gson();

        String tag = remoteMessage.getNotification().getTag();

        if (tag.equals("post")) {
            //Objects
            NotificationBody body = gson.fromJson(
                    remoteMessage.getNotification().getBody(),
                    NotificationBody.class);
            Profile profile = body.getProfile();
            Post post = gson.fromJson(
                    body.getObj().toString(),
                    Post.class);
            //RAM
            Ram.addProfile(profile.getUid(), profile);
            Ram.addPost(post.getOid(), post);
            //Upload To Fireabse
            //Notification
            doPostNotifiation(profile, post);

        } else if (tag.equals("chat")) {

            //Objects
            NotificationBody body = gson.fromJson(remoteMessage.getNotification().getBody(),
                    NotificationBody.class);
            Profile profile = body.getProfile();
            Chat chat = gson.fromJson(body.getObj().toString(),
                    Chat.class);

            //RAM
            Ram.addProfile(profile.getUid(), profile);

            //Upload To Fireabse


            //Notification
            doChatNotifiation(profile, chat);

        } else if (tag.equals("alarmpost")) {

        } else if (tag.equals("alarmchat")) {

        } else if (tag.equals("friend_req")) {

        }
    }





    private void doPostNotifiation(Profile profile, Post post) {

                Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("fragment", 2);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0 /* Request code */,
                intent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_check_white_48dp)
                .setContentTitle(profile.getFull_name())
                .setContentText(StringGenerator.postedx(post))
                //.addAction(new NotificationCompat.Action())
                //.addPerson(uriPerson)
                .setAutoCancel(true)
                .setCategory(NotificationCompat.CATEGORY_SOCIAL)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    private void doChatNotifiation(Profile profile , Chat chat) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_check_white_48dp)
                .setContentTitle(profile.getFull_name())
                .setContentText(chat.getTxt())
                //.addAction(new NotificationCompat.Action())
                //.addPerson(uriPerson)
                .setAutoCancel(true)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

}