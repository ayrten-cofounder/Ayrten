package com.ayrten.scrots.dots;

import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class PowerDot_Rainbow extends PowerDot {

	public PowerDot_Rainbow(Texture dot, Manager gm, Sound pop) {
		super(dot, gm, pop);
		
		ACTIVE_TIME = 5;
		num = Assets.power_dot_manager.getRainbowDots();
	}
	
	@Override
	public void beforeAction()
	{
		super.beforeAction();
		
		Assets.power_dot_manager.setRainbowDotAmount(--num);
		updateNumLabel();
		
		gm.setRainbowState(true);
		gm.changePenalityDotVisibility(false);
		Assets.stats_manager.stats.power_dot_rainbow.popped++;
	}
	
	@Override
	public void afterAction() 
	{
		super.afterAction();
		gm.setRainbowState(false);
		gm.changePenalityDotVisibility(true);
	}
	
	@Override
	public void draw(Batch batch, float alpha) {
		batch.draw(dot, getX(), getY(), getWidth(), getHeight());
	}
}
