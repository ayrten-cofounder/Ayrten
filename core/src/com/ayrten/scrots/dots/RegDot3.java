package com.ayrten.scrots.dots;

import java.util.ArrayList;

import com.ayrten.scrots.screens.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;

public class RegDot3 extends Dot
{
	private Circle circle;
	
	public RegDot3(Texture dot, Manager gm, Sound pop)
	{
		super(dot, gm, pop);
		circle = new Circle();
	}

	@Override
	public void touchedByAnAngel()
	{
		super.touchedByAnAngel();

		circle.set(getCenterX(), getCenterY(), getWidth());
		checkRadius(gm.curr_level.get_baby_blue_dots(), false);
		checkRadius(gm.curr_level.get_blue_dots(), false);
		checkRadius(gm.curr_level.get_grn_dots(), true);
		checkRadius(gm.curr_level.get_red_dots(), false);
	}

	private void checkRadius(ArrayList<Dot> arrayList, boolean isRegDot1)
	{
		for(Dot dot : arrayList)
		{
			if(circle.contains(dot.getCenterX() + dot.getWidth(), dot.getCenterY())
					|| circle.contains(dot.getCenterX() - dot.getWidth(), dot.getCenterY())
					|| circle.contains(dot.getCenterX(), dot.getCenterY() + dot.getWidth())
					|| circle.contains(dot.getCenterX(), dot.getCenterY() - dot.getWidth())
					|| circle.contains(dot.getCenterX() + 1.414f, dot.getCenterY() + 1.414f) 
					|| circle.contains(dot.getCenterX() - 1.414f, dot.getCenterY() + 1.414f) 
					|| circle.contains(dot.getCenterX() + 1.414f, dot.getCenterY() - 1.414f) 
					|| circle.contains(dot.getCenterX() - 1.414f, dot.getCenterY() - 1.414f))
			{
				dot.remove();
				if(isRegDot1)
					gm.curr_level.minusGreenDot();;				
			}

		}
	}
}
