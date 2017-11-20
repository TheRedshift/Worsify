package com.example.rahulsoni.worsify;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private MyBoundService.MyBinder myService = null;

    private ServiceConnection serviceConnection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder
                service) {
            myService = (MyBoundService.MyBinder) service;

            Log.d("g53mdp", "MainActivity onServiceConnected");
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("g53mdp", "MainActivity onServiceDisconnected");
            myService = null;
        }
    };

    @Override
    protected void onStart(){

        super.onStart();
        Intent intent = new Intent(this, MyBoundService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //this.bindService(new Intent(this, MyBoundService.class),
                //serviceConnection, Context.BIND_AUTO_CREATE);

        final ListView lv = findViewById(R.id.listView);
        File musicDir = new File(
                Environment.getExternalStorageDirectory().getPath()+ "/Music/");
        File list[] = musicDir.listFiles();
        lv.setAdapter(new ArrayAdapter<File>(this,
                android.R.layout.simple_list_item_1, list));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter,
                                    View myView,
                                    int myItemInt,
                                    long mylng) {
                File selectedFromList =(File) (lv.getItemAtPosition(myItemInt));
                Log.d("g53mdp", selectedFromList.getAbsolutePath());

                myService.load(selectedFromList.getAbsolutePath());

            }
        });


    }


    public void playClick(View view){

        myService.play();

    }

    public void pauseClick(View view){

        myService.pause();


    }

    public void stopClick(View view){

        myService.stop();

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    protected void onStop() {

            if (isFinishing()) {
                if (myService != null) {
                    unbindService(serviceConnection);
                    myService = null;
            }
        }

        super.onStop();

}}
