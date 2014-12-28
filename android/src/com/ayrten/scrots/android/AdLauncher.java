package com.ayrten.scrots.android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ayrten.scrots.manager.AndroidInterface;
import com.ayrten.scrots.manager.ButtonInterface;
import com.ayrten.scrots.screens.GameScreen;
import com.ayrten.scrots.screens.ScrotsGame;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

@SuppressLint("NewApi")
public class AdLauncher extends AndroidApplication implements AndroidInterface {
	protected AdView adView;
	private AdRequest adRequest;
	private RelativeLayout layout;
	private YesNoDialog dialog;
	private GameOverDialog gameOverDialog;
	private RelativeLayout.LayoutParams adParams;

	private final static int SHOW_ADS = 1;
	private final static int HIDE_ADS = 0;
	private final static int SHOW_TOAST = 2;

	protected Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SHOW_ADS: {
				showAd();
				break;
			}
			case HIDE_ADS: {
				hideAd();
				break;
			}
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		FontsOverride.setDefaultFont(this, "MONOSPACE",
				"fonts/summer_of_love.ttf");

		// Create the layout
		layout = new RelativeLayout(this);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = false;
		config.useCompass = false;
		config.useImmersiveMode = true;
		config.hideStatusBar = true;

		// Dialog
		dialog = new YesNoDialog(this);
		AdView dialog_adView = (AdView) dialog.findViewById(R.id.adView);

		// Game Over Dialog
		gameOverDialog = new GameOverDialog(this);
		AdView gameOver_dialog_adView = (AdView) gameOverDialog
				.findViewById(R.id.adView);

		// Create the libgdx View
		View gameView = initializeForView(new ScrotsGame(this), config);

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
		adParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

		layout.addView(inflatedLayout, adParams);

		adView.loadAd(adRequest);
		dialog_adView.loadAd(adRequest);
		gameOver_dialog_adView.loadAd(adRequest);

		// Hook it all up
		setContentView(layout);
		shouldShowAd(false);
	}

	private void showAd() {
		adView.resume();
		adView.loadAd(adRequest);
		adView.setVisibility(View.VISIBLE);
	}

	private void hideAd() {
		adView.pause();
		adView.setVisibility(View.GONE);
	}

	public void shouldShowAd(boolean show) {
		System.out.println(handler);
		handler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
	}

	@Override
	public void showToast(final String msg) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT)
						.show();
			}
		});
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public void copyTextToClipboard(final String text) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				int sdk = android.os.Build.VERSION.SDK_INT;
				if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
					android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
					clipboard.setText("text to clip");
				} else {
					android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
					android.content.ClipData clip = android.content.ClipData
							.newPlainText(text, text);
					clipboard.setPrimaryClip(clip);
				}
			}
		});
	}

	@Override
	public void makeYesNoWindow(final String title,
			final ButtonInterface yes_interface,
			final ButtonInterface no_interface) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				dialog.setDialog(title, yes_interface, no_interface);
				dialog.show();
			}
		});
	}

	public void makeWindow(final String title, final String yes_button,
			final String no_button, final ButtonInterface yes_interface,
			final ButtonInterface no_interface) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				dialog.setDialogWithButtonNames(title, yes_button, no_button,
						yes_interface, no_interface);
				dialog.show();
			}
		});
	}

	public void makeGameOverDialog(final ButtonInterface yes_interface,
			final ButtonInterface no_interface) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				gameOverDialog.setDialog(yes_interface, no_interface);
				gameOverDialog.show();
			}
		});
	}

	@Override
	public void makeGameOverDialogHighScore(final GameScreen gameScreen,
			final ButtonInterface yes_interface,
			final ButtonInterface no_interface) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				gameOverDialog.setHighScoreDialog(gameScreen, yes_interface,
						no_interface);
				gameOverDialog.show();
			}
		});
	}
}
