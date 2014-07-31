package com.ayrten.scrots.dots;

import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.graphics.Texture;

public class RedDot extends Dot
{
	public RedDot(Texture dot, Manager gm)
	{
		super(dot, gm);
	}

	@Override
	public void touchedByAnAngel()
	{
		super.touchedByAnAngel();

		// Game is over
		gm.gameOver();
	}
}
