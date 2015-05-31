package com.ayrten.scrots.dots.power;

import com.ayrten.scrots.dots.Dot;
import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class PowerDot_Decelerate extends PowerDot {
	public static int SLOW_BY = 3;
	
	public PowerDot_Decelerate(Texture dot, Manager gm, Sound pop) {
		super(dot, gm, pop);
		
		ACTIVE_TIME = 7;
		num = Assets.power_dot_manager.getDecelDots();
	}
	
	@Override
	public void beforeAction() {
		super.beforeAction();
		for(Dot dot : gm.curr_level.getDotList())
			if(gm.isPenDot(dot))
				dot.slowDown(SLOW_BY);
	}
	
	@Override
	public void afterAction() {
		super.afterAction();
		for(Dot dot : gm.curr_level.getDotList())
			dot.resetSpeed();
	}
	
	@Override
	public boolean isUnlocked() {
		return true;
	}

}