package com.ayrten.scrots.screens;

import com.ayrten.scrots.manager.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class CreditsScreen extends ScrotsScreen 
{	
	public CreditsScreen(Screen bscreen) 
	{
		super(bscreen, true);
		setupStage();
		
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = Assets.font_32;
		labelStyle.fontColor = Assets.ORANGE;
		
		Label created = new Label("", labelStyle);
		created.setWrap(true);
		created.setText("Created by");
		created.setWidth(labelStyle.font.getBounds("Created by").width);
		
		Label createdNames = new Label("", labelStyle);
		createdNames.setWrap(true);
		createdNames.setText("Andy Yeung and Tony Doan");
		
		Label thanks = new Label("", labelStyle);
		thanks.setWrap(true);
		thanks.setText("Special thanks to");
		thanks.setWidth(labelStyle.font.getBounds("Special thanks to").width);
		
		Label publish = new Label("", labelStyle);
		publish.setWrap(true);
		publish.setText("Published by");
		publish.setWidth(labelStyle.font.getBounds("Published by").width);
		
		Label publishName = new Label("", labelStyle);
		publishName.setWrap(true);
		publishName.setText("Ayrten");
		publishName.setWidth(labelStyle.font.getBounds("Ayrten").width);
		
		// Site credit for music
		
		Table table = new Table(Assets.skin);
		table.setFillParent(true);
		
		table.add(created).left().padLeft(Gdx.graphics.getWidth()/2 - created.getWidth()).width(created.getWidth());
		table.add(createdNames).padLeft(back.getWidth()/5);
		table.row();
		table.add(thanks).left().padLeft(Gdx.graphics.getWidth()/2 - thanks.getWidth());
		table.row();
		table.add(publish).left().padLeft(Gdx.graphics.getWidth()/2 - publish.getWidth());
		table.add(publishName).padLeft(back.getWidth()/5);
		
		table.debug();
		table.debugCell();
		
		table.align(Align.left);
		table.padLeft(back.getWidth()/5);
		
		stage.addActor(table);
	}
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		super.render(delta);
		Table.drawDebug(stage);
	}
}
