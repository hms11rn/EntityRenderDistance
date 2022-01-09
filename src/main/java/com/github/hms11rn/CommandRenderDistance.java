package com.github.hms11rn;

import java.util.Arrays;
import java.util.List;

import com.github.hms11rn.gui.EdrGui;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class CommandRenderDistance extends CommandBase {

	@Override
	public String getCommandName() {
		return "edr"; // command name is edr so it won't be to long.
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		String str = args.length != 0 ? args[0] : ""; // returns whether the string is empty and open the gui or its a command

		if (str.isEmpty()) {
			MinecraftForge.EVENT_BUS.register(this); // gui must be open at a tick and not a random time, so I am using tick event to do this.
			return;
		}
		if (!str.equalsIgnoreCase("Entity") && !str.equalsIgnoreCase("Player")) {
			MinecraftForge.EVENT_BUS.register(this); // Gui must be open at a tick and not a random time, so I am using tick event to do this.
			return;
		}
		if (!isInteger(args[1])) {
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "The value must be an integer, usage: /edr [Player, Entity] [Value 1-140]"));
			return;
		}
		int value = Integer.parseInt(args[1]);

		if (1 > value || 140 < value) { // Coudln't get it to work for more then 140 blocks.
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "The value must be between 1 and 140."));
			return;
		}

		if (str.equals("Entity")) {
			EntityRenderDistance.pConfig.entityR = value;
			EntityRenderDistance.pConfig.set();
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Set Entity Render Distance to " + EntityRenderDistance.edr.pConfig.entityR));
		} else if (str.equals("Player")) {
			EntityRenderDistance.pConfig.playerR = value;
			EntityRenderDistance.pConfig.set();
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Set Player Render Distance to " + EntityRenderDistance.edr.pConfig.playerR));
		}

	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {

		return true;
	}

	public boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		if (args.length == 0) {
			return newList("Entity", "Player", "Gui");
		} else
			return newList(); // returns empty tab list.
	}

	@SubscribeEvent
	public void onTick(ClientTickEvent e) {
		Minecraft.getMinecraft().displayGuiScreen(new EdrGui());
		MinecraftForge.EVENT_BUS.unregister(this);
	}

	<T> List<T> newList(T... obj) { 
		return Arrays.asList(obj);
	}
}
