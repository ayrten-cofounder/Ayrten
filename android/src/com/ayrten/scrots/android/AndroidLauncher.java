package com.ayrten.scrots.android;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import com.ayrten.scrots.manager.GPlayManager;
import com.badlogic.gdx.utils.Json;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.games.achievement.Achievement;
import com.google.android.gms.games.achievement.AchievementBuffer;
import com.google.android.gms.games.achievement.Achievements;
import com.google.android.gms.games.achievement.Achievements.LoadAchievementsResult;
import com.google.android.gms.games.achievement.Achievements.UpdateAchievementResult;
import com.google.android.gms.internal.gp;
import com.google.example.games.basegameutils.BaseGameUtils;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;

public class AndroidLauncher extends AdLauncher implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener
{
	private GoogleApiClient mGoogleApiClient;
	private static int RC_SIGN_IN = 9001;

	private boolean mResolvingConnectionFailure = false;
	private boolean mAutoStartSignInflow = true;
	private boolean mSignInClicked = false;
	
	// Pointer to ScrotsGame GPlayerManager object.
	private GPlayManager gplay_manager;
	
	protected HashMap<String, Integer> achievement_list;

	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//		initialize(new ScrotsGame(this), config);

		// Create the Google Api Client with access to the Play Game services
		mGoogleApiClient = new GoogleApiClient.Builder(this)
		.addConnectionCallbacks(this)
		.addOnConnectionFailedListener(this)
		.addApi(Games.API).addScope(Games.SCOPE_GAMES)
		// add other APIs and scopes here as needed
		.build();
		
		initializeAchievements();
	}

	@Override
	protected void onStart() {
		super.onStart();
		mGoogleApiClient.connect();
	}

	@Override
	protected void onStop() {
		super.onStop();
		mGoogleApiClient.disconnect();
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
//		if(scrots.main_menu != null)
//			scrots.main_menu.update_gplay_status(false);
		
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
			if (!BaseGameUtils.resolveConnectionFailure(this,
					mGoogleApiClient, result,
					RC_SIGN_IN, this.getResources().getString(R.string.sign_in_failed))) {
				mResolvingConnectionFailure = false;
			}
		}

		// Put code here to display the sign-in button
	}

	@Override
	public void onConnected(Bundle connectionHint) {
//		scrots.main_menu.update_gplay_status(true);
	}

	@Override
	public void onConnectionSuspended(int cause) {
		// Attempt to reconnect
		mGoogleApiClient.connect();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
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
				BaseGameUtils.showActivityResultError(this,
						requestCode, resultCode, R.string.sign_in_failed);
			}
		}	
	}
	
	
	@Override
	public void showAchievements() {
		if(mGoogleApiClient != null && mGoogleApiClient.isConnected())
			startActivityForResult(Games.Achievements.getAchievementsIntent(mGoogleApiClient), 789);
		else
			showToast("Failed to show Achievements: not signed-in!");
	}
	
	@Override
	public void showLeadershipBoard() {
		if(mGoogleApiClient != null && mGoogleApiClient.isConnected())
			startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(mGoogleApiClient), 789);
		else
			showToast("Failed to show Leaderboard: not signed-in!");
	}
	
	@Override
	public void unlockAchievement(final String name) {
		if(mGoogleApiClient != null && mGoogleApiClient.isConnected())
		{
			if(achievement_list.containsKey(name)) {
				PendingResult<Achievements.UpdateAchievementResult> result = Games.Achievements.unlockImmediate(mGoogleApiClient, 
						this.getResources().getString(achievement_list.get(name)));
				result.setResultCallback(new ResultCallback<Achievements.UpdateAchievementResult>() {
					@Override
					public void onResult(UpdateAchievementResult result) {
						if(result.getStatus().getStatusCode() == GamesStatusCodes.STATUS_OK)
	gplay_manager.addPoints(name);
					}
				});
			}
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
		scrots.main_menu.update_gplay_status(false);
	}
	
	@Override
	public boolean loadAchievements(HashMap<String, Boolean> map) {
		 boolean fullLoad = false;  // set to 'true' to reload all achievements (ignoring cache)
	     long waitTime = 60;    // seconds to wait for achievements to load before timing out

	     // load achievements
	     PendingResult<LoadAchievementsResult> p = Games.Achievements.load( mGoogleApiClient, fullLoad );
	     Achievements.LoadAchievementsResult r = (Achievements.LoadAchievementsResult) p.await(waitTime, TimeUnit.SECONDS);
	     int status = r.getStatus().getStatusCode();
	     if ( status != GamesStatusCodes.STATUS_OK )  {
	        r.release();
	        return false;           // Error Occured
	     }
	     
	     HashMap<String, String> conversion_map = new HashMap<String, String>();
	     Iterator<Entry<String, Integer>> iter = achievement_list.entrySet().iterator();
	     while(iter.hasNext()) {
	    	 Map.Entry<String, Integer> pair = (Entry<String, Integer>) iter.next();
	    	 String id = this.getResources().getString(pair.getValue());
	    	 String alias = pair.getKey();
	    	 conversion_map.put(id, alias);
	     }

	     // cache the loaded achievements
	     AchievementBuffer buf = r.getAchievements();
	     int bufSize = buf.getCount();
	     for ( int i = 0; i < bufSize; i++ )  {
	        Achievement ach = buf.get( i );

	        String id = ach.getAchievementId();  
	        boolean unlocked = ach.getState() == Achievement.STATE_UNLOCKED;
	        String alias = conversion_map.get(id);
	        System.out.println("GPLAY ALIAS: " + alias);
	        System.out.println("GPLAY UNLCOKED: " + unlocked);
	        map.put(alias, unlocked);
	     }
	     buf.close();
	     r.release();
		
		return true;
	}
	
	@Override
	public void setGPlayManager(GPlayManager manager) {
		gplay_manager = manager;
	}
	
	private void initializeAchievements()
	{
		achievement_list = new HashMap<String, Integer>();
		achievement_list.put("popped_1000_dots", R.string.popped_1000_dots);
		achievement_list.put("popped_5000_dots", R.string.popped_5000_dots);
		achievement_list.put("popped_10000_dots", R.string.popped_10000_dots);
		achievement_list.put("popped_25000_dots", R.string.popped_25000_dots);
		achievement_list.put("popped_50000_dots", R.string.popped_50000_dots);
		achievement_list.put("popped_100000_dots", R.string.popped_100000_dots);
		
		achievement_list.put("unlock_rainbow", R.string.unlocked_rainbow);
		achievement_list.put("unlock_invincible", R.string.unlocked_invinvincible);
		achievement_list.put("unlock_magnet", R.string.unlocked_magnet);
	
		achievement_list.put("passed_lvl5", R.string.passed_lvl5);
		achievement_list.put("passed_lvl10", R.string.passed_lvl10);
		achievement_list.put("passed_lvl15", R.string.passed_lvl15);
	}
}
