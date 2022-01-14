package com.flashoverride.fitw.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.flashoverride.fitw.config.FITWConfig;
import com.flashoverride.fitw.util.FITWTemperature;

public class FITWGUI extends Gui
{
    private final Minecraft mc;

    public FITWGUI(Minecraft mc)
    {
        super();
        this.mc = mc;
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onRenderHotBar(RenderGameOverlayEvent event)
    {
        if (event.isCancelable() || event.getType() != ElementType.HOTBAR || mc.gameSettings.showDebugInfo) return;
        FontRenderer fontRenderer = this.mc.fontRenderer;
        fontRenderer.drawStringWithShadow(FITWTemperature.getTemp(mc.world, mc.player), FITWConfig.xPos_actual, FITWConfig.yPos_actual, FITWConfig.color_actual);
    }
}