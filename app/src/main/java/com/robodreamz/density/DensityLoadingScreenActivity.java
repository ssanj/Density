package com.robodreamz.density;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class DensityLoadingScreenActivity extends Activity {

    public static final String TAG = "DensityLoadingScreenActivity";
    private static final long ONE_SECOND = 1000l;
    private Timer timer;

    //TODO: Add configuration change for timer.
    @Override protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);
    }

    @Override protected void onStart() {
        Log.i(TAG, "Calling start");
        super.onStart();
        if (!isFinishing()) {
            Log.i(TAG, "creating timer");
            timer = new Timer("LoadingScreenSpinner");
            callAppWhenTimerExpires();
        }
    }

    private void callAppWhenTimerExpires() {
        timer.schedule(new TimerTask() {
            @Override public void run() {
                Log.i(TAG, "starting activity");
                startActivity(new Intent(DensityLoadingScreenActivity.this, DensityAppActivity.class));
                finish();
            }
        }, ONE_SECOND);
    }

    @Override public void onBackPressed() {
        super.onBackPressed();
        if (timer != null) {
            Log.i(TAG, "Trying to cancel timer");
            timer.cancel();
            timer.purge();
        }
    }
}
