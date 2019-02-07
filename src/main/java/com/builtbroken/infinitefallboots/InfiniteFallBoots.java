package com.builtbroken.infinitefallboots;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = InfiniteFallBoots.MODID, name = InfiniteFallBoots.NAME, version = InfiniteFallBoots.VERSION)
public class InfiniteFallBoots
{
    public static final String MODID = "infinitefallboots";
    public static final String NAME = "Infinite Fall Boots";
    public static final String VERSION = "1.0.0";

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        
    }
}
