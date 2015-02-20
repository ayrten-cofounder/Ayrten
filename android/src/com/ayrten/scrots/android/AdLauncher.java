package com.ayrten.scrots.android;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.ayrten.scrots.shop.IAP;
import com.ayrten.scrots.shop.IAPInterface;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

@SuppressLint("NewApi")
public class AdLauncher extends AndroidApplication implements AndroidInterface, IAP {
	protected AdView adView;
	private AdRequest adRequest;
	private RelativeLayout layout;
	private YesNoDialog dialog;
	private GameOverDialog gameOverDialog;
	private RelativeLayout.LayoutParams adParams;
	private InAppPurchase iap;

	private final static int SHOW_ADS = 1;
	private final static int HIDE_ADS = 0;
	private final static int SHOW_TOAST = 2;

	private final static int BLACK = 1;
	private final static int WHITE = 0;
	
	protected ScrotsGame scrots;

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
		
		// Create IAP
		iap = new InAppPurchase(this);

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
		
		scrots = new ScrotsGame(this, this);

		// Create the libgdx View
		View gameView = initializeForView(scrots, config);

		LayoutInflater inflater = LayoutInflater.from(this);
		View inflatedLayout = inflater.inflate(R.layout.fragment_ad, null,
				false);

		adView = (AdView) inflatedLayout.findViewById(R.id.adView);

		adRequest = new AdRequest.Builder().build();

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

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		iap.destroy();
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
			final ButtonInterface no_interface, final int color) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				if (color == BLACK) {
					dialog.getWindow().setBackgroundDrawableResource(
							android.R.color.black);
				} else {
					dialog.getWindow().setBackgroundDrawableResource(
							android.R.color.white);
				}
				dialog.setDialog(title, yes_interface, no_interface);
				dialog.show();
			}
		});
	}

	@Override
	public void makeWindow(final String title, final String yes_button,
			final String no_button, final ButtonInterface yes_interface,
			final ButtonInterface no_interface, final int color) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				if (color == BLACK) {
					dialog.getWindow().setBackgroundDrawableResource(
							android.R.color.black);
				} else {
					dialog.getWindow().setBackgroundDrawableResource(
							android.R.color.white);
				}
				dialog.setDialogWithButtonNames(title, yes_button, no_button,
						yes_interface, no_interface);
				dialog.show();
			}
		});
	}

	@Override
	public void makeGameOverDialog(final ButtonInterface yes_interface,
			final ButtonInterface no_interface, final int color) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				if (color == BLACK) {
					gameOverDialog.getWindow().setBackgroundDrawableResource(
							android.R.color.black);
				} else {
					gameOverDialog.getWindow().setBackgroundDrawableResource(
							android.R.color.white);
				}
				gameOverDialog.setDialog(yes_interface, no_interface);
				gameOverDialog.show();
			}
		});
	}

	@Override
	public void makeGameOverDialogHighScore(final GameScreen gameScreen,
			final ButtonInterface yes_interface,
			final ButtonInterface no_interface, final int color) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				if (color == BLACK) {
					gameOverDialog.getWindow().setBackgroundDrawableResource(
							android.R.color.black);
				} else {
					gameOverDialog.getWindow().setBackgroundDrawableResource(
							android.R.color.white);
				}
				gameOverDialog.setHighScoreDialog(gameScreen, yes_interface,
						no_interface);
				gameOverDialog.show();
			}
		});
	}

	@Override
	public void showLeadershipBoard() {
	}

	@Override
	public void showAchievements() {
	}

	@Override
	public void unlockAchievement(String name) {
	}

	@Override
	public void gplay_signin() {}

	@Override
	public void gplay_logout() {}

	@Override
	public boolean is_gplay_signedin() { return true; }
	
	public void purchase(String item, IAPInterface callback)
	{
		iap.purchase(item, callback);
	}

	@Override
	public void consume(String item, IAPInterface callback)
	{
		iap.consumeItem(iap.inventory.getPurchase(item), callback);
	}

	@Override
	public void queryPurchaseItems()
	{
		iap.queryPurchasedItems();
	}

	@Override
	public boolean isConnected()
	{
		return iap.isConnected;
	}

	@Override
	public String getPrice(String item)
	{
		return iap.getPrice(item);
	}
}
