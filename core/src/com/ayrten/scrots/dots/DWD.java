package com.ayrten.scrots.dots;

import java.util.ArrayList;

import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class DWD extends Dot
{
	protected int MAX_DOTS = 0;
	
	public DotGenerator generator;
	public ArrayList<Dot> dots_inside = new ArrayList<Dot>(); 
	
	public DWD(Texture dot, Manager gm, Sound pop)
	{
		super(dot, gm, pop);
		
		generator = new DotGenerator(Assets.width, Assets.game_height, gm);
	}

	@Override
	public void touchedByAnAngel()
	{
		super.touchedByAnAngel();

		generateMoreDots();
		explode();
	}
	
	private void explode()
	{
		if(!dots_inside.isEmpty())
		{
			
			for(Dot dot: dots_inside)
			{
				gm.getStage().addActor(dot);
			}
			
			gm.curr_level.addToDotsThatCameOutOfDWDList(dots_inside);
		}
	}
	
	public void generateMoreDots()
	{
	}
}