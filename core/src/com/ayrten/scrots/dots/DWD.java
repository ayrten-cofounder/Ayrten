package com.ayrten.scrots.dots;

import com.ayrten.scrots.screens.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class DWD extends Dot
{
	public DWD(Texture dot, Manager gm, Sound pop)
	{
		super(dot, gm, pop);
	}

	@Override
	public void touchedByAnAngel()
	{
		super.touchedByAnAngel();

		explode();
	}
	
	private void explode()
	{
		System.out.println("Exploded!");
	}
}