package com.github.hms11rn;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.EntityList;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;

public class Config {

	public int playerR = 68; // 68 default
	public int entityR = 68; // 68 default
	Configuration c;
	public List<String> entities = EntityList.getEntityNameList();
	public Map<String, Integer> entityValues;
	public String splitter = "?:/'split'/:?";

	public Config(File file) {
		entityValues = createMapFromList(entities);
		c = new Configuration(file);
		c.load();
		playerR = c.get("Player_Render_Distance", "Player", 68).getInt();
		entityR = c.get("Entity_Render_Distance", "Entity_General", 68).getInt();

		for (String entity : entities) { // This is going to be an update in the future once I get more time to make a better gui.
			entityValues.put(entity, c.get("Entity_Render_Distance", entity, 68).getInt());
		}
		EntityRenderDistance.erd.set(entityR > playerR ? entityR : playerR + 30);
		c.save();
	}

	/**
	 * Set the Config from entityR and playerR to file.
	 */
	public void set() {
		c.load();
		c.get("Player_Render_Distance", "Player", 68).set(playerR);
		c.get("Entity_Render_Distance", "Entity_General", 68).set(entityR);
		playerR = c.get("Player_Render_Distance", "Player", 68).getInt();
		entityR = c.get("Entity_Render_Distance", "Entity_General", 68).getInt();
		for (String entity : entities) { 
			entityValues.put(entity, c.get("Entity_Render_Distance", entity, 68).getInt());
		}
		EntityRenderDistance.erd.set(entityR > playerR ? entityR : playerR + 30); // This is an important part of this because otherwise changing the entity rendering weight won't make a difference
		c.save();
	}

	Map<String, Integer> createMapFromList(List<String> list) {
		HashMap<String, Integer> ret = new HashMap<String, Integer>();
		for (String str : list) {
			ret.put(str, 68);
		}
		return ret;
	}
}
