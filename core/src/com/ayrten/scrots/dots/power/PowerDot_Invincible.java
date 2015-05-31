package com.ayrten.scrots.dots.power;


import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class PowerDot_Invincible extends PowerDot {

	public PowerDot_Invincible(Texture dot, Manager gm, Sound pop) {
		super(dot, gm, pop);
		
		ACTIVE_TIME = 5;
	}

	@Override
	protected void initializeAssets() {
		num = Assets.power_dot_manager.getInvincibleDots();
		gray_dot_image = new Image(Assets.invincible_dot_gray);
	}
	
	@Override
	public void beforeAction()
	{
		super.beforeAction();
		
		Assets.power_dot_manager.setInvincibleDotAmount(--num);
		updateNumLabel();
		gm.setInvincible(true);
		Assets.stats_manager.getPlayerStats().power_dot_invincible.popped++;
	}
	
	
	@Override
	public void afterAction() 
	{
		super.afterAction();
		gm.setInvincible(false);
	}
	
	@Override
	public boolean isUnlocked() {
		return (Assets.power_dot_manager.isInvincibleDotUnlocked());
	}
}
