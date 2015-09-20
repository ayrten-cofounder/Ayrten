package com.ayrten.scrots.dots.power;

import com.ayrten.scrots.common.Assets;
import com.ayrten.scrots.dots.MovingDot;
import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class PowerDot_Decelerate extends PowerDot {
	public static int SLOW_BY = 3;
	
	public PowerDot_Decelerate(Texture dot, Manager gm, Sound pop) {
		super(dot, gm, pop);
		
		ACTIVE_TIME = 7;
	}
	
	@Override
	protected void initializeAssets() {
		num = Assets.power_dot_manager.getDotCount(this.getClass());
		gray_dot_image = new Image(Assets.invincible_dot_gray);
	}
	
	@Override
	public void beforeAction() {
		super.beforeAction();
		for(MovingDot dot : gm.curr_level.getDotList())
			if(gm.isPenDot(dot))
				dot.slowDown(SLOW_BY);
	}
	
	@Override
	public void afterAction() {
		super.afterAction();
		for(MovingDot dot : gm.curr_level.getDotList())
			dot.resetSpeed();
	}
	
	@Override
	public boolean isUnlocked() {
		return true;
	}

}
