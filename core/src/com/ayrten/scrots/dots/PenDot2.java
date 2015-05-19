package com.ayrten.scrots.dots;

import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class PenDot2 extends Dot
{
	public static final float TIME_OFF = 5.0f;

	public PenDot2(Texture dot, Manager gm, Sound pop)
	{
		super(dot, gm, pop);
	}

	@Override
	public void touchedByAnAngel()
	{
		super.touchedByAnAngel();
		
		if(gm.isInvincible())
			return;
		
		// Lose time
		gm.subtractTime(TIME_OFF);
		Assets.stats_manager.getPlayerStats().pen_dot_2.popped++;
	}
}
