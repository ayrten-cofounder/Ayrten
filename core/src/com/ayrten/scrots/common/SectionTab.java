package com.ayrten.scrots.common;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

// Used for organizing objects into tabs (ie. a folder with tabs).
// Each tab has a table of contents that gets displayed when tab is
// touched and a highlight to show current tab is being accessed.

// When working with multiple tabs, all tabs will be in a separate
// table. The contents of each tab will be contained all in one table,
// stacked on top of each other. When a tab is selected, its contents
// are made visible via highlight() function and previous tab's contents
// are hidden.
public class SectionTab extends Stack {
	protected Table contents;
	protected Image icon;
	protected Image highlight;
	
	// Default highlight.
	public SectionTab(Image icon, Table contents) {
		this(icon, contents, null);
	}
	
	// Custom highlight.
	public SectionTab(Image icon_param, Table contents, Image hl_image) {
		this.highlight = createHighlight(hl_image);
		this.highlight.setVisible(false);
		icon = (icon_param == null) ? new Image() : icon_param;
		icon.setBounds(icon.getX(), icon.getY(), icon.getWidth(),
				icon.getHeight());
		this.contents = contents;

		add(highlight);
		add(icon);
	}
	
	private Image createHighlight(Image hl_image) {
		if(hl_image == null)
			return (new Image(Assets.rounded_rectangle_blue));
		return hl_image;
	}

	public void highlight(boolean opt) {
		highlight.setVisible(opt);
		contents.setVisible(opt);
	}
	
	public void setIconVisible(boolean opt) { icon.setVisible(opt); }
	public void setIcon(Image new_icon) { icon.setDrawable(new_icon.getDrawable()); }
	public Table getContents() { return contents; }
}
