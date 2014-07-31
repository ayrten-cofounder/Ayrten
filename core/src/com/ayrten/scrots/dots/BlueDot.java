package com.ayrten.scrots.dots;

import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.graphics.Texture;

public class BlueDot extends Dot
{
	public BlueDot(Texture dot, Manager gm)
	{
		super(dot, gm);
	}

	@Override
	public void touchedByAnAngel()
	{
		super.touchedByAnAngel();
		
		// Lose time
	}
	
	
}
