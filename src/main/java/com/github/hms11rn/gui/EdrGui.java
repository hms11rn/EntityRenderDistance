package com.github.hms11rn.gui;

import static com.github.hms11rn.EntityRenderDistance.edr;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.GuiScrollingList;
import net.minecraftforge.fml.client.config.GuiSlider;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class EdrGui extends GuiScreen {

	GuiSlider player;
	GuiSlider entity;
	GuiButton close;
	GuiScreen parent;

	public EdrGui(GuiScreen parent) {
		this.parent = parent;
	}

	@Override
	public void initGui() {

		player = new GuiSlider(0, width / 2 - 90, height / 2 - 50, 180, 20, "Player Render Distance: ", "", 1, 140,
				edr.pConfig.playerR, false, true);
		entity = new GuiSlider(0, width / 2 - 90, height / 2 - 25, 180, 20, "Entity Render Distance: ", "", 1, 140,
				edr.pConfig.entityR, false, true);
		close = new GuiButton(0, width / 2 - 30, height / 2, 60, 20, "close");

		buttonList.add(player);
		buttonList.add(entity);
		buttonList.add(close);
		super.initGui();

	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		this.drawString(fontRendererObj, "Entity Render Distance " + edr.VERSION, width / 2 - (20 * 3), height / 2 - 75, 0xb9c2b8);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (keyCode == 1) {
			edr.pConfig.entityR = entity.getValueInt();
			edr.pConfig.playerR = player.getValueInt();
			edr.pConfig.set();
			MinecraftForge.EVENT_BUS.register(this);
		}
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button == close) {
			edr.pConfig.entityR = entity.getValueInt();
			edr.pConfig.playerR = player.getValueInt();
			edr.pConfig.set();
			MinecraftForge.EVENT_BUS.register(this);
		}
	}

	@SubscribeEvent
	public void onTick(ClientTickEvent e) {
		if (parent != null) {
			Minecraft.getMinecraft().displayGuiScreen(parent);
			MinecraftForge.EVENT_BUS.unregister(this);
		} else {
			Minecraft.getMinecraft().thePlayer.closeScreen();
			MinecraftForge.EVENT_BUS.unregister(this);
		}
	}

}
