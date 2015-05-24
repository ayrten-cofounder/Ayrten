package com.ayrten.scrots.dots;

import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class DWD_PenDot1 extends DWD_PenDot_Base {
	private static final float DIVIDER = (float) 0.5;

	public DWD_PenDot1(Texture dot, Manager gm, Sound pop) {
		super(dot, gm, pop);

		MAX_DOTS = 4;
	}

	@Override
	public void generateMoreDots() {
		super.generateMoreDots();

		for (int i = 0; i < MAX_DOTS; i++) {
			Dot newDot = gm.generator.genPenDot1();
			newDot.setPosition(getX(), getY());
			gm.curr_level.getDotList().add(newDot);
			addDWDToStage(newDot);
		}
	}
	
	@Override
	public void executePenalty() {
		super.executePenalty();
		gm.subtractTime(gm.getFloatTime() * DIVIDER);
		Assets.stats_manager.getPlayerStats().dwd_pen_dot_1.popped++;
	}

}
