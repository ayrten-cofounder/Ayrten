package com.ayrten.scrots.android;

import java.util.ArrayList;

import android.app.Activity;

import com.ayrten.scrots.android.util.IabHelper;
import com.ayrten.scrots.android.util.IabResult;
import com.ayrten.scrots.android.util.Inventory;
import com.ayrten.scrots.android.util.Purchase;
import com.ayrten.scrots.shop.IAP;
import com.ayrten.scrots.shop.IAPInterface;

public class InAppPurchase {
	private static String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAupC79FGlC6wvZFhHkKXvrbcvh26pYAzPDuCyJUS3ACsOdzAjGTa3jn6QSWhIaQXemviTUWY72Gp3bTK38kG5E7xL9CBAiSwKcaDgoM82TCznJ/myZzwg3wNLVbnBbjlfdY0R5uiNouKPXnc0pZ3I2uwpM6UEsGtYGhIBhMtig9L9PqZWAejjNpYlusAfin6Rad0H/nDVOv6AFLiYTzqoE78d4Rrpa5kx9tEJIdgRJtyX/knm/R4Eb/daFuqEAtt7BGfU7lFrNYOP3zjCAfG22E+4jAvThfNJT9ilE6vQ7YLwJQNF9y2DBF1hIHXfnaTniQ6G4bnB0WaBEqCq9cHCywIDAQAB";
	private static final int REQUEST_CODE = 83197;

	private static final String ITEM_1 = IAP.ITEM_1;

	private Activity activity;
	private IabHelper mHelper;
	public Inventory inventory;

	public boolean isConnected = false; // If connected to Google Play for IAP

	public String item_1_price;

	public InAppPurchase(Activity activity) {
		this.activity = activity;

		mHelper = new IabHelper(activity, base64EncodedPublicKey);

		connect();
	}

	// Connect to Google Play
	private void connect() {
		mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
			public void onIabSetupFinished(IabResult result) {
				if (!result.isSuccess()) {
					isConnected = false;
				} else {
					isConnected = true;

					// Retrieve prices of items sold
					IabHelper.QueryInventoryFinishedListener mQueryFinishedListener = new IabHelper.QueryInventoryFinishedListener() {
						public void onQueryInventoryFinished(IabResult result,
								Inventory inventory) {
							if (result.isFailure()) {
								return;
							}

							item_1_price = inventory.getSkuDetails(ITEM_1)
									.getPrice();
						}
					};

					ArrayList<String> additionalSkuList = new ArrayList<String>();
					additionalSkuList.add(ITEM_1);
					mHelper.queryInventoryAsync(true, additionalSkuList,
							mQueryFinishedListener);
				}
			}
		});
	}

	public void purchase(final String item, final IAPInterface callback) {
		IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
			public void onIabPurchaseFinished(IabResult result,
					Purchase purchase) {
				if (result.isFailure()) {
					callback.purchaseFailed();
					return;
				} else if (purchase.getSku().equals(item)) {
					// consume the gas and update the UI
					callback.purchaseSuccess();
					
					consumeItem(purchase, callback);
				}
			}
		};

		mHelper.launchPurchaseFlow(activity, item, REQUEST_CODE,
				mPurchaseFinishedListener, base64EncodedPublicKey);
	}

	public void consumeItem(Purchase purchase, final IAPInterface callback) {
		IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
			public void onConsumeFinished(Purchase purchase, IabResult result) {
				if (result.isSuccess())
				{
					callback.consumeSuccess();
				} 
				else
				{
					callback.consumeFailed();
				}
			}
		};

		mHelper.consumeAsync(purchase, mConsumeFinishedListener);
	}

	public void queryPurchasedItems() {
		IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
			public void onQueryInventoryFinished(IabResult result,
					Inventory inventory) {

				if (result.isFailure())
				{

				} 
				else
				{
					InAppPurchase.this.inventory = inventory;
				}
			}
		};

		mHelper.queryInventoryAsync(mGotInventoryListener);
	}
	
	public String getPrice(String item)
	{
		if(item.equals(ITEM_1))
		{
			return item_1_price;
		}
		else
		{
			return "$0";
		}
	}

	// Used on Activity's destroy method
	public void destroy() {
		if (mHelper != null) {
			mHelper.dispose();
		}
		mHelper = null;
	}
}
