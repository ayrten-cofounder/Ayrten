package com.ayrten.scrots.dots.penalty;

import com.ayrten.scrots.common.Assets;
import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class PenDot1 extends PenDot_Base
{
	public PenDot1(Texture dot, Manager gm, Sound pop)
	{
		super(dot, gm, pop);
	}
	
	@Override
	public void executePenalty() {
		super.executePenalty();
		gm.gameOver();
		Assets.stats_manager.getPlayerStats().pen_dot_1.popped++;
	}
}
