package com.flashoverride.fitw.util;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import com.flashoverride.fitw.config.FITWConfig;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.world.classic.biomes.BiomesTFC;

public class FITWTemperature
{
    public static String getTemp(World world, EntityPlayer player)
    {
        int posX = MathHelper.floor(player.posX);
        int posY = MathHelper.floor(player.getEntityBoundingBox().minY - 1);
        int posZ = MathHelper.floor(player.posZ);
        BlockPos pos = new BlockPos(posX, posY, posZ);

        float temperature = ClimateTFC.getActualTemp(world, pos);
        float rainfall = ClimateTFC.getRainfall(world, pos);

        int wetCooldown = 0;

        String temperatureFuzzy;
        String humidity;
        String conjunction = " " + new TextComponentTranslation("msg.fitw.conjunction.and").getFormattedText() + " ";

        boolean hotSpring = false;

        if (rainfall < 50) humidity = new TextComponentTranslation("msg.fitw.humidity.very_dry").getFormattedText();
        else if (rainfall < 100) humidity = new TextComponentTranslation("msg.fitw.humidity.dry").getFormattedText();
        else if (rainfall < 150) humidity = new TextComponentTranslation("msg.fitw.humidity.bland").getFormattedText();
        else if (rainfall < 200) humidity = new TextComponentTranslation("msg.fitw.humidity.damp").getFormattedText();
        else if (rainfall < 300) humidity = new TextComponentTranslation("msg.fitw.humidity.humid").getFormattedText();
        else if (rainfall < 450) humidity = new TextComponentTranslation("msg.fitw.humidity.very_humid").getFormattedText();
        else humidity = new TextComponentTranslation("msg.fitw.humidity.wet").getFormattedText();

        if (world.getBiome(pos).equals(BiomesTFC.SWAMPLAND)) humidity = new TextComponentTranslation("msg.fitw.humidity.swampy").getFormattedText();

        if (FITWConfig.msgWeather && world.isRaining())
        {
            conjunction = " " + new TextComponentTranslation("msg.fitw.conjunction.and").getFormattedText() + " ";
            if (world.isThundering()) humidity = new TextComponentTranslation("msg.fitw.weather.stormy").getFormattedText();
            else if (world.getBiome(pos).getEnableSnow()) humidity = new TextComponentTranslation("msg.fitw.weather.snowy").getFormattedText();
            else humidity = new TextComponentTranslation("msg.fitw.weather.rainy").getFormattedText();
        }

        if (temperature < -30) temperatureFuzzy = new TextComponentTranslation("msg.fitw.temperature.deathly_cold").getFormattedText();
        else if (temperature < -20) temperatureFuzzy = new TextComponentTranslation("msg.fitw.temperature.bone_chillingly_cold").getFormattedText();
        else if (temperature < -10) temperatureFuzzy = new TextComponentTranslation("msg.fitw.temperature.extremely_cold").getFormattedText();
        else if (temperature < 0) temperatureFuzzy = new TextComponentTranslation("msg.fitw.temperature.freezing").getFormattedText();
        else if (temperature < 5) temperatureFuzzy = new TextComponentTranslation("msg.fitw.temperature.very_cold").getFormattedText();
        else if (temperature < 10) temperatureFuzzy = new TextComponentTranslation("msg.fitw.temperature.cold").getFormattedText();
        else if (temperature < 15) temperatureFuzzy = new TextComponentTranslation("msg.fitw.temperature.cool").getFormattedText();
        else if (temperature < 20) temperatureFuzzy = new TextComponentTranslation("msg.fitw.temperature.nice").getFormattedText();
        else if (temperature < 25) temperatureFuzzy = new TextComponentTranslation("msg.fitw.temperature.warm").getFormattedText();
        else if (temperature < 30) temperatureFuzzy = new TextComponentTranslation("msg.fitw.temperature.hot").getFormattedText();
        else if (temperature < 35) temperatureFuzzy = new TextComponentTranslation("msg.fitw.temperature.very_hot").getFormattedText();
        else temperatureFuzzy = new TextComponentTranslation("msg.fitw.temperature.burning_hot").getFormattedText();

        if (FITWConfig.msgWater && player.isInWater())
        {
            wetCooldown = 40;
        }

        if (FITWConfig.msgBurning && player.isBurning()) return new TextComponentTranslation("msg.fitw.burning").getFormattedText();
        else if (FITWConfig.msgWater && wetCooldown > 0)
        {
            int blockY = MathHelper.floor(player.getEntityBoundingBox().maxY);
            Block block;
            while (blockY >= MathHelper.floor(player.getEntityBoundingBox().minY) - 1)
            {
                block = world.getBlockState(pos).getBlock();
                if (block.equals(FluidsTFC.HOT_WATER.get().getBlock()))
                {
                    hotSpring = true;
                }
                --blockY;
            }
            wetCooldown -= 1;
            if (hotSpring) return new TextComponentTranslation("msg.fitw.hot_spring").getFormattedText();
            else return new TextComponentTranslation("msg.fitw.water").getFormattedText() + " " + temperatureFuzzy;
        }
        else if (FITWConfig.msgTemperature && FITWConfig.msgHumidity) return new TextComponentTranslation("msg.fitw.air").getFormattedText() + " " + temperatureFuzzy + conjunction + humidity;
        else if (FITWConfig.msgTemperature && !temperatureFuzzy.equals("")) return new TextComponentTranslation("msg.fitw.air").getFormattedText() + " " + temperatureFuzzy;
        else if (FITWConfig.msgHumidity && !humidity.equals("")) return new TextComponentTranslation("msg.fitw.air").getFormattedText() + " " + humidity;
        else return null;
    }
}