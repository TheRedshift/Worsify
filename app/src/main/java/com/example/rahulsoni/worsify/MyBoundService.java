package com.example.rahulsoni.worsify;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

public class MyBoundService extends Service {


    private final IBinder binder = new MyBinder();

    final int FOREGROUND_SERVICE_ID = 2;

    MP3Player myPlayer;

    @Override
    public void onCreate() {
        super.onCreate();

        new Thread()
        {
            @Override
            public void run()
            {
                myPlayer = new MP3Player();
                }
        }.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int id) {
        {
            Intent mainActivityIntent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, mainActivityIntent, PendingIntent.FLAG_CANCEL_CURRENT);

            Notification myNotification = new Notification.Builder(this).setContentTitle("Worsify")
                    .setContentText("Worse in every way")
                    .setContentIntent(pendingIntent)
                    .setOngoing(true)
                    .setSmallIcon(R.drawable.ic_stat_name)
                    .setWhen(System.currentTimeMillis())
                    .build();

            //startForeground(FOREGROUND_SERVICE_ID, myNotification);

            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            manager.notify(FOREGROUND_SERVICE_ID, myNotification);

            Notification noti = new Notification.Builder(this)
                    .setContentTitle("New mail from ")
                    .setContentText("yo")
                    .setSmallIcon(R.drawable.ic_stat_name)
                    .build();

            return START_STICKY;


        }

    }
    @Override
    public IBinder onBind(Intent arg0) {
        return binder;
    }


    public class MyBinder extends Binder
    {

        public MP3Player.MP3PlayerState getState(){

            return myPlayer.getState();

        }

        public void load(String filePath){

            myPlayer.stop();

            myPlayer.load(filePath);

        }

        public String getFilePath(){

            return myPlayer.getFilePath();

        }

        public int getProgress(){

            return myPlayer.getProgress();

        }

        public int getDuration(){

            return myPlayer.getDuration();

        }

        public void play(){

            myPlayer.play();

        }

        public void pause(){

            myPlayer.pause();

        }

        public void stop(){

            myPlayer.stop();

        }

    }

    @Override
    public boolean onUnbind(Intent intent)
    {
        Log.d("g53mdp", "Service Unbound");
        return false;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        myPlayer.stop();
        Log.d("g53mdp", "Service is Destroyed");
    }

    @Override
    public void onRebind(Intent intent)
    {
        super.onRebind(intent);
        Log.d("g53mdp", "Service is Rebound");
    }
}