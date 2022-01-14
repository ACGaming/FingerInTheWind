package com.flashoverride.fitw.util;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.util.climate.ClimateTFC;

public class FITWTemperature
{
    public static String getTemp(World world, EntityPlayer player)
    {
        int posX = MathHelper.floor(player.posX);
        int posY = MathHelper.floor(player.getEntityBoundingBox().minY - 1);
        int posZ = MathHelper.floor(player.posZ);
        BlockPos pos = new BlockPos(posX, posY, posZ);

        float temp = ClimateTFC.getActualTemp(world, pos);
        float rain = ClimateTFC.getRainfall(world, pos);

        int wet_cooldown = 0;

        String fuzzy_temp;
        String humidity = "";
        String conjunction = " and ";

        boolean hot_spring = false;

        if (rain < 50) humidity = "very dry";
        else if (rain < 100) humidity = "dry";
        else if (rain < 150) conjunction = "";
        else if (rain < 200) humidity = "damp";
        else if (rain < 300) humidity = "humid";
        else if (rain < 450) humidity = "very humid";
        else humidity = "wet";

        if (world.isRaining())
        {
            conjunction = " and ";
            if (world.isThundering()) humidity = "stormy";
            else if (world.canSnowAt(pos, false)) humidity = "snowy";
            else humidity = "rainy";
        }

        if (temp < -30) fuzzy_temp = "deathly cold";
        else if (temp < -20) fuzzy_temp = "bone-chillingly cold";
        else if (temp < -10) fuzzy_temp = "extremely cold";
        else if (temp < 0) fuzzy_temp = "freezing";
        else if (temp < 5) fuzzy_temp = "very cold";
        else if (temp < 10) fuzzy_temp = "cold";
        else if (temp < 15) fuzzy_temp = "cool";
        else if (temp < 20) fuzzy_temp = "nice";
        else if (temp < 25) fuzzy_temp = "warm";
        else if (temp < 30) fuzzy_temp = "hot";
        else if (temp < 35) fuzzy_temp = "very hot";
        else fuzzy_temp = "burning hot";

        if (player.isInWater())
        {
            wet_cooldown = 40;
        }

        if (player.isBurning()) return "The air is filled with smoke and the stench of burning flesh";
        else if (wet_cooldown > 0)
        {
            int blockY = MathHelper.floor(player.getEntityBoundingBox().maxY);
            Block block;

            while (blockY >= MathHelper.floor(player.getEntityBoundingBox().minY) - 1)
            {
                block = world.getBlockState(pos).getBlock();

                if (block.equals(FluidsTFC.HOT_WATER.get().getBlock()))
                {
                    hot_spring = true;
                }
                --blockY;
            }

            wet_cooldown -= 1;
            if (hot_spring) return "The water feels rejuvenating";
            else return "The water feels " + fuzzy_temp;
        }
        else return "The air feels " + fuzzy_temp + conjunction + humidity;
    }
}