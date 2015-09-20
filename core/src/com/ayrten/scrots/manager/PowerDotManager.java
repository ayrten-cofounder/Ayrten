package com.ayrten.scrots.manager;

import java.util.HashMap;
import java.util.Map;

import com.ayrten.scrots.common.Assets;
import com.badlogic.gdx.Gdx;

public class PowerDotManager {
	protected Map<String, Object> map;
	protected String MAP_FILENAME = "pdm";
	protected String UNLOCK_FORMAT = "%s_unlock";
	protected String COUNT_FORMAT = "%s_count";
	
	public PowerDotManager() {
		// Delete old file. Should remove in future iteration.
		if(Gdx.files.local("pd").exists())
			Gdx.files.local("pd").delete();
		
		map = getMappings();
	}
	
	public boolean isDotUnlocked(Class<?> clazz) {
		String key = String.format(UNLOCK_FORMAT, clazz.getSimpleName());
		if(map.containsKey(key))
			return (Boolean) map.get(key);
		return false;
	}
	
	public void unlockDot(Class<?> clazz) {
		String key = String.format(UNLOCK_FORMAT, clazz.getSimpleName());
		map.put(key, true);
	}

	public int getDotCount(Class<?> clazz) {
		String key = String.format(COUNT_FORMAT, clazz.getSimpleName());
		if(map.containsKey(key))
			return (Integer) (map.get(key));
		return 0;
	}

	public void setDotCount(Class<?> clazz, int count) {
		String key = String.format(COUNT_FORMAT, clazz.getSimpleName());
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
