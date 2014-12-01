package com.ayrten.scrots.screens;

import com.ayrten.scrots.manager.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class ContactScreen extends ScrotsScreen
{
	protected ScrollPane scroll_view;

	public ContactScreen(Screen bscreen) 
	{
		super(bscreen, false);
		createBackLabel();
		
		Table table = new Table(Assets.skin);
		table.setFillParent(true);
		table.left().top();
		table.padLeft(back.getWidth()/5).padRight(back.getWidth()/5);

		Label msg = new Label("", Assets.style_font_32_orange);
		msg.setText("Want to make a suggestion to improve the game? Got a bug to report? We at Ayrten strive to provide" +
				" quick and responsive support. We would like to sincerely thank you for playing Scrots.");
		msg.setWrap(true);
		
		Label general = new Label("", Assets.style_font_32_orange);
		general.setText("Please send all inquiries/comments to android.ayrten@gmail.com");
		general.setWrap(true);
		
		Label facebook = new Label("", Assets.style_font_32_orange);
		facebook.setText("Facebook: https://www.facebook.com/AyrtenMobile");
		facebook.setWrap(true);
		
		Label twitter = new Label("", Assets.style_font_32_orange);
		twitter.setText("Twitter: https://twitter.com/AyrtenMobile");
		twitter.setWrap(true);
		
		table.add(back).top().left();
		table.row();
		table.add("").height(back.getStyle().font.getLineHeight()/2);
		table.row();
		table.add(msg).width(Gdx.graphics.getWidth() - back.getWidth()/5);
		table.row();
		table.add("").height(back.getStyle().font.getLineHeight()/2);
		table.row();
		table.add(general).width(Gdx.graphics.getWidth() - back.getWidth()/5);
		table.row();
		table.add("").height(back.getStyle().font.getLineHeight()/2);
		table.row();
		table.add(facebook).width(Gdx.graphics.getWidth() - back.getWidth()/5);
		table.row();
		table.add("").height(back.getStyle().font.getLineHeight()/2);
		table.row();
		table.add(twitter).width(Gdx.graphics.getWidth() - back.getWidth()/5);
		
		scroll_view = new ScrollPane(table);
		scroll_view.setFillParent(true);
		scroll_view.setFlickScroll(true);
		scroll_view.updateVisualScroll();
		
		setupStage();
		stage.addActor(scroll_view);
	}
}
