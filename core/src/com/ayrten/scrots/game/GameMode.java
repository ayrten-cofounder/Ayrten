package com.ayrten.scrots.game;

import com.ayrten.scrots.level.Level;
import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class GameMode {
	public static final int MAIN_MENU_BACKGROUND_MODE = -1;
	public static final int NORMAL_MODE = 1;
	public static final int CHALLENGE_MODE = 2;

	protected Stage stage;
	protected Manager gm;

	public GameMode(Stage stage, Manager gm) {
		this.stage = stage;
		this.gm = gm;
	}

	protected Level generate(int lvl)	{
		return (new Level(lvl, gm));
	}
	
	public Level gen_curr_level(int lvl, Touchable touchable) {
		Level curr_level = gen_curr_level(lvl);
		for(int i = 0; i < curr_level.get_all_dots().size(); i++)
			curr_level.get_all_dots().get(i).setTouchable(touchable);
		return curr_level;
	}
	
	public Level gen_curr_level(int lvl)
	{
		Level curr_level = generate(lvl);
		gm.setLevel(curr_level);
		
		for(int i = 0; i < curr_level.get_all_dots().size(); i++)
			stage.addActor(curr_level.get_all_dots().get(i));
		
		if(gm.isRainbowState())
			gm.changePenalityDotVisibility(false);
		
		if(gm.isMagnetState())
			stage.addActor(gm.getMagnet());
		
		return curr_level;
	}	
}
