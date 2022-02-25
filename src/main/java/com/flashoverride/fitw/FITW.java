package com.flashoverride.fitw;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.flashoverride.fitw.client.gui.FITWGUI;

@Mod(modid = FITW.MOD_ID, name = FITW.NAME, version = FITW.VERSION, acceptedMinecraftVersions = "[1.12.2]", dependencies = "required-after:tfc", clientSideOnly = true)
public class FITW
{
    public static final String MOD_ID = "fitw";
    public static final String VERSION = "1.1.1";
    public static final String NAME = "Finger In The Wind";

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event)
    {

    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event)
    {

    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new FITWGUI(Minecraft.getMinecraft()));
    }
}