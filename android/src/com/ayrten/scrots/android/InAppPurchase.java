package com.ayrten.scrots.android;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;

import com.ayrten.scrots.android.util.IabHelper;
import com.ayrten.scrots.android.util.IabResult;
import com.ayrten.scrots.android.util.Inventory;
import com.ayrten.scrots.android.util.Purchase;
import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.shop.IAP;
import com.ayrten.scrots.shop.IAPInterface;

public class InAppPurchase {
	private static String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAupC79FGlC6wvZFhHkKXvrbcvh26pYAzPDuCyJUS3ACsOdzAjGTa3jn6QSWhIaQXemviTUWY72Gp3bTK38kG5E7xL9CBAiSwKcaDgoM82TCznJ/myZzwg3wNLVbnBbjlfdY0R5uiNouKPXnc0pZ3I2uwpM6UEsGtYGhIBhMtig9L9PqZWAejjNpYlusAfin6Rad0H/nDVOv6AFLiYTzqoE78d4Rrpa5kx9tEJIdgRJtyX/knm/R4Eb/daFuqEAtt7BGfU7lFrNYOP3zjCAfG22E+4jAvThfNJT9ilE6vQ7YLwJQNF9y2DBF1hIHXfnaTniQ6G4bnB0WaBEqCq9cHCywIDAQAB";
	private static final int REQUEST_CODE = 83197;

	private Activity activity;
	private IabHelper mHelper;
	public Inventory inventory;

	public boolean isConnected = false; // If connected to Google Play for IAP
	public boolean retrievedItems = false; // If items are queried

	public String item_1_price;
	public String item_1_description;

	public String item_2_price;
	public String item_2_description;

	public String item_3_price;
	public String item_3_description;

	public String item_4_price;
	public String item_4_description;

	public String item_5_price;
	public String item_5_description;

	public String no_ads_prics;
	public String no_ads_description;

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

							item_1_price = inventory.getSkuDetails(IAP.ITEM_1)
									.getPrice();
							item_1_description = inventory.getSkuDetails(
									IAP.ITEM_1).getDescription();

							item_2_price = inventory.getSkuDetails(IAP.ITEM_2)
									.getPrice();
							item_2_description = inventory.getSkuDetails(
									IAP.ITEM_2).getDescription();

							item_3_price = inventory.getSkuDetails(IAP.ITEM_3)
									.getPrice();
							item_3_description = inventory.getSkuDetails(
									IAP.ITEM_3).getDescription();

							item_4_price = inventory.getSkuDetails(IAP.ITEM_4)
									.getPrice();
							item_4_description = inventory.getSkuDetails(
									IAP.ITEM_4).getDescription();

							item_5_price = inventory.getSkuDetails(IAP.ITEM_5)
									.getPrice();
							item_5_description = inventory.getSkuDetails(
									IAP.ITEM_5).getDescription();

							no_ads_prics = inventory.getSkuDetails(
									IAP.REMOVE_ADS).getPrice();
							no_ads_description = inventory.getSkuDetails(
									IAP.REMOVE_ADS).getDescription();

							if (inventory.hasPurchase(IAP.ITEM_1)) {
								consumeItem(inventory.getPurchase(IAP.ITEM_1),
										new IAPInterface() {

											@Override
											public void purchaseSuccess() {
											}

											@Override
											public void purchaseFailed() {
											}

											@Override
											public void consumeSuccess() {
												Assets.points_manager.addPoints(Integer
														.valueOf(item_1_description));
											}

											@Override
											public void consumeFailed() {
											}
										});
							}
							if (inventory.hasPurchase(IAP.ITEM_2)) {
								consumeItem(inventory.getPurchase(IAP.ITEM_2),
										new IAPInterface() {

											@Override
											public void purchaseSuccess() {
											}

											@Override
											public void purchaseFailed() {
											}

											@Override
											public void consumeSuccess() {
												Assets.points_manager.addPoints(Integer
														.valueOf(item_2_description));
											}

											@Override
											public void consumeFailed() {
											}
										});
							}
							if (inventory.hasPurchase(IAP.ITEM_3)) {
								consumeItem(inventory.getPurchase(IAP.ITEM_3),
										new IAPInterface() {

											@Override
											public void purchaseSuccess() {
											}

											@Override
											public void purchaseFailed() {
											}

											@Override
											public void consumeSuccess() {
												Assets.points_manager.addPoints(Integer
														.valueOf(item_3_description));
											}

											@Override
											public void consumeFailed() {
											}
										});
							}
							if (inventory.hasPurchase(IAP.ITEM_4)) {
								consumeItem(inventory.getPurchase(IAP.ITEM_4),
										new IAPInterface() {

											@Override
											public void purchaseSuccess() {
											}

											@Override
											public void purchaseFailed() {
											}

											@Override
											public void consumeSuccess() {
												Assets.points_manager.addPoints(Integer
														.valueOf(item_4_description));
											}

											@Override
											public void consumeFailed() {
											}
										});
							}
							if (inventory.hasPurchase(IAP.ITEM_5)) {
								consumeItem(inventory.getPurchase(IAP.ITEM_5),
										new IAPInterface() {

											@Override
											public void purchaseSuccess() {
											}

											@Override
											public void purchaseFailed() {
											}

											@Override
											public void consumeSuccess() {
												Assets.points_manager.addPoints(Integer
														.valueOf(item_5_description));
											}

											@Override
											public void consumeFailed() {
											}
										});
							}
							if (inventory.hasPurchase(IAP.REMOVE_ADS)) {
								Assets.prefs.putBoolean(IAP.REMOVE_ADS, true);
							}

							retrievedItems = true;
						}
					};

					ArrayList<String> additionalSkuList = new ArrayList<String>();
					additionalSkuList.add(IAP.ITEM_1);
					additionalSkuList.add(IAP.ITEM_2);
					additionalSkuList.add(IAP.ITEM_3);
					additionalSkuList.add(IAP.ITEM_4);
					additionalSkuList.add(IAP.ITEM_5);
					additionalSkuList.add(IAP.REMOVE_ADS);
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

					if (purchase.getSku().equals(IAP.REMOVE_ADS))
						return;

					consumeItem(purchase, callback);
				}
			}
		};

		try {
			mHelper.launchPurchaseFlow(activity, item, REQUEST_CODE,
					mPurchaseFinishedListener, "testing");
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
	}

	public void consumeItem(Purchase purchase, final IAPInterface callback) {
		IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
			public void onConsumeFinished(Purchase purchase, IabResult result) {
				if (result.isSuccess()) {
					callback.consumeSuccess();
				} else {
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

				if (result.isFailure()) {

				} else {
					InAppPurchase.this.inventory = inventory;
				}
			}
		};

		mHelper.queryInventoryAsync(mGotInventoryListener);
	}

	public String getPrice(String item) {

		if (item.equals(IAP.ITEM_1)) {
			return item_1_price;
		} else if (item.equals(IAP.ITEM_2)) {
			return item_2_price;
		} else if (item.equals(IAP.ITEM_3)) {
			return item_3_price;
		} else if (item.equals(IAP.ITEM_4)) {
			return item_4_price;
		} else if (item.equals(IAP.ITEM_5)) {
			return item_5_price;
		} else if (item.equals(IAP.REMOVE_ADS)) {
			return no_ads_prics;
		} else {
			return "$0";
		}
	}

	public String getDescription(String item) {
		if (item.equals(IAP.ITEM_1)) {
			return item_1_description;
		} else if (item.equals(IAP.ITEM_2)) {
			return item_2_description;
		} else if (item.equals(IAP.ITEM_3)) {
			return item_3_description;
		} else if (item.equals(IAP.ITEM_4)) {
			return item_4_description;
		} else if (item.equals(IAP.ITEM_5)) {
			return item_5_description;
		} else if (item.equals(IAP.REMOVE_ADS)) {
			return no_ads_description;
		} else {
			return "0";
		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE) {
			mHelper.handleActivityResult(requestCode, resultCode, data);
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
