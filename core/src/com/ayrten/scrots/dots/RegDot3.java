package com.ayrten.scrots.dots;

import java.util.ArrayList;
import java.util.LinkedList;

import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;

public class RegDot3 extends Dot {
	private Circle circle;

	public RegDot3(Texture dot, Manager gm, Sound pop) {
		super(dot, gm, pop);
		circle = new Circle();
	}

	@Override
	public void touchedByAnAngel() {
		super.touchedByAnAngel();

		circle.set(getCenterX(), getCenterY(), getWidth());
		checkRadius(gm.curr_level.get_all_dots());
	}

	private void checkRadius(LinkedList<ArrayList<Dot>> linkedList) {
		for (ArrayList<Dot> dotList : linkedList) {
			for (Dot dot : dotList) {
				if (circle.contains(dot.getCenterX() + dot.getWidth(),
						dot.getCenterY())
						|| circle.contains(dot.getCenterX() - dot.getWidth(),
								dot.getCenterY())
						|| circle.contains(dot.getCenterX(), dot.getCenterY()
								+ dot.getWidth())
						|| circle.contains(dot.getCenterX(), dot.getCenterY()
								- dot.getWidth())
						|| circle.contains(dot.getCenterX() + 1.414f,
								dot.getCenterY() + 1.414f)
						|| circle.contains(dot.getCenterX() - 1.414f,
								dot.getCenterY() + 1.414f)
						|| circle.contains(dot.getCenterX() + 1.414f,
								dot.getCenterY() - 1.414f)
						|| circle.contains(dot.getCenterX() - 1.414f,
								dot.getCenterY() - 1.414f)) {
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
}
