package com.ayrten.scrots.manager;

import java.util.HashMap;
import java.util.Map;

import com.ayrten.scrots.common.Assets;
import com.badlogic.gdx.Gdx;

public class ItemManager {
	protected Map<String, Object> map;
	protected String MAP_FILENAME = "im";
	// For items that need to be unlocked.
	protected String UNLOCK_FORMAT = "%s_unlock";
	// For items that have an amount (ie. PowerDot)
	protected String COUNT_FORMAT = "%s_count";
	
	public ItemManager() {
		// Delete old file. Should remove in future iteration.
		if(Gdx.files.local("pd").exists())
			Gdx.files.local("pd").delete();
		
		map = getMappings();
	}
	
	public boolean isItemUnlocked(String item_name) {
		String key = String.format(UNLOCK_FORMAT, item_name);
		if(map.containsKey(key))
			return (Boolean) map.get(key);
		return false;
	}
	
	public void unlockItem(String item_name) {
		String key = String.format(UNLOCK_FORMAT, item_name);
		map.put(key, true);
	}

	public int getItemCount(String item_name) {
		String key = String.format(COUNT_FORMAT, item_name);
		if(map.containsKey(key))
			return (Integer) (map.get(key));
		return 0;
	}

	public void setItemCount(String item_name, int count) {
		String key = String.format(COUNT_FORMAT, item_name);
		map.put(key, count);
	}

	public HashMap<String, Object> getMappings() {
		String contents = Assets.readFile(MAP_FILENAME);

		if (!contents.isEmpty())
			return (Assets.json.fromJson(HashMap.class, contents));
		return (new HashMap<String, Object>());
	}

	public void dispose() {
		Assets.writeFile(MAP_FILENAME, Assets.json.toJson(map));
	}
}
