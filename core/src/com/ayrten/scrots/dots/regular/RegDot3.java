package com.ayrten.scrots.dots.regular;

import java.util.LinkedList;

import com.ayrten.scrots.dots.MovingDot;
import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class RegDot3 extends MovingDot {
	private Circle circle;

	public RegDot3(Texture dot, Manager gm, Sound pop) {
		super(dot, gm, pop);
		circle = new Circle();
	}

	@Override
	public void touchedByAnAngel() {
		super.touchedByAnAngel();

		circle.set(getX(Align.center), getY(Align.center), getWidth());
		checkRadius(gm.curr_level.getDotList());
		Assets.stats_manager.getPlayerStats().reg_dot_3.popped++;
	}

	private void checkRadius(LinkedList<MovingDot> linkedList) {
		for (MovingDot dot : linkedList) {
			if (circle.contains(dot.getX(Align.center) + dot.getWidth(),
					dot.getY(Align.center))
					|| circle.contains(dot.getX(Align.center) - dot.getWidth(),
							dot.getY(Align.center))
							|| circle.contains(dot.getX(Align.center), dot.getY(Align.center)
									+ dot.getWidth())
									|| circle.contains(dot.getX(Align.center), dot.getY(Align.center)
											- dot.getWidth())
											|| circle.contains(dot.getX(Align.center) + 1.414f,
													dot.getY(Align.center) + 1.414f)
													|| circle.contains(dot.getX(Align.center) - 1.414f,
															dot.getY(Align.center) + 1.414f)
															|| circle.contains(dot.getX(Align.center) + 1.414f,
																	dot.getY(Align.center) - 1.414f)
																	|| circle.contains(dot.getX(Align.center) - 1.414f,
																			dot.getY(Align.center) - 1.414f)) {
				boolean removed = dot.remove();
				if (dot.getClass() == RegDot1.class && removed)
				{
					gm.curr_level.minusGreenDot();
					Assets.points_manager.addPoints(1);
				}
			}
		}
	}
}
