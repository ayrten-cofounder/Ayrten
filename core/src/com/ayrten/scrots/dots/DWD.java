package com.ayrten.scrots.dots;

import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class DWD extends Dot
{
	protected int MAX_DOTS = 0;
	
	public DWD(Texture dot, Manager gm, Sound pop)
	{
		super(dot, gm, pop);
	}

	@Override
	public void touchedByAnAngel()	{
		super.touchedByAnAngel();
		generateMoreDots();
	}
	
	public void generateMoreDots() {}
	public void addDWDToStage(Dot dwdDot) {
		gm.getStage().addActor(dwdDot);
	}
}