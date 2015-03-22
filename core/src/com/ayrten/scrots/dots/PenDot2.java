package com.ayrten.scrots.dots;

import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class PenDot2 extends Dot
{
	public PenDot2(Texture dot, Manager gm, Sound pop)
	{
		super(dot, gm, pop);
	}

	@Override
	public void touchedByAnAngel()
	{
		super.touchedByAnAngel();
		
		if(INVINCIBLE)
		{
			return;
		}
		
		// Lose time
		gm.subtractTime(Assets.TIME_OFF);
		Assets.stats_manager.getPlayerStats().pen_dot_2.popped++;
	}
}
