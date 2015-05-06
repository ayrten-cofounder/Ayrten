package com.ayrten.scrots.game;

import java.util.ArrayList;

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
	
	protected ArrayList<Level> all_levels = new ArrayList<Level>();
	protected int levels_generated = 0;

	public GameMode(Stage stage, Manager gm) {
		this.stage = stage;
		this.gm = gm;
	}
	
	public GameMode(Stage stage, Manager gm, int start_lvl) {
		this.stage = stage;
		this.gm = gm;
	}

	protected void generate_threaded(final int lvl)	{
		new Thread(new Runnable() {
			@Override
			public void run() {
				Level new_level = new Level(lvl, gm);
				all_levels.add(new_level);
			}
		}).start();
	}
	
	protected void generate(int lvl) {
		all_levels.add(new Level(lvl, gm));
	}
	
	public Level gen_start_level(int lvl, Touchable touchable) {
		gen_start_level(lvl);
		Level curr_level = gm.curr_level;
		for(int i = 0; i < curr_level.get_all_dots().size(); i++)
			curr_level.get_all_dots().get(i).setTouchable(touchable);
		return curr_level;
	}
	
	public void gen_start_level(int lvl) {
		gen_level(lvl);
		levels_generated = lvl + 2;
	}
	
	public void gen_next_level() {
		gen_level(levels_generated);
		levels_generated++;
	}
	
	private Level gen_level(int lvl)
	{
		if(all_levels.size() == 0) {
			generate(lvl);
			generate_threaded(lvl + 1);
		} else
			generate_threaded(lvl);
		Level curr_level = all_levels.remove(0);
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
