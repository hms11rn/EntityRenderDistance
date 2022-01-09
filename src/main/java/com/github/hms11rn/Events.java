package com.github.hms11rn;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class Events {

	/**
	 * This is the main part of this mod, this changes renderDistanceWeight of each entity,
	 * diving by 68 makes me be able to convert the actual distance to the proper weight
	 * that Minecraft uses.
	 */
	@SubscribeEvent
	public void clientTicked(ClientTickEvent e) {

		if (Minecraft.getMinecraft().theWorld != null) {
			List<Entity> entities = Minecraft.getMinecraft().theWorld.loadedEntityList;

			for (Entity t : entities) {
				if (t instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer) t;
					double i = ((double) EntityRenderDistance.pConfig.playerR) / 68;
					t.renderDistanceWeight = i;
				} else {
					double i = ((double) EntityRenderDistance.pConfig.entityR) / 68;
					t.renderDistanceWeight = i;
				}
			}
		}

	}
}
