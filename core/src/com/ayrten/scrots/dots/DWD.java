package com.ayrten.scrots.dots;

import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class DWD extends MovingDot {
	protected int MAX_DOTS = 0;
	
	public DWD(Texture dot, Manager gm, Sound pop)
	{
		super(dot, gm, pop);
	}

	@Override
	public void touchedByAnAngel(InputEvent event)	{
		super.touchedByAnAngel(event);
		generateMoreDots();
	}
	
	public void generateMoreDots() {}
	public void addDWDToStage(Dot dwdDot) {
		gm.getStage().addActor(dwdDot);
	}
}