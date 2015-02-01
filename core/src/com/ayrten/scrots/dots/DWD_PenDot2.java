package com.ayrten.scrots.dots;

import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class DWD_PenDot2 extends DWD {
	private static final float TIME_OFF = (float) -3.0;

	public DWD_PenDot2(Texture dot, Manager gm, Sound pop) {
		super(dot, gm, pop);
		MAX_DOTS = 2;
	}

	@Override
	public void touchedByAnAngel() {
		super.touchedByAnAngel();
		
		if(INVINCIBLE)
		{
			return;
		}

		// Lose time
		gm.addTime(TIME_OFF);
	}
	
	@Override
	public void generateMoreDots() {
		super.generateMoreDots();
		
		for (int i = 0; i < MAX_DOTS; i++) {
			Dot newDot = generator.genPenDot2();
			newDot.setPosition(getX(), getY());
			dots_inside.add(newDot);
		}
	}
}
