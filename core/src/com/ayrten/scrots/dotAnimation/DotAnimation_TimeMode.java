package com.ayrten.scrots.dotAnimation;

import com.ayrten.scrots.dots.Dot;


public class DotAnimation_TimeMode extends DotAnimation {

	public DotAnimation_TimeMode() {
	}

	@Override
	public void move(Dot dot) {
		move_v3(dot);
	}
}
