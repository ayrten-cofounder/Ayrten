package com.ayrten.scrots.dots;

import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class PenDot1 extends Dot
{
	public PenDot1(Texture dot, Manager gm, Sound pop)
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
		
		// Game is over
		gm.gameOver();
		
		Assets.stats_manager.getPlayerStats().pen_dot_1.popped++;
	}
}
