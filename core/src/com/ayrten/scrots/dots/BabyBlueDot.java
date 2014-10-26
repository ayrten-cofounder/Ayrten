package com.ayrten.scrots.dots;

import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class BabyBlueDot extends Dot
{
	private static final float TIME_ADD = (float) 1.1; // seconds
	
	public BabyBlueDot(Texture dot, Manager gm, Sound pop)
	{
		super(dot, gm, pop);
		this.move.speed_of_dot = 3f;
	}

	@Override
	public void touchedByAnAngel()
	{
		super.touchedByAnAngel();
		
		// Lose time
		gm.addTime(TIME_ADD);
	}
}