package com.ayrten.scrots.game;

import com.ayrten.scrots.level.Level;
import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class ChallengeGameMode extends NormalGameMode
{

	public ChallengeGameMode(Stage stage, Manager gm, int width, int height) 
	{
		super(stage, gm, width, height);
	}
	
	public Level gen_curr_level()
	{
		Level curr_level = all_levels.remove(0);
		gm.setLevel(curr_level);
		for(int i = 0; i < curr_level.get_baby_blue_dots().size(); i++)
		{
			stage.addActor(curr_level.get_baby_blue_dots().get(i));
		}
		
		for(int i = 0; i < curr_level.get_blue_dots().size(); i++)
		{
			stage.addActor(curr_level.get_blue_dots().get(i));
		}
		
		int red_half_size = curr_level.get_red_dots().size()/2;
		for(int i = 0; i < curr_level.get_red_dots().size()/2; i++)
		{
			stage.addActor(curr_level.get_red_dots().get(i));
		}
		
		int grn_half_size = curr_level.get_grn_dots().size()/2;
		for(int i = 0; i < curr_level.get_grn_dots().size()/2; i++)
		{
			stage.addActor(curr_level.get_grn_dots().get(i));
		}
		
		for(int i = red_half_size; i < curr_level.get_red_dots().size(); i++)
		{
			stage.addActor(curr_level.get_red_dots().get(i));
		}
		
		for(int i = grn_half_size; i < curr_level.get_grn_dots().size(); i++)
		{
			stage.addActor(curr_level.get_grn_dots().get(i));
		}
		
		return curr_level;
	}
}
