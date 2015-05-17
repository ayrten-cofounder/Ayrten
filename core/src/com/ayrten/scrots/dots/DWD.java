package com.ayrten.scrots.dots;

import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class DWD extends Dot
{
	protected int MAX_DOTS = 0;
	
	public DotGenerator generator;
//	public ArrayList<Dot> dots_inside = new ArrayList<Dot>(); 
	
	public DWD(Texture dot, Manager gm, Sound pop)
	{
		super(dot, gm, pop);
		
		generator = new DotGenerator(gm);
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