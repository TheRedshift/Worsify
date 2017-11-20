package com.example.rahulsoni.worsify;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

/**
 * Created by Rahul Soni on 19/11/2017.
 */

public class MyIntentService extends IntentService
{
    public MyIntentService()
    {
        super("MyIntentService");
    }
    @Override
    protected void onHandleIntent(Intent arg0)
    {

        Bundle bundle = arg0.getExtras();

        Integer data = bundle.getInt("name");

        SystemClock.sleep(3000);

        Log.d("g53mdp", "Test Click service run with data:"+ data + " !");

    }
}