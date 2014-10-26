package com.ayrten.scrots.dots;

import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class GreenDot extends Dot
{
	public GreenDot(Texture dot, Manager gm, Sound pop)
	{
		super(dot, gm, pop);
		this.move.speed_of_dot = 2f;
	}

	@Override
	public void touchedByAnAngel()
	{
		super.touchedByAnAngel();
		
		// Removes a green dot from the array
		gm.minusGreenDot();
	}
}
