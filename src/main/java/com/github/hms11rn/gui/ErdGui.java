package com.github.hms11rn.gui;


import java.io.IOException;

import com.github.hms11rn.EntityRenderDistance;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.config.GuiSlider;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

/**
 * Gui of EntityRenderDistance mod.
 */
public class ErdGui extends GuiScreen {

    GuiSlider playerSlider;
    GuiSlider entitySlider;

    GuiButton closeButton;
    GuiScreen parent;
    /**
     * Chose if to update settings while in menu (before applying) or not.
     */
    GuiButton liveUpdateButton;

    /**
     * Getting parent of class for when it's time to close gui.
     * @param parent
     */
    public ErdGui(GuiScreen parent) {
        this.parent = parent;
    }

    /**
     * Crates all buttons and sliders,
     * <br>playerSlider - change Player render distance
     * <br>entitySlider - change Entity render distance
     * <br>closeButton - close and apply changes
     * <br>liveUpdateButton - chose if it should update settings live or not.
     */
    @Override
    public void initGui() {

        playerSlider = new GuiSlider(0, width / 2 - 90, height / 2 - 50, 180, 20, "Player Render Distance: ", "", 1, 140, EntityRenderDistance.config.playerR, false, true);
        entitySlider = new GuiSlider(0, width / 2 - 90, height / 2 - 25, 180, 20, "Entity Render Distance: ", "", 1, 140, EntityRenderDistance.config.entityR, false, true);
        closeButton = new GuiButton(0, width / 2 - 30, height / 2, 60, 20, "close");

        liveUpdateButton = new GuiButton(0, width - 120, 10, 110, 20, EntityRenderDistance.config.liveUpdate ? "Disable" + " Live Update" : "Enable" + " Live Update");

        buttonList.add(playerSlider);
        buttonList.add(entitySlider);
        buttonList.add(liveUpdateButton);
        buttonList.add(closeButton);
        super.initGui();

    }

    /**
     * used to draw EntityRenderDistance above sliders.
     * @param mouseX
     * @param mouseY
     * @param partialTicks
     */
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawString(fontRendererObj, "Entity Render Distance " + EntityRenderDistance.VERSION, width / 2 - 60, height / 2 - 75, 0xb9c2b8);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    /**
     * Checks if Escape key was typed, if so it's going to close the window
     * and apply changes (same thing as "close" button).
     *
     * @param typedChar
     * @param keyCode
     * @throws IOException
     */
    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == 1) {
            EntityRenderDistance.config.entityR = entitySlider.getValueInt();
            EntityRenderDistance.config.playerR = playerSlider.getValueInt();
            EntityRenderDistance.config.set();
            MinecraftForge.EVENT_BUS.register(this);
        }
    }

    /**
     * When a button is pressed, this method is called.
     * There is going to be 4 buttons that can be pressed,
     * <br> #1 is "close" when close button is pressed.
     * <br> #2 and #3 are going to be either playerSlider or EntitySlider,
     * when anything is done with them, if "liveUpdate" is enabled,
     * it's going to apply changes immediately.
     * <br> #4 is "liveUpdate" when enabled moving the sliders
     * is going to apply changes immediately
     *<br><br>
     * buttons 1,2,3 are going to change "config.entityR" and "config.playerR" to the
     * value of their associated sliders, then they call config.set().
     *
     *
     * @param button  the button that has been pressed.
     */
    @Override
    public void actionPerformed(GuiButton button) {
        if (button == closeButton) {
            EntityRenderDistance.config.entityR = entitySlider.getValueInt();
            EntityRenderDistance.config.playerR = playerSlider.getValueInt();
            EntityRenderDistance.config.set();
            MinecraftForge.EVENT_BUS.register(this);
        } else if ((button == entitySlider || button == playerSlider) && EntityRenderDistance.config.liveUpdate) {
            EntityRenderDistance.config.entityR = entitySlider.getValueInt();
            EntityRenderDistance.config.playerR = playerSlider.getValueInt();
        } else if (button == liveUpdateButton) {
            if (EntityRenderDistance.config.liveUpdate) {
                liveUpdateButton.displayString = "Enable live update";
                EntityRenderDistance.config.liveUpdate = false;
            } else {
                liveUpdateButton.displayString = "Disable live update";
                EntityRenderDistance.config.liveUpdate = true;
            }
        }

    }

    /**
     * Tick event. <br> There is a bug in Minecraft that if you try to open a GUI,
     * when code is not synced with a tick, it won't open, therefore the solution is
     * to set a Tick event and when you are trying to open a GUI, you Register the class
     * to forge Event Bus, then tick event is run, and you can open GUI while being synced with a Tick.
     *<br><br>
     * I saw this trick in Sk1er's discord server.
     * @param e Event input, not used here.
     */
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
