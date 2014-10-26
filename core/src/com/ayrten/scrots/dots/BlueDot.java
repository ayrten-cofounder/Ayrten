package com.ayrten.scrots.dots;

import com.ayrten.scrots.screens.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class BlueDot extends Dot
{
	private static final float TIME_OFF = (float) -10.0;
	
	public BlueDot(Texture dot, Manager gm, Sound pop)
	{
		super(dot, gm, pop);
	}

	@Override
	public void touchedByAnAngel()
	{
		super.touchedByAnAngel();
		
		// Lose time
		gm.addTime(TIME_OFF);
	}
}
