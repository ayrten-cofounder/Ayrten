///*
// * This is game mode is for the background for the MainMenuScreen.
// * 
// * So we can have balls.
// */
//
//package com.ayrten.scrots.game;
//
//import com.ayrten.scrots.level.Level;
//import com.ayrten.scrots.manager.Manager;
//import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.scenes.scene2d.Touchable;
//
//public class MainMenuBackgroundGameMode extends GameMode {
//
//	public MainMenuBackgroundGameMode(Stage stage, Manager gm, int width,
//			int height) {
//		super(stage, gm, width, height);
//	}
//
////	public Level gen_curr_level() {
//////		Level curr_level = all_levels.remove(0);
////		Level curr_level = new Level(20, w, h, gm);
//////		gm.setLevel(curr_level);
//////		for (int i = 0; i < curr_level.get_baby_blue_dots().size(); i++) {
//////			curr_level.get_baby_blue_dots().get(i).setTouchable(Touchable.disabled);
//////			stage.addActor(curr_level.get_baby_blue_dots().get(i));
//////		}
//////
//////		for(int i = 0; i < curr_level.get_dwd_baby_blue_dots().size(); i++)
//////		{
//////			curr_level.get_dwd_baby_blue_dots().get(i).setTouchable(Touchable.disabled);
//////			stage.addActor(curr_level.get_dwd_baby_blue_dots().get(i));
//////		}
//////		
//////		for(int i = 0; i < curr_level.get_dwd_blue_dots().size(); i++)
//////		{
//////			curr_level.get_dwd_blue_dots().get(i).setTouchable(Touchable.disabled);
//////			stage.addActor(curr_level.get_dwd_blue_dots().get(i));
//////		}
//////		
//////		for (int i = 0; i < curr_level.get_blue_dots().size(); i++) {
//////			curr_level.get_blue_dots().get(i).setTouchable(Touchable.disabled);
//////			stage.addActor(curr_level.get_blue_dots().get(i));
//////		}
//////
//////		int dwd_red_half_size = curr_level.get_dwd_red_dots().size()/2;
//////		for(int i = 0; i < curr_level.get_dwd_red_dots().size()/2; i++)
//////		{
//////			curr_level.get_dwd_red_dots().get(i).setTouchable(Touchable.disabled);
//////			stage.addActor(curr_level.get_dwd_red_dots().get(i));
//////		}
//////		
//////		
//////		int red_half_size = (int) Math.floor(curr_level.get_red_dots().size() / 2);
//////		int red_other_half_size = curr_level.get_red_dots().size() - red_half_size;
//////		
//////		int grn_half_size = (int) Math.floor(curr_level.get_grn_dots().size() / 2);
//////		int grn_other_half_size = curr_level.get_grn_dots().size() - grn_half_size;
//////		
//////		for (int i = 0; i < curr_level.get_red_dots().size() / 2; i++) {
//////			curr_level.get_red_dots().get(i).setTouchable(Touchable.disabled);
//////			stage.addActor(curr_level.get_red_dots().get(i));
//////		}
//////		
//////		int dwd_grn_half_size = curr_level.get_dwd_grn_dots().size()/2;
//////		for(int i = 0; i < curr_level.get_dwd_grn_dots().size()/2; i++)
//////		{
//////			curr_level.get_dwd_grn_dots().get(i).setTouchable(Touchable.disabled);
//////			stage.addActor(curr_level.get_dwd_grn_dots().get(i));
//////		}
//////
//////		for (int i = 0; i < curr_level.get_grn_dots().size() / 2; i++) {
//////			curr_level.get_grn_dots().get(i).setTouchable(Touchable.disabled);
//////			stage.addActor(curr_level.get_grn_dots().get(i));
//////		}
//////		
//////		for(int i = dwd_red_half_size; i < curr_level.get_dwd_red_dots().size(); i++)
//////		{
//////			curr_level.get_dwd_red_dots().get(i).setTouchable(Touchable.disabled);
//////			stage.addActor(curr_level.get_dwd_red_dots().get(i));
//////		}
//////
//////		for (int i = red_other_half_size; i < curr_level.get_red_dots().size(); i++) {
//////			curr_level.get_red_dots().get(i).setTouchable(Touchable.disabled);
//////			stage.addActor(curr_level.get_red_dots().get(i));
//////		}
//////
//////		for (int i = grn_other_half_size; i < curr_level.get_grn_dots().size(); i++) {
//////			curr_level.get_grn_dots().get(i).setTouchable(Touchable.disabled);
//////			stage.addActor(curr_level.get_grn_dots().get(i));
//////		}
//////		
//////		for(int i = dwd_grn_half_size; i < curr_level.get_dwd_grn_dots().size(); i++)
//////		{
//////			curr_level.get_dwd_grn_dots().get(i).setTouchable(Touchable.disabled);
//////			stage.addActor(curr_level.get_dwd_grn_dots().get(i));
//////		}
////
////		return curr_level;
////	}
//
//}
