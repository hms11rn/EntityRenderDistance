package com.github.hms11rn;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

@Mod(modid = EntityRenderDistance.MODID, version = EntityRenderDistance.VERSION)
public class EntityRenderDistance {
	public static final String MODID = "edr";
	public static final String VERSION = "2.0";
	public static EntityRenderDistance edr;
	public static Config pConfig;
	public Config config;

	public EntityRenderDistance() {
		edr = this;
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		try {
			config = new Config();
			pConfig = config;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		MinecraftForge.EVENT_BUS.register(new Events());
		ClientCommandHandler.instance.registerCommand(new CommandRenderDistance());
		set(pConfig.entityR > pConfig.playerR ? pConfig.entityR : pConfig.playerR + 30);
	}

	public void set(int i) {
		List<String> entityList = EntityList.getEntityNameList();
		for (String str : entityList) {
			int id = EntityList.getIDFromString(str);
			Class<? extends Entity> clazz = EntityList.getClassFromID(id);
			EntityRegistry.registerModEntity(clazz, str, id, EntityRenderDistance.edr, i, 1, true);

		}
	}
}
