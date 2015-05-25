package com.ayrten.scrots.dots.power;

import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class PowerDot_Invincible extends PowerDot {

	public PowerDot_Invincible(Texture dot, Manager gm, Sound pop) {
		super(dot, gm, pop);
		
		ACTIVE_TIME = 5;
		num = Assets.power_dot_manager.getInvincibleDots();
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
