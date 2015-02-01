package com.ayrten.scrots.level;

import com.ayrten.scrots.dots.PenDot1;
import com.ayrten.scrots.dots.PenDot2;
import com.ayrten.scrots.dots.RegDot1;
import com.ayrten.scrots.dots.RegDot2;
import com.ayrten.scrots.manager.Manager;

public class MainMenuBackgroundLevel extends Level {

	public MainMenuBackgroundLevel(int level, int width, int height, Manager gm) {
		super(level, width, height, gm);
	}

	protected void gen_grn_dots() {
		regDots1.clear();
		int num = (int) Math.floor((level - GREEN_DOT_START) * GREEN_DOT_MOD);

		for (int i = 0; i < num; i++) {
			RegDot1 dot = generator.genRegDot1();
			regDots1.add(dot);
		}
	}

	protected void gen_red_dots() {
		penDots1.clear();
		int num = (int) Math.floor((level - RED_DOT_START) * RED_DOT_MOD);

		for (int i = 0; i < num; i++) {
			PenDot1 dot = generator.genPenDot1();
			penDots1.add(dot);
		}
	}

	protected void gen_blue_dots() {
		penDots2.clear();
		int num = (int) Math.floor((level - BLUE_DOT_START) * BLUE_DOT_MOD);

		for (int i = 0; i < num; i++) {
			PenDot2 dot = generator.genPenDot2();
			penDots2.add(dot);
		}
	}

	protected void gen_baby_blue_dots() {
		regDots2.clear();
		int num = (int) Math.floor((level - BABY_BLUE_DOT_START)
				* BABY_BLUE_DOT_MOD);

		for (int i = 0; i < num; i++) {
			RegDot2 dot = generator.genRegDot2();
			regDots2.add(dot);
		}
	}
}
