package com.github.hms11rn;

import java.io.File;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

@Mod(modid = EntityRenderDistance.MODID, version = EntityRenderDistance.VERSION)
public class EntityRenderDistance {
	public static final String MODID = "erd";
	public static final String VERSION = "2.0";
	public static EntityRenderDistance erd;
	public static Config config;

	public EntityRenderDistance() {
		erd = this;
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		try {
			File configFile = new File(Loader.instance().getConfigDir().getAbsolutePath() + "/EntityRenderDistance.cfg");
			config = new Config(configFile);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		MinecraftForge.EVENT_BUS.register(new Events());
		ClientCommandHandler.instance.registerCommand(new CommandRenderDistance());
		set(config.entityR > config.playerR ? config.entityR : config.playerR + 30);
	}

	public void set(int i) {
		List<String> entityList = EntityList.getEntityNameList();
		for (String str : entityList) {
			int id = EntityList.getIDFromString(str);
			Class<? extends Entity> clazz = EntityList.getClassFromID(id);
			EntityRegistry.registerModEntity(clazz, str, id, EntityRenderDistance.erd, i, 1, true);

		}
	}
}
