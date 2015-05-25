package com.ayrten.scrots.dotAnimation;

import com.ayrten.scrots.dots.Dot;

public class DotAnimation_TimeMode extends DotAnimation {

	public DotAnimation_TimeMode(Dot dot) {
		super(dot);
	}

	@Override
	public void move() {
		move_v3();
	}
}
