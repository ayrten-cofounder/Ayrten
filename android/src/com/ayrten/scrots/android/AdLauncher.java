package com.ayrten.scrots.android;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ayrten.scrots.screens.AndroidInterface;
import com.ayrten.scrots.screens.ScrotsGame;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class AdLauncher extends AndroidApplication implements AndroidInterface 
{
	protected AdView adView;
	private AdRequest adRequest;
	private RelativeLayout layout;

	private final static int SHOW_ADS = 1;
	private final static int HIDE_ADS = 0;

	protected Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			System.out.println("handler");

			switch (msg.what) {
			case SHOW_ADS: {
				show();
				break;
			}
			case HIDE_ADS: {
				hide();
				break;
			}
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Create the layout
		layout = new RelativeLayout(this);

		// Do the stuff that initialize() would do for you
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(
				WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		// Create the libgdx View
		View gameView = initializeForView(new ScrotsGame(this));

		LayoutInflater inflater = LayoutInflater.from(this);
		View inflatedLayout = inflater.inflate(R.layout.fragment_ad, null,
				false);

		adView = (AdView) inflatedLayout.findViewById(R.id.adView); // Put in
																	// your
																	// secret
																	// key here

		adRequest = new AdRequest.Builder().addTestDevice("ABCDEF012345")
				.build();

		// Add the libgdx view
		layout.addView(gameView);

		// Add the AdMob view
		RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

		layout.addView(inflatedLayout, adParams);

		adView.loadAd(adRequest);

		// Hook it all up
		setContentView(layout);
		showAds(false);
	}

	private void show() {
		adView.resume();
		adView.loadAd(adRequest);
		adView.setVisibility(View.VISIBLE);
	}

	private void hide() {
		adView.pause();
		adView.setVisibility(View.GONE);
	}

	public void showAds(boolean show) {
		System.out.println(handler);
		handler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
	}

	@Override
	public void showToast(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
		System.out.println("blah");
	}
}
