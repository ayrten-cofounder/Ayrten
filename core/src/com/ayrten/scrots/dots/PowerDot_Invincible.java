package com.ayrten.scrots.dots;

import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

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
	
	@Override
	public void draw(Batch batch, float alpha) {
		batch.draw(dot, getX(), getY(), getWidth(), getHeight());
	}
}
