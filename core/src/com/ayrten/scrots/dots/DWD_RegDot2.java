package com.ayrten.scrots.dots;

import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class DWD_RegDot2 extends DWD
{
	private static final float TIME_ADD = (float) 1.1; // seconds
	
	public DWD_RegDot2(Texture dot, Manager gm, Sound pop)
	{
		super(dot, gm, pop);
		
		MAX_DOTS = 2;
	}

	@Override
	public void touchedByAnAngel()
	{
		super.touchedByAnAngel();

		gm.addTime(TIME_ADD);
		Assets.stats_manager.getPlayerStats().dwd_reg_dot_2.popped++;
	}
	
	@Override
	public void generateMoreDots() {
		super.generateMoreDots();
		
		for (int i = 0; i < MAX_DOTS; i++) {
			Dot newDot = generator.genRegDot2();
			newDot.setPosition(getX(), getY());
			dots_inside.add(newDot);
		}
	}
}
