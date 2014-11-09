package com.ayrten.scrots.android;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.ayrten.scrots.screens.AdCallback;
import com.ayrten.scrots.screens.ScrotsGame;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class AdLauncher extends AndroidApplication  implements AdCallback {

    protected AdView adView;

    private final int SHOW_ADS = 1;
    private final int HIDE_ADS = 0;

    protected Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case SHOW_ADS:
                {
                    adView.setVisibility(View.VISIBLE);
                    break;
                }
                case HIDE_ADS:
                {
                    adView.setVisibility(View.GONE);
                    break;
                }
            }
        }
    };

    @Override public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create the layout
        RelativeLayout layout = new RelativeLayout(this);

        // Do the stuff that initialize() would do for you
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        // Create the libgdx View
        View gameView = initializeForView(new ScrotsGame(this));

        // Create and setup the AdMob view
//        adView = new AdView(this); // Put in your secret key here
//        adView.loadAd(new AdRequest(null));
        
        LayoutInflater inflater = LayoutInflater.from(layout.getContext());
        View inflatedLayout= inflater.inflate(R.layout.fragment_ad, null, false);
        
        adView = (AdView) inflatedLayout.findViewById(R.id.adView); // Put in your secret key here
        
        AdRequest adRequest = new AdRequest.Builder()
        .addTestDevice("ABCDEF012345")
        .build();
        
        adView.loadAd(adRequest);

        // Add the libgdx view
        layout.addView(gameView);

        // Add the AdMob view
        RelativeLayout.LayoutParams adParams = 
            new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        layout.addView(inflatedLayout, adParams);

        // Hook it all up
        setContentView(layout);
//        showAds(false);
    }

    public void showAds(boolean show) {
       handler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
    }
}
