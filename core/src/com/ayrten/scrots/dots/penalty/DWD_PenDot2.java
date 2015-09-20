package com.ayrten.scrots.dots.penalty;

import com.ayrten.scrots.common.Assets;
import com.ayrten.scrots.dots.MovingDot;
import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class DWD_PenDot2 extends DWD_PenDot_Base {
	private static final float TIME_OFF = 3.0f;

	public DWD_PenDot2(Texture dot, Manager gm, Sound pop) {
		super(dot, gm, pop);
		MAX_DOTS = 2;
	}
	
	@Override
	public void generateMoreDots() {
		super.generateMoreDots();
		
		for (int i = 0; i < MAX_DOTS; i++) {
			MovingDot newDot = gm.generator.genPenDot2();
			newDot.setPosition(getX(), getY());
			gm.curr_level.getDotList().add(newDot);
			addDWDToStage(newDot);
		}
	}
	
	@Override
	public void executePenalty() {
		super.executePenalty();
		gm.subtractTime(TIME_OFF);
		Assets.stats_manager.getPlayerStats().dwd_pen_dot_2.popped++;
	}
}
