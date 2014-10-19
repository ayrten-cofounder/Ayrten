package com.ayrten.scrots.dots;

import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class BlueDot extends Dot
{
	private static final float TIME_OFF = (float) -10.0;
	
	public BlueDot(Texture dot, Manager gm, Sound pop)
	{
		super(dot, gm, pop);
		this.velocity_of_dot = 2;
		this.speed_of_dot = 2.5f;
	}

	@Override
	public void touchedByAnAngel()
	{
		super.touchedByAnAngel();
		
		// Lose time
		gm.addTime(TIME_OFF);
	}
}
