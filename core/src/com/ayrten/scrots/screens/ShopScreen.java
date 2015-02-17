package com.ayrten.scrots.screens;

import java.util.ArrayList;

import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.manager.ButtonInterface;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class ShopScreen extends ScrotsScreen {
	private int points = 0;
	private Table table;
	private Label points_label;
	
	private enum DOT_TYPE {
		MAGNET(150), 
		INVINCIBLE(250), 
		RAINBOW(350);
		
		private int price = 0;
		
		private DOT_TYPE(int p) {
			price = p;
		}
		
		protected int price() {
			return price;
		}
	}

	private class Dot {
		private Image dotImage;
		private Label priceLabel;
		private Label  buyLabel ;
		private DOT_TYPE dotType;
		
		protected Dot(DOT_TYPE dotType) {
			this.dotType = dotType;
			priceLabel = new Label(
					String.valueOf(dotType.price()),
					Assets.prefs.getString("bg_color").equals("Black") ? Assets.style_font_32_white : Assets.style_font_32_black);
			buyLabel = new Label(
					"Buy",
					Assets.prefs.getString("bg_color").equals("Black") ? Assets.style_font_32_white : Assets.style_font_32_black);
			
			switch(dotType) {
				case INVINCIBLE:
					dotImage = new Image(Assets.invincible_dot);
					break;
				
				case MAGNET:
					dotImage = new Image(Assets.magnet_dot);
					break;
				
				case RAINBOW:
					dotImage = new Image(Assets.rainbow_dot);
					break;
			}
		}
		
		protected void buyDot(int quantity) {
			int cost = dotType.price() * quantity;
			
			if (points < cost) {
				notEnoughPoints();
				
			} else {
				
				Assets.points_manager.addPoints(-(cost));
				
				switch(dotType) {
					case INVINCIBLE:
						Assets.power_dot_manager
							.setInvincibleDotAmount(Assets.power_dot_manager
							.getInvincibleDots() + quantity);
						break;
					case MAGNET:
						Assets.power_dot_manager
							.setMagnetDotAmount(Assets.power_dot_manager
							.getMagnetDots() + quantity);
						break;
					case RAINBOW:
						Assets.power_dot_manager
							.setRainbowDotAmount(Assets.power_dot_manager
							.getRainbowDots() + quantity);
						break;
				}
				
				updatePoints();
				
			}
		}
		
		protected Image getDotImage() { return dotImage; }
		protected Label getPriceLabel() { return priceLabel; }
		protected Label getBuyLabel() { return buyLabel; }
	}

	public ShopScreen(Screen bscreen) {
		super(bscreen, true);

		table = new Table();
		table.setFillParent(true);
		table.setSkin(Assets.skin);

		points = Assets.points_manager.getTotalPoints();

		setupStage();
		setUpShopTable();
		updatePoints();
	}

	private void setBackgroundWindow() {
		MessageScreen message = new MessageScreen(stage);

		message.makeWindow("You do not have enough points. Do you want to purchase more?", "Yes", "No",
				new ButtonInterface() {

					@Override
					public void buttonPressed() {
						// User clicked yes
						// Go to purchase shop
					}
				}, new ButtonInterface() {

					@Override
					public void buttonPressed() {
						// User clicked no
					}
				});
	}

	private void updatePoints() {
		points = Assets.points_manager.getTotalPoints();
		points_label.setText("Points: " + String.valueOf(points));
	}

	private void setUpShopTable() {
		final Dot invincibleDot = new Dot(DOT_TYPE.INVINCIBLE);
		final Dot rainbowDot = new Dot(DOT_TYPE.RAINBOW);
		final Dot magnetDot = new Dot(DOT_TYPE.MAGNET);
		
		ArrayList<Dot> dots = new ArrayList<Dot>(); 
		dots.add(invincibleDot);
		dots.add(rainbowDot);
		dots.add(magnetDot);
		
		// Total Points Label
		points_label = new Label(
				"Points: " + String.valueOf(points),
				Assets.prefs.getString("bg_color").equals("Black") ? Assets.style_font_32_white : Assets.style_font_32_black);

		// Event Listeners
		for(final Dot d : dots) {
			d.getBuyLabel().addListener(new InputListener() {
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					return true;
				}

				public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
					d.buyDot(1);
				}
			});
		}
		
		// Construct Table
		table.add(points_label);
		
		for(Dot d : dots) {
			table.row();
			table.add(d.getDotImage());
			table.add(d.getPriceLabel());
			table.add(d.getBuyLabel());
		}

		stage.addActor(table);
	}

	private void notEnoughPoints() {
		setBackgroundWindow();
	}

	@Override
	public void show() {
		super.show();
		updatePoints();
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		super.render(delta);
	}
	
	
}
