package com.ayrten.scrots.common;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

// Used for organizing objects into tabs (ie. a folder with tabs).
// Each tab has a table and a highlight associated with it.
public class SectionTab extends Stack {
	protected Table contents;
	protected Image icon;
	protected Image highlight;
	
	public SectionTab(Texture texture, Table contents) {
		icon = new Image(texture);
		highlight = new Image(Assets.rounded_rectangle_border_blue);
		highlight.setVisible(false);
		icon.setBounds(icon.getX(), icon.getY(), icon.getWidth(),
				icon.getHeight());
		this.contents = contents;

		add(highlight);
		add(icon);
	}

	public void highlight(boolean opt) {
		highlight.setVisible(opt);
		contents.setVisible(opt);
	}
	
	public Table getContents() { return contents; }
}
