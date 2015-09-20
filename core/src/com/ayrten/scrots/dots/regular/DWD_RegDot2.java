package com.ayrten.scrots.dots.regular;

import com.ayrten.scrots.common.Assets;
import com.ayrten.scrots.dots.DWD;
import com.ayrten.scrots.dots.MovingDot;
import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class DWD_RegDot2 extends DWD
{
	private static final float TIME_ADD = (float) 1.1; // seconds
	
	public DWD_RegDot2(Texture dot, Manager gm, Sound pop)
	{
		super(dot, gm, pop);
		
		MAX_DOTS = 2;
	}

	@Override
	public void touchedByAnAngel(InputEvent event) {
		super.touchedByAnAngel(event);
		gm.addTime(TIME_ADD);
		Assets.stats_manager.getPlayerStats().dwd_reg_dot_2.popped++;
	}
	
	@Override
	public void generateMoreDots() {
		super.generateMoreDots();
		
		for (int i = 0; i < MAX_DOTS; i++) {
			MovingDot newDot = gm.generator.genRegDot2();
			newDot.setPosition(getX(), getY());
			gm.curr_level.getDotList().add(newDot);
			addDWDToStage(newDot);
		}
	}
}
