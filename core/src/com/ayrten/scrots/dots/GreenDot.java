package com.ayrten.scrots.dots;

import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class GreenDot extends Dot
{
	public GreenDot(Texture dot, Manager gm, Sound pop)
	{
		super(dot, gm, pop);
	}

	@Override
	public void touchedByAnAngel()
	{
		super.touchedByAnAngel();
		
		// Add a point every time it gets popped
		gm.plusOnePoint();
	}
}
