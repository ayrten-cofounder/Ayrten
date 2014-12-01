package com.ayrten.scrots.screens;

import com.ayrten.scrots.manager.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
		misc.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.net.openURI("http://audionautix.com");
			}
		});
		
		Label miscMem0 = new Label("http://audionautix.com", Assets.style_font_32_blue);
		miscMem0.setWrap(true);
		miscMem0.setBounds(miscMem0.getX(), miscMem0.getY(), miscMem0.getWidth(), miscMem0.getHeight());
		miscMem0.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.net.openURI("http://audionautix.com");
			}
		});
		
		Table table = new Table(Assets.skin);
		table.setFillParent(true);
		table.left().top();
		table.padLeft(back.getWidth()/5).padRight(back.getWidth()/5);
		
		Table upperTable = new Table(Assets.skin);
		upperTable.add(back).left();
		upperTable.row();
		upperTable.add(created).left().padLeft(Gdx.graphics.getWidth()/2 - created.getWidth()).width(created.getWidth());
		upperTable.add(createdNames).left().padLeft(back.getWidth()/5);
		upperTable.row();
		upperTable.add("").height(back.getStyle().font.getLineHeight());
		upperTable.row();
		upperTable.add(thanks).left().padLeft(Gdx.graphics.getWidth()/2 - thanks.getWidth());
		upperTable.row();
		upperTable.add("").height(back.getStyle().font.getLineHeight());
		upperTable.row();
		upperTable.add(publish).left().padLeft(Gdx.graphics.getWidth()/2 - publish.getWidth());
		upperTable.add(publishName).left().padLeft(back.getWidth()/5).width(Gdx.graphics.getWidth()/2 - back.getWidth()/5);
		
		Table bottomTable = new Table(Assets.skin);
		bottomTable.left().top();
		bottomTable.add(misc).left().width(Gdx.graphics.getWidth() - back.getWidth()/5);
		bottomTable.row();
		bottomTable.add(miscMem0).left();
		
		table.add(upperTable);
		table.row();
		table.add("").height(back.getStyle().font.getLineHeight());
		table.row();
		table.add(bottomTable);
			
		setupStage();
		stage.addActor(table);
	}
}
