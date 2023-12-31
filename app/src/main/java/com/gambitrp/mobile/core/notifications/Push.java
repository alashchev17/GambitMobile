package com.gambitrp.mobile.core.notifications;

import static kotlin.random.RandomKt.Random;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.gambitrp.mobile.R;
import com.gambitrp.mobile.core.Permissions;
import com.gambitrp.mobile.core.Window;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.concurrent.Semaphore;

public class Push extends FirebaseMessagingService {
    private final Semaphore semaphoreMsg = new Semaphore(1);

    private static final String channel = "GambitMobileID";

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);

        System.out.println("[CLIENT] onNewToken: " + token);
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        new Thread(() -> {
            try {
                semaphoreMsg.acquire();

                System.out.println("[CLIENT] onMessageReceived: " + remoteMessage);

                Permissions.check(Manifest.permission.POST_NOTIFICATIONS, new Permissions.PermissionResponse() {
                    @Override
                    public void allowed() {
                        RemoteMessage.Notification notification = remoteMessage.getNotification();

                        if (notification == null) {
                            return;
                        }

                        String header = notification.getTitle();
                        String body = notification.getBody();

                        send(header, body);
                    }
                });
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                semaphoreMsg.release();
            }
        }).start();
    }

    @SuppressLint("MissingPermission")
    public static void send(String header, String body) {
        Window window = Window.getContext();

        int id = Random(System.currentTimeMillis()).nextInt(10000);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(Push.channel, "Gambit Mobile", NotificationManager.IMPORTANCE_HIGH);

            window.getSystemService(NotificationManager.class).createNotificationChannel(channel);

            Notification.Builder notification = new Notification.Builder(window, Push.channel)
                    .setContentTitle(header)
                    .setContentText(body)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true);

            NotificationManagerCompat.from(window).notify(id, notification.build());
        } else {
            NotificationCompat.Builder notification = new NotificationCompat.Builder(window, channel)
                    .setContentTitle(header)
                    .setContentText(body)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true);

            NotificationManager manager = (NotificationManager) window.getSystemService(Context.NOTIFICATION_SERVICE);

            manager.notify(id, notification.build());
        }
    }
}
