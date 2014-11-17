package com.ayrten.scrots.screens;

import com.ayrten.scrots.manager.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class ContactScreen extends ScrotsScreen
{
	protected Label msg;
	protected Table table;

	public ContactScreen(Screen bscreen) 
	{
		super(bscreen, false);
		createBackLabel();
		table = new Table(Assets.skin);
		table.sizeBy(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = Assets.font_64;
		labelStyle.fontColor = Assets.ORANGE;

		msg = new Label("", labelStyle);
		msg.setText("Want to make a suggestion to improve the game? Got a bug to report? We at Ayrten strive to provide" +
				" quick and responsive support. We would like to sincerely thank you for playing Scrots. Please send" +
				" all inquiries/comments to android.ayrten@gmail.com");
		msg.setWrap(true);

		table.add(back).top().left().padLeft(back.getWidth()/5);
		table.row();
		table.add("").height(msg.getHeight()/2);
		table.row();
		table.add(msg).expand().left().top().width(Gdx.graphics.getWidth() - back.getWidth()/5).padLeft(back.getWidth()/5);

		setupStage();
		stage.addActor(table);
	}
}
