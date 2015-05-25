package com.ayrten.scrots.dots.penalty;

import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class PenDot2 extends PenDot_Base
{
	public static final float TIME_OFF = 5.0f;

	public PenDot2(Texture dot, Manager gm, Sound pop)
	{
		super(dot, gm, pop);
	}
	
	@Override
	public void executePenalty() {
		super.executePenalty();
		gm.subtractTime(TIME_OFF);
		Assets.stats_manager.getPlayerStats().pen_dot_2.popped++;
	}
}
