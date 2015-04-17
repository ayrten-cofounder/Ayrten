package com.ayrten.scrots.android;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ayrten.scrots.manager.AndroidInterface;
import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.manager.GPlayManager;
import com.ayrten.scrots.screens.ScrotsGame;
import com.ayrten.scrots.shop.IAP;
import com.ayrten.scrots.shop.IAPInterface;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.BaseGameUtils;

@SuppressLint("NewApi")
public class AdLauncher extends AndroidApplication implements AndroidInterface,
		IAP, GoogleApiClient.ConnectionCallbacks,
		GoogleApiClient.OnConnectionFailedListener {
	protected AdView adView;
	private AdRequest adRequest;
	private RelativeLayout layout;
	private RelativeLayout.LayoutParams adParams;
	private InAppPurchase iap;

	private final static int SHOW_ADS = 1;
	private final static int HIDE_ADS = 0;
	// private final static int SHOW_TOAST = 2;

	private GoogleApiClient mGoogleApiClient;
	private static int RC_SIGN_IN = 9001;

	private boolean mResolvingConnectionFailure = false;
	private boolean mAutoStartSignInflow = true;
	private boolean mSignInClicked = false;

	protected HashMap<String, Integer> achievement_list;

	// Pointer to ScrotsGame objects.
	private Label gplay_status;

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

		ScrotsGame scrots = new ScrotsGame(this, this);

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

		// Hook it all up
		setContentView(layout);
		shouldShowAd(false);

		// Create the Google Api Client with access to the Play Game services
		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this).addApi(Games.API)
				.addScope(Games.SCOPE_GAMES)
				// add other APIs and scopes here as needed
				.build();

		initializeAchievements();
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
		if (Assets.prefs.getBoolean(Assets.PREFS_NO_ADS, false))
			return;
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

	public void purchase(String item, IAPInterface callback) {
		iap.purchase(item, callback);
	}

	@Override
	public void consume(String item, IAPInterface callback) {
		iap.consumeItem(iap.inventory.getPurchase(item), callback);
	}

	@Override
	public void queryPurchaseItems() {
		iap.queryPurchasedItems();
	}

	@Override
	public boolean isConnected() {
		return iap.isConnected;
	}

	@Override
	public boolean retrievedItems() {
		return iap.retrievedItems;
	}

	@Override
	public String getDescription(String item) {
		return iap.getDescription(item);
	}

	@Override
	public String getPrice(String item) {
		return iap.getPrice(item);
	}

	@Override
	public float getAppVersion() {
		PackageInfo info = null;
		try {
			info = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return (Float.parseFloat(info.versionName));
	}

	@Override
	protected void onStop() {
		super.onStop();
		mGoogleApiClient.disconnect();
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		// if(scrots.main_menu != null)
		// scrots.main_menu.update_gplay_status(false);
		if (gplay_status != null)
			gplay_status.setText("Sign in");

		if (mResolvingConnectionFailure) {
			// already resolving
			return;
		}

		// if the sign-in button was clicked or if auto sign-in is enabled,
		// launch the sign-in flow
		if (mSignInClicked || mAutoStartSignInflow) {
			mAutoStartSignInflow = false;
			mSignInClicked = false;
			mResolvingConnectionFailure = true;

			// Attempt to resolve the connection failure using BaseGameUtils.
			// The R.string.signin_other_error value should reference a generic
			// error string in your strings.xml file, such as "There was
			// an issue with sign-in, please try again later."
			if (!BaseGameUtils.resolveConnectionFailure(this, mGoogleApiClient,
					result, RC_SIGN_IN,
					this.getResources().getString(R.string.sign_in_failed))) {
				mResolvingConnectionFailure = false;
			}
		}

		// Put code here to display the sign-in button
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		// scrots.main_menu.update_gplay_status(true);
		if (gplay_status != null)
			gplay_status.setText("Logout");
	}

	@Override
	public void onConnectionSuspended(int cause) {
		// Attempt to reconnect
		mGoogleApiClient.connect();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		iap.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RC_SIGN_IN) {
			mSignInClicked = false;
			mResolvingConnectionFailure = false;
			if (resultCode == RESULT_OK) {
				mGoogleApiClient.connect();
			} else {
				// Bring up an error dialog to alert the user that sign-in
				// failed. The R.string.signin_failure should reference an error
				// string in your strings.xml file that tells the user they
				// could not be signed in, such as "Unable to sign in."
				BaseGameUtils.showActivityResultError(this, requestCode,
						resultCode, R.string.sign_in_failed);
			}
		}
	}

	@Override
	public void showAchievements() {
		if (mGoogleApiClient != null && mGoogleApiClient.isConnected())
			startActivityForResult(
					Games.Achievements.getAchievementsIntent(mGoogleApiClient),
					789);
		else
			showToast("Failed to show Achievements: not signed-in!");
	}

	@Override
	public void showLeaderboard(Assets.LeaderboardType lb_type) {
		if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
			// startActivityForResult(
			// Games.Leaderboards
			// .getAllLeaderboardsIntent(mGoogleApiClient),
			// 789);
			if (lb_type == Assets.LeaderboardType.TIME)
				startActivityForResult(Games.Leaderboards.getLeaderboardIntent(
						mGoogleApiClient,
						this.getResources()
								.getString(R.string.leaderboard_time)), 789);
			else if (lb_type == Assets.LeaderboardType.SURVIVAL)
				startActivityForResult(Games.Leaderboards.getLeaderboardIntent(
						mGoogleApiClient,
						this.getResources().getString(
								R.string.leaderboard_survival)), 789);
		} else
			showToast("Failed to show Leaderboard: not signed-in!");
	}

	@Override
	public void unlockAchievement(String name) {
		if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
			if (achievement_list.containsKey(name))
				Games.Achievements.unlock(mGoogleApiClient, this.getResources()
						.getString(achievement_list.get(name)));
		}
	}

	@Override
	public boolean is_gplay_signedin() {
		return (mGoogleApiClient != null && mGoogleApiClient.isConnected());
	}

	@Override
	public void gplay_signin() {
		mSignInClicked = true;
		mGoogleApiClient.connect();
	}

	@Override
	public void gplay_logout() {
		mSignInClicked = false;
		Games.signOut(mGoogleApiClient);
		mGoogleApiClient.disconnect();
		gplay_status.setText("Sign in");
		showToast("Logged out successfully!");
	}

	private void initializeAchievements() {
		achievement_list = new HashMap<String, Integer>();
		achievement_list.put("popped_1000_dots", R.string.popped_1000_dots);
		achievement_list.put("popped_5000_dots", R.string.popped_5000_dots);
		achievement_list.put("popped_10000_dots", R.string.popped_10000_dots);
		achievement_list.put("popped_25000_dots", R.string.popped_25000_dots);
		achievement_list.put("popped_50000_dots", R.string.popped_50000_dots);
		achievement_list.put("popped_100000_dots", R.string.popped_100000_dots);

		achievement_list.put("unlock_rainbow", R.string.unlocked_rainbow);
		achievement_list.put("unlock_invincible",
				R.string.unlocked_invinvincible);
		achievement_list.put("unlock_magnet", R.string.unlocked_magnet);

		achievement_list.put("passed_lvl5", R.string.passed_lvl5);
		achievement_list.put("passed_lvl10", R.string.passed_lvl10);
		achievement_list.put("passed_lvl15", R.string.passed_lvl15);
	}

	@Override
	public boolean loadAchievements(HashMap<String, Boolean> map) {
		return false;
	}

	@Override
	public void setGPlayManager(GPlayManager manager) {
	}

	@Override
	public void setGPlayButton(Label button) {
		gplay_status = button;
	}

	@Override
	public void submitLeaderboardScore(Assets.LeaderboardType lb_type,
			long score) {
		if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
			if (lb_type == Assets.LeaderboardType.TIME)
				Games.Leaderboards.submitScore(mGoogleApiClient, this
						.getResources().getString(R.string.leaderboard_time),
						score);
			else if (lb_type == Assets.LeaderboardType.SURVIVAL) {
			}
		}
	}
}
