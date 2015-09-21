package com.ayrten.scrots.dots.power;

import com.ayrten.scrots.common.Assets;
import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class PowerDot_Rainbow extends PowerDot {

	public PowerDot_Rainbow(Texture dot, Manager gm, Sound pop) {
		super(dot, gm, pop);
		
		ACTIVE_TIME = 5;
	}
	
	@Override
	protected void initializeAssets() {
		num = Assets.item_manager.getItemCount(this.getClass().getSimpleName());
		gray_dot_image = new Image(Assets.rainbow_dot_gray);
	}
	
	
	@Override
	public void beforeAction() {
		super.beforeAction();
		
		gm.setRainbowState(true);
		gm.changePenalityDotVisibility(false);
		Assets.stats_manager.getPlayerStats().power_dot_rainbow.popped++;
	}
	
	@Override
	public void afterAction() {
		super.afterAction();
		gm.setRainbowState(false);
		gm.changePenalityDotVisibility(true);
	}
	
	@Override
	public boolean isUnlocked() {
		return (Assets.item_manager.isItemUnlocked(this.getClass().getSimpleName()));
	}
}
