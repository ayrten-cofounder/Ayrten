package com.ayrten.scrots.dotGraphics;

import com.ayrten.scrots.dots.Dot;

public class DotGraphics_NormalGameMode extends DotGraphics {

	public DotGraphics_NormalGameMode(Dot dot) {
		super(dot);
	}

	@Override
	public void move() {
		move_v3();
	}
}
