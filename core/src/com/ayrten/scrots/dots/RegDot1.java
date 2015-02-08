package com.ayrten.scrots.dots;

import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class RegDot1 extends Dot
{
	public RegDot1(Texture dot, Manager gm, Sound pop)
	{
		super(dot, gm, pop);
	}

	@Override
	public void touchedByAnAngel()
	{
		super.touchedByAnAngel();
		
		// Removes a green dot from the array
		// Then Adds a point
		gm.minusGreenDot();
		Assets.points_manager.addEarnedPoints(1);
	}
}
