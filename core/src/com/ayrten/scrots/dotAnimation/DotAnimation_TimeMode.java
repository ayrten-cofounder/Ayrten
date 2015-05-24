package com.ayrten.scrots.dotAnimation;

import com.ayrten.scrots.dots.Dot;

public class DotAnimation_NormalGameMode extends DotAnimation {

	public DotAnimation_NormalGameMode(Dot dot) {
		super(dot);
	}

	@Override
	public void move() {
		move_v3();
	}
}
