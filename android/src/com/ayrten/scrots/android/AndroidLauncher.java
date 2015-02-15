package com.ayrten.scrots.android;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.BaseGameUtils;

import android.content.Intent;
import android.os.Bundle;

public class AndroidLauncher extends AdLauncher implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener
{
	private GoogleApiClient mGoogleApiClient;
	private static int RC_SIGN_IN = 9001;

	private boolean mResolvingConnectionFailure = false;
	private boolean mAutoStartSignInflow = true;
	private boolean mSignInClicked = false;

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
	
	// Call when the sign-in button is clicked
	private void signInClicked() {
	    mSignInClicked = true;
	    mGoogleApiClient.connect();
	}
	
	// Call when the sign-out button is clicked
	private void signOutclicked() {
	    mSignInClicked = false;
	    Games.signOut(mGoogleApiClient);
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
}
