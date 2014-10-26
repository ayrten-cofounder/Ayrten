package com.ayrten.scrots.dotGraphics;

import com.ayrten.scrots.dots.Dot;

public class DotGraphics_NormalGameMode extends DotGraphics {

	public DotGraphics_NormalGameMode(Dot dot) {
		super(dot);
		
		randSlopeV3();
//		if(dot.gm.get_player_score() >= 6 && dot.gm.get_player_score() < 9)
//		{
//			randSlopeV2();
//		}
//		else if(dot.gm.get_player_score() >= 9)
//		{
//			randSlopeV3();
//		}
	}

	@Override
	public void move() {
		move_v3();
//		if(dot.gm.get_player_score() >= 3 && dot.gm.get_player_score() < 6)
//		{
//			move_v1();
//		}
//		else if(dot.gm.get_player_score() >= 6 && dot.gm.get_player_score() < 9)
//		{
//			move_v2();
//		}
//		else if(dot.gm.get_player_score() >= 9 && dot.gm.get_player_score() < 12)
//		{
//			move_v3();
//		}
//		else if(dot.gm.get_player_score() >= 12)
//		{
//			move_v3();
//		}
	}
}
