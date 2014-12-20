package com.ayrten.scrots.screens;

import com.ayrten.scrots.manager.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ContactScreen extends ScrotsScreen
{
	protected ScrollPane scroll_view;

	public ContactScreen(Screen bscreen) 
	{
		super(bscreen, false);
		createBackLabel();
		
		Table table = new Table(Assets.skin);
		table.left().top();
		table.padLeft(back.getWidth()/5).padRight(back.getWidth()/5);

		Label msg = new Label("", Assets.style_font_32_orange);
		msg.setText("Want to make a suggestion to improve the game? Got a bug to report? We at Ayrten strive to provide" +
				" quick and responsive support. We would like to sincerely thank you for playing Scrots.");
		msg.setWrap(true);
		
		Label general = new Label("", Assets.style_font_32_orange);
		general.setText("Please send all inquiries/comments to ");
		general.setWrap(true);
		general.setWidth(general.getStyle().font.getBounds("Please send all inquiries/comments to ").width);
		
		Label generalURL = new Label("android.ayrten@gmail.com", Assets.style_font_32_blue);
		generalURL.setWrap(true);
		generalURL.setBounds(generalURL.getX(), generalURL.getY(), generalURL.getWidth(), generalURL.getHeight());
		generalURL.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Assets.game.apk_intf.showToast("Copied to android.ayrten@gmail.com to clipboard!");
				Assets.game.apk_intf.copyTextToClipboard("android.ayrten@gmail.com");
			}
		});
		
		Table generalTable = new Table(Assets.skin);
		generalTable.add(general).width(general.getWidth());
		generalTable.add(generalURL).width(generalURL.getWidth());
		
		Label facebook = new Label("Facebook: ", Assets.style_font_32_orange);
		facebook.setWrap(true);
		
		Label facebookURL = new Label("https://www.facebook.com/AyrtenMobile", Assets.style_font_32_blue);
		facebookURL.setBounds(facebookURL.getX(), facebookURL.getY(), facebookURL.getWidth(), facebookURL.getHeight());
		facebookURL.setWrap(true);
		facebookURL.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.net.openURI("https://www.facebook.com/AyrtenMobile");
			}
		});
		
		Table facebookTable = new Table(Assets.skin);
		facebookTable.add(facebook).width(facebook.getWidth());
		facebookTable.add(facebookURL).width(facebookURL.getWidth());
		
		Label twitter = new Label("Twitter: ", Assets.style_font_32_orange);
		twitter.setWrap(true);
		
		Label twitterURL = new Label("https://twitter.com/AyrtenMobile", Assets.style_font_32_blue);
		twitterURL.setWrap(true);
		twitterURL.setBounds(twitterURL.getX(), twitterURL.getY(), twitterURL.getWidth(), twitterURL.getHeight());
		twitterURL.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.net.openURI("https://twitter.com/AyrtenMobile");
			}
		});
		
		Table twitterTable = new Table(Assets.skin);
		twitterTable.add(twitter).width(twitter.getWidth());
		twitterTable.add(twitterURL).width(twitterURL.getWidth());
		
		table.add(back).left();
		table.row();
		table.add("").height(back.getStyle().font.getLineHeight()/2);
		table.row();
		table.add(msg).width(Gdx.graphics.getWidth() - back.getWidth()/5);
		table.row();
		table.add("").height(back.getStyle().font.getLineHeight()/2);
		table.row();
		table.add(generalTable).width(Gdx.graphics.getWidth() - back.getWidth()/5);
		table.row();
		table.add("").height(back.getStyle().font.getLineHeight()/2);
		table.row();
		table.add(facebookTable).left().width(Gdx.graphics.getWidth() - back.getWidth()/5);
		table.row();
		table.add("").height(back.getStyle().font.getLineHeight()/2);
		table.row();
		table.add(twitterTable).left().width(Gdx.graphics.getWidth() - back.getWidth()/5);
		
		scroll_view = new ScrollPane(table);
		scroll_view.setFillParent(true);
		scroll_view.setFlickScroll(true);
		scroll_view.updateVisualScroll();
		
		setupStage();
		stage.addActor(scroll_view);
	}
}
