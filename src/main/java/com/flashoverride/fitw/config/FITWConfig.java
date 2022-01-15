package com.flashoverride.fitw.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.flashoverride.fitw.FITW;

@Config(modid = FITW.MOD_ID, name = "FITW")
public class FITWConfig
{
    @Config.Name("Horizontal position")
    @Config.Comment("X position in pixels from the left side of the screen")
    public static int xPos = 2;

    @Config.Name("Vertical position")
    @Config.Comment("Y position in pixels from the top of the screen")
    public static int yPos = 2;

    @Config.Name("Scale")
    @Config.Comment("The scale of the message")
    public static float scale = 1.0F;

    @Config.Name("Color")
    @Config.Comment("The integer color of the message")
    public static int color = 0xFFFFFF;

    @Config.Name("Temperature")
    @Config.Comment("Include temperature in the message")
    public static boolean msgTemperature = true;

    @Config.Name("Humidity")
    @Config.Comment("Include humidity in the message")
    public static boolean msgHumidity = true;

    @Config.Name("Weather")
    @Config.Comment("Include weather in the message")
    public static boolean msgWeather = true;

    @Config.Name("Player burning")
    @Config.Comment("Display a special message when the player is burning")
    public static boolean msgBurning = true;

    @Config.Name("Player in water")
    @Config.Comment("Display a special message when the player is in water")
    public static boolean msgWater = true;

    @Mod.EventBusSubscriber(modid = FITW.MOD_ID)
    public static class EventHandler
    {
        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event)
        {
            if (event.getModID().equals(FITW.MOD_ID))
            {
                ConfigManager.sync(FITW.MOD_ID, Config.Type.INSTANCE);
            }
        }
    }
}