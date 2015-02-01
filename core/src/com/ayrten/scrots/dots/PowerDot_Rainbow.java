package com.ayrten.scrots.dots;

import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class PowerDot_Rainbow extends PowerDot {

	public PowerDot_Rainbow(Texture dot, Manager gm, Sound pop) {
		super(dot, gm, pop);
		
		ACTIVE_TIME = 5;
	}
	
	@Override
	public void beforeAction()
	{
		super.beforeAction();
		
		gm.changePenalityDotVisibility(false);
	}
	
	@Override
	public void afterAction() 
	{
		super.afterAction();
		
		gm.changePenalityDotVisibility(true);
	}
}
