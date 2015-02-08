package com.ayrten.scrots.screens;

import java.util.ArrayList;

import com.ayrten.scrots.manager.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class CreditsScreen extends ScrotsScreen 
{	
	public CreditsScreen(Screen bscreen) 
	{
		super(bscreen, false);
		createBackLabel();
		
		Label created = new Label("", Assets.style_font_32_orange);
		created.setWrap(true);
		created.setText("Created by");
		created.setWidth(Assets.style_font_32_orange.font.getBounds("Created by").width);
		
		Label createdNames = new Label("", Assets.style_font_32_orange);
		createdNames.setWrap(true);
		createdNames.setText("Andy Yeung and Tony Doan");
		
		Label thanks = new Label("", Assets.style_font_32_orange);
		thanks.setWrap(true);
		thanks.setText("Special thanks to");
		thanks.setWidth(Assets.style_font_32_orange.font.getBounds("Special thanks to").width);
		
		Label thanksName = new Label("", Assets.style_font_32_orange);
		thanksName.setWrap(true);
		thanksName.setText("Family and friends who supported us");
		thanksName.setWidth(Assets.style_font_32_orange.font.getBounds("Family and friends who supported us").width);
		
		ArrayList<String> thanks_list = new ArrayList<String>();
		thanks_list.add("Trina Dao");
		thanks_list.add("Emily Do");
		
		Table thanks_table = new Table(Assets.skin);
		thanks_table.left();
		thanks_table.add(thanksName);
		for(int i = 0; i < thanks_list.size(); i++)
		{
			Label thank_person = new Label(thanks_list.get(i), Assets.style_font_32_orange);
			thank_person.setWrap(true);
			thank_person.setWidth(Assets.style_font_32_orange.font.getBounds(thanks_list.get(i)).width);
			thanks_table.row();
			thanks_table.add(thank_person);
		}
		
		Label publish = new Label("", Assets.style_font_32_orange);
		publish.setWrap(true);
		publish.setText("Published by");
		publish.setWidth(Assets.style_font_32_orange.font.getBounds("Published by").width);
		
		Label publishName = new Label("", Assets.style_font_32_orange);
		publishName.setWrap(true);
		publishName.setText("Ayrten");
		publishName.setWidth(Assets.style_font_32_orange.font.getBounds("Ayrten").width);
		
		Label misc = new Label("", Assets.style_font_32_orange);
		misc.setWrap(true);
		misc.setText("Assets are used according to terms described in the Creative Commons 3.0 Attribution"
				+ " License. Giving credit and thanks to the following:");
		misc.setBounds(misc.getX(), misc.getY(), misc.getWidth(), misc.getHeight());
		
		Label miscMem0 = new Label("http://audionautix.com", Assets.style_font_32_blue);
		miscMem0.setWrap(true);
		miscMem0.setBounds(miscMem0.getX(), miscMem0.getY(), miscMem0.getWidth(), miscMem0.getHeight());
		miscMem0.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.net.openURI("http://audionautix.com");
			}
		});
		
		Label miscMem1 = new Label("http://www.bensound.com", Assets.style_font_32_blue);
		miscMem1.setWrap(true);
		miscMem1.setBounds(miscMem1.getX(), miscMem1.getY(), miscMem1.getWidth(), miscMem1.getHeight());
		miscMem1.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.net.openURI("http://www.bensound.com");
			}
		});
		
		Label miscMem2 = new Label("https://www.freesound.org", Assets.style_font_32_blue);
		miscMem2.setWrap(true);
		miscMem2.setBounds(miscMem2.getX(), miscMem2.getY(), miscMem2.getWidth(), miscMem2.getHeight());
		miscMem2.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.net.openURI("https://www.freesound.org");
			}
		});
		
		Table table = new Table(Assets.skin);
		table.left().top();
		table.padLeft(back.getWidth()/5).padRight(back.getWidth()/5);
		
		Table upperTable = new Table(Assets.skin);
		upperTable.add(back).left();
		upperTable.row();
		upperTable.add(created).left().top().padLeft(Gdx.graphics.getWidth()/2 - created.getWidth()).width(created.getWidth());
		upperTable.add(createdNames).left().padLeft(back.getWidth()/5).width(Gdx.graphics.getWidth()/2 - back.getWidth()/5 * 2);
		upperTable.row();
		upperTable.add("").height(back.getStyle().font.getLineHeight());
		upperTable.row();
		upperTable.add(thanks).left().top().padLeft(Gdx.graphics.getWidth()/2 - thanks.getWidth());
		upperTable.add(thanks_table).left().padLeft(back.getWidth()/5).width(Gdx.graphics.getWidth()/2 - back.getWidth()/5 * 2);
		upperTable.row();
		upperTable.add("").height(back.getStyle().font.getLineHeight());
		upperTable.row();
		upperTable.add(publish).left().top().padLeft(Gdx.graphics.getWidth()/2 - publish.getWidth());
		upperTable.add(publishName).left().padLeft(back.getWidth()/5).width(Gdx.graphics.getWidth()/2 - back.getWidth()/5);
		
		Table bottomTable = new Table(Assets.skin);
		bottomTable.left().top();
		bottomTable.add(misc).left().width(Gdx.graphics.getWidth() - back.getWidth()/5);
		bottomTable.row();
		bottomTable.add(miscMem0).left().width(Gdx.graphics.getWidth() - back.getWidth()/5 * 2);
		bottomTable.row();
		bottomTable.add(miscMem1).left().width(Gdx.graphics.getWidth() - back.getWidth()/5 * 2);
		bottomTable.row();
		bottomTable.add(miscMem2).left().width(Gdx.graphics.getWidth() - back.getWidth()/5 * 2);
		bottomTable.row();
		bottomTable.add("").height(back.getStyle().font.getLineHeight());
		
		
		table.add(upperTable);
		table.row();
		table.add("").height(back.getStyle().font.getLineHeight());
		table.row();
		table.add(bottomTable);
		
		ScrollPane scroll_view = new ScrollPane(table);
		scroll_view.setFillParent(true);
		scroll_view.setScrollingDisabled(true, false);
		
		setupStage();
		stage.addActor(scroll_view);
	}
}
