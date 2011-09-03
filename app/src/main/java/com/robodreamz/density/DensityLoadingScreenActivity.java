package com.robodreamz.density;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class DensityLoadingScreenActivity extends Activity {

    public static final String TAG = "DensityLoadingScreenActivity";
    private static final long ONE_SECOND = 1000l;
    private Timer timer;

    @Override protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);
        final LoadingState state = (LoadingState) getLastNonConfigurationInstance();
        if (state != null && state.timer != null) {
            timer = state.timer;
        }
    }

    @Override protected void onStart() {
        Log.i(TAG, "Calling start");
        super.onStart();
        if (!isFinishing()  && !getDensityApp().isAppScheduled()) {
            if (timer == null) {
                Log.i(TAG, "creating timer");
                timer = new Timer("LoadingScreenSpinner");
            }

            callAppWhenTimerExpires();
        }

        if (getDensityApp().isAppScheduled()) { //if we scheduled the app then finish this Activity..
            finish();
        }
    }

    private void callAppWhenTimerExpires() {
        timer.schedule(new TimerTask() {
            @Override public void run() {
                if (!getDensityApp().isAppScheduled()) {
                    Log.i(TAG, "starting activity: " + getDensityApp().isAppScheduled());
                    startActivity(new Intent(DensityLoadingScreenActivity.this, DensityAppActivity.class));
                    //there could be a race condition here. If we launch the app but there is a rotation before we set the variable.
                    //wish there was an atomic way to do this.
                    Log.i(TAG, "setting scheduled");
                    getDensityApp().setAppScheduled(true);
                    Log.i(TAG, "scheduled set:" + getDensityApp().isAppScheduled());
                    finish();
                }
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

    @Override public Object onRetainNonConfigurationInstance() {
        return new LoadingState(timer);
    }

    private DensityApplication getDensityApp() {
        return (DensityApplication) getApplication();
    }

    private static final class LoadingState {
        private final Timer timer;

        public LoadingState(final Timer timer) {
            this.timer = timer;
        }
    }
}
