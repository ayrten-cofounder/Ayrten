package com.ayrten.scrots.screens;

import com.ayrten.scrots.manager.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
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

		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = Assets.font_32;
		labelStyle.fontColor = Assets.ORANGE;

		Label msg = new Label("", labelStyle);
		msg.setText("Want to make a suggestion to improve the game? Got a bug to report? We at Ayrten strive to provide" +
				" quick and responsive support. We would like to sincerely thank you for playing Scrots.");
		msg.setWrap(true);
		
		Label general = new Label("", labelStyle);
		general.setText("Please send all inquiries/comments to android.ayrten@gmail.com");
		general.setWrap(true);
		
		Label facebook = new Label("", labelStyle);
		facebook.setText("Facebook: https://www.facebook.com/AyrtenMobile");
		facebook.setWrap(true);
		
		Label twitter = new Label("", labelStyle);
		twitter.setText("Twitter: https://twitter.com/AyrtenMobile");
		twitter.setWrap(true);
		
		table.add(back).top().left().padLeft(back.getWidth()/5);
		table.row();
		table.add("").height(back.getStyle().font.getLineHeight()/2);
		table.row();
		table.add(msg).expandX().left().top().width(Gdx.graphics.getWidth() - back.getWidth()/5).padLeft(back.getWidth()/5);
		table.row();
		table.add("").height(back.getStyle().font.getLineHeight()/2);
		table.row();
		table.add(general).left().top().width(Gdx.graphics.getWidth() - back.getWidth()/5).padLeft(back.getWidth()/5);
		table.row();
		table.add("").height(back.getStyle().font.getLineHeight()/2);
		table.row();
		table.add(facebook).left().top().width(Gdx.graphics.getWidth() - back.getWidth()/5).padLeft(back.getWidth()/5);
		table.row();
		table.add("").height(back.getStyle().font.getLineHeight()/2);
		table.row();
		table.add(twitter).left().top().width(Gdx.graphics.getWidth() - back.getWidth()/5).padLeft(back.getWidth()/5).expand();
		
		scroll_view = new ScrollPane(table);
		scroll_view.setFillParent(true);
		scroll_view.setFlickScroll(true);
		scroll_view.updateVisualScroll();
		
		setupStage();
		stage.addActor(scroll_view);
	}
}
