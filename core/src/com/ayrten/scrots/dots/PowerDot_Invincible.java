package com.ayrten.scrots.dots;

import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class PowerDot_Invincible extends PowerDot {

	public PowerDot_Invincible(Texture dot, Manager gm, Sound pop) {
		super(dot, gm, pop);
		
		ACTIVE_TIME = 5;
	}
	
	@Override
	public void beforeAction()
	{
		super.beforeAction();
		
		INVINCIBLE = true;
	}
	
	@Override
	public void afterAction() 
	{
		super.afterAction();
		
		INVINCIBLE = false;
	}
}
