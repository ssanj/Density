package com.robodreamz.density;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class DensityLoadingScreenActivity extends Activity {

    @Override protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);
    }

    @Override protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                startActivity(new Intent(DensityLoadingScreenActivity.this, DensityAppActivity.class));
            }
        }, 1000);

//        DensityLoadingScreenActivity.this.finish();
    }
}
