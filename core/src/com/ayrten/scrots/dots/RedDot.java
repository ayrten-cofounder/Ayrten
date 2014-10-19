package com.ayrten.scrots.dots;

import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class RedDot extends Dot
{
	public RedDot(Texture dot, Manager gm, Sound pop)
	{
		super(dot, gm, pop);
		this.velocity_of_dot = 5;
	}

	@Override
	public void touchedByAnAngel()
	{
		super.touchedByAnAngel();

		// Game is over
		gm.gameOver();
	}
}
