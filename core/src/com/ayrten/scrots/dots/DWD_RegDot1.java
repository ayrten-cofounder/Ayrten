package com.ayrten.scrots.dots;

import java.util.Random;

import com.ayrten.scrots.screens.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class DWD_RegDot1 extends DWD
{
	public DWD_RegDot1(Texture dot, Manager gm, Sound pop)
	{
		super(dot, gm, pop);
		
		MAX_DOTS = 5;
	}

	@Override
	public void touchedByAnAngel()
	{
		super.touchedByAnAngel();

		
	}
	
	@Override
	public void generateMoreDots() {
		super.generateMoreDots();
		
		Random rand = new Random(System.currentTimeMillis());
		int random = rand.nextInt(3) + 3;
		
		for (int i = 0; i < random; i++) {
			if (i > 2)
			{
				if(rand.nextInt(100) % 10 > 0)
				{
					Dot newDot = generator.genRegDot3();
					newDot.setPosition(getX(), getY());
					dots_inside.add(newDot);
					return;
				}
			}
			
			Dot newDot = generator.genRegDot1();
			newDot.setPosition(getX(), getY());
			dots_inside.add(newDot);
			gm.curr_level.addGreenDot();
		}
	}
}