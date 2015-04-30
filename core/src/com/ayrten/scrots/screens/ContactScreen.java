package com.ayrten.scrots.screens;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.ayrten.scrots.manager.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ContactScreen extends ScrotsScreen {
	protected ScrollPane scroll_view;

	public ContactScreen(Screen bscreen) {
		super(bscreen, true);
		setupStage();
		showTableScreen();

		Label msg = new Label("", Assets.style_font_32_white);
		msg.setText("Want to make a suggestion to improve the game? Got a bug to report? We at Ayrten strive to provide"
				+ " quick and responsive support. We would like to sincerely thank you for playing Scrots.");
		msg.setWrap(true);

		Label general = new Label("", Assets.style_font_32_white);
		general.setText("Please send all inquiries/comments to: ");
		general.setWrap(true);
		general.setWidth(general.getStyle().font
				.getBounds("Please send all inquiries/comments to: ").width);

		Label generalURL = new Label("android.ayrten@gmail.com",
				Assets.style_font_32_orange);
		generalURL.setWrap(true);
		generalURL.setBounds(generalURL.getX(), generalURL.getY(),
				generalURL.getWidth(), generalURL.getHeight());
		generalURL.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Assets.game.apk_intf
						.showToast("Copied to android.ayrten@gmail.com to clipboard!");
				Assets.game.apk_intf
						.copyTextToClipboard("android.ayrten@gmail.com");
			}
		});

		Table generalTable = new Table(Assets.skin);
		generalTable.left();
		if (general.getWidth() + generalURL.getWidth() > Gdx.graphics
				.getWidth() - back.getWidth() / 5 * 2) {
			generalTable.add(general)
					.width(Gdx.graphics.getWidth() - back.getWidth() / 5 * 2)
					.left();
			generalTable.row();
			generalTable.add(generalURL).width(
					Gdx.graphics.getWidth() - back.getWidth() / 5 * 2);
		} else {
			generalTable.add(general).width(general.getWidth()).left();
			generalTable.add(generalURL).width(generalURL.getWidth()).left();
		}

		float width = table.getWidth() - Assets.PAD * 4;

		Table social_media = new Table(Assets.skin);
		social_media.setWidth(width);

		HashMap<String, String> icon_list = new HashMap<String, String>();
		icon_list.put("data/facebook.png",
				"https://www.facebook.com/AyrtenMobile");
		icon_list.put("data/google+.png", "");
		icon_list.put("data/twitter.png", "https://twitter.com/AyrtenMobile");

		int count = 0;
		Iterator<?> it = icon_list.entrySet().iterator();
		while (it.hasNext()) {
			@SuppressWarnings("unchecked")
			final Map.Entry<String, String> pair = (Map.Entry<String, String>) it
					.next();
			Texture imageTexture = new Texture(
					Gdx.files.internal(pair.getKey()));
			imageTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			Image image = new Image(imageTexture);
			image.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					Gdx.net.openURI(pair.getValue());
				}
			});
			// image.setBounds(image.getX(), image.getY(), image.getWidth(),
			// image.getHeight());
			// social_media.add(image).width(image.getWidth()).height(image.getHeight());
			social_media.add(image).width(width / 6).height(width / 6);

			if (count != 2) {
				social_media.add().width(width / 5);
			}
			count++;
		}

		Table contacts_table = new Table(Assets.skin);
		contacts_table.setWidth(width);

		contacts_table.add(msg).width(width).padLeft(Assets.PAD);
		contacts_table.row();
		contacts_table.add().height(back.getStyle().font.getLineHeight() / 2);
		contacts_table.row();
		contacts_table.add(generalTable).width(width).padLeft(Assets.PAD)
				.left();
		contacts_table.row();
		contacts_table.add().height(back.getStyle().font.getLineHeight() / 2);
		contacts_table.row();
		contacts_table.add(social_media).width(width);

		scroll_view = new ScrollPane(contacts_table);
		scroll_view.setScrollingDisabled(true, false);

		table.add(scroll_view);
	}
}
