package com.ayrten.scrots.game;

import com.ayrten.scrots.dots.Dot;
import com.ayrten.scrots.level.Level;
import com.ayrten.scrots.level.MainMenuBackgroundLevel;
import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameMode {
	public static final int MAIN_MENU_BACKGROUND_MODE = -1;
	public static final int NORMAL_MODE = 1;
	public static final int CHALLENGE_MODE = 2;

	protected Stage stage;
	protected Manager gm;
	protected Level curr_level;
	protected int w, h;
	
	private int levels_generated = 1;

	public GameMode(Stage stage, Manager gm, int width,
			int height) {
		this.stage = stage;
		this.gm = gm;
		this.w = width;
		this.h = height;

		Gdx.input.setInputProcessor(this.stage);
		generate();
	}

	protected void generate()
	{
		new Thread(new Runnable()
		{
			   @Override
			   public void run()
			   {
				   curr_level = new Level(levels_generated, w, h, gm);
				   GameMode.this.levels_generated++;
			   }
			}).start();
	}
	
	public Level gen_curr_level()
	{
		generate();
		gm.setLevel(curr_level);
		
		for(int i = 0; i < curr_level.get_baby_blue_dots().size(); i++)
		{
			stage.addActor(curr_level.get_baby_blue_dots().get(i));
		}
		
		for(int i = 0; i < curr_level.get_dwd_baby_blue_dots().size(); i++)
		{
			stage.addActor(curr_level.get_dwd_baby_blue_dots().get(i));
		}
		
		for(int i = 0; i < curr_level.get_dwd_blue_dots().size(); i++)
		{
			stage.addActor(curr_level.get_dwd_blue_dots().get(i));
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
		
		int dwd_red_half_size = curr_level.get_dwd_red_dots().size()/2;
		for(int i = 0; i < curr_level.get_dwd_red_dots().size()/2; i++)
		{
			stage.addActor(curr_level.get_dwd_red_dots().get(i));
		}
		
		int grn_half_size = curr_level.get_grn_dots().size()/2;
		for(int i = 0; i < curr_level.get_grn_dots().size()/2; i++)
		{
			stage.addActor(curr_level.get_grn_dots().get(i));
		}
		
		int dwd_grn_half_size = curr_level.get_dwd_grn_dots().size()/2;
		for(int i = 0; i < curr_level.get_dwd_grn_dots().size()/2; i++)
		{
			stage.addActor(curr_level.get_dwd_grn_dots().get(i));
		}
		
		for(int i = dwd_red_half_size; i < curr_level.get_dwd_red_dots().size(); i++)
		{
			stage.addActor(curr_level.get_dwd_red_dots().get(i));
		}
		
		for(int i = red_half_size; i < curr_level.get_red_dots().size(); i++)
		{
			stage.addActor(curr_level.get_red_dots().get(i));
		}
		
		for(int i = dwd_grn_half_size; i < curr_level.get_dwd_grn_dots().size(); i++)
		{
			stage.addActor(curr_level.get_dwd_grn_dots().get(i));
		}
		
		for(int i = grn_half_size; i < curr_level.get_grn_dots().size(); i++)
		{
			stage.addActor(curr_level.get_grn_dots().get(i));
		}
		
		for(Dot dot: curr_level.get_power_ups())
		{
			stage.addActor(dot);
		}
		
		if(gm.isRainbowState())
			gm.changePenalityDotVisibility(false);
		
		if(gm.isMagnetState())
			stage.addActor(gm.getMagnet());
		
		return curr_level;
	}	
}
