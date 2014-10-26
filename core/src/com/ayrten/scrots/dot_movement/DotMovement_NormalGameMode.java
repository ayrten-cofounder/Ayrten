package com.ayrten.scrots.dot_movement;

import com.ayrten.scrots.dots.Dot;
import com.ayrten.scrots.manager.Assets;
import com.badlogic.gdx.Gdx;

public class DotMovement_NormalGameMode extends DotMovement {

	public DotMovement_NormalGameMode(Dot dot) {
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
		if (velocity_count == velocity_of_dot) {
			velocity_count = 0;
		} else {
			velocity_count++;
			return;
		}
		
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
