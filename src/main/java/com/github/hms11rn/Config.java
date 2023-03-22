package com.github.hms11rn;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.EntityList;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;

/**
 * Config file used just for this mod. <br>
 * this class contains playerR, entityR playerR is Player Render Distance,
 * entityR is Entity Render Distance
 * 
 * @author hms11
 *
 */
public class Config {

	/**
	 * Player Render Distance value.
	 */
	public int playerR = 68; // 68 default
	/**
	 * Entity Render Distance value.
	 */

	public int entityR = 68; // 68 default
	/**
	 * Forge config class.
	 */
	Configuration c;
	/**
	 * List of all entities, this is not being used right now
	 * but in future versions its going to be used for
	 * Map entityValues.
	 */
	public List<String> entities = EntityList.getEntityNameList();
	/**
	 * Currently not being used, in future versions this is going to be
	 * used to be able to apply different values to each entity
	 */
	public Map<String, Integer> entityValues;

	public  boolean liveUpdate = false;

	/**
	 *
	 * @param file
	 */
	public Config(File file) {
		entityValues = createMapFromList(entities);
		c = new Configuration(file);
		c.load();
		playerR = c.get("Player_Render_Distance", "Player", 68).getInt();
		entityR = c.get("Entity_Render_Distance", "Entity_General", 68).getInt();

		for (String entity : entities) { // This is going to be an update in the future once I get more time to make a
											// better gui.
			entityValues.put(entity, c.get("Entity_Render_Distance", entity, 68).getInt());
		}
		EntityRenderDistance.erd.set(entityR > playerR ? entityR : playerR + 30); // TODO This is using static access right now
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
		EntityRenderDistance.erd.set(entityR > playerR ? entityR : playerR + 30); // This is an important part of this
																					// because otherwise changing the
																					// entity rendering weight won't
																					// make a difference
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
