package com.ayrten.scrots.dotAnimation;

import com.ayrten.scrots.dots.MovingDot;


public class DotAnimation_TimeMode extends DotAnimation {

	public DotAnimation_TimeMode() {
	}

	@Override
	public void move(MovingDot dot) {
		move_v3(dot);
	}
}
