package com.builtbroken.infinitefallboots;

import org.apache.logging.log4j.Logger;

import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = InfiniteFallBoots.MODID, name = InfiniteFallBoots.NAME, version = InfiniteFallBoots.VERSION)
@Mod.EventBusSubscriber(modid = InfiniteFallBoots.MODID)
public class InfiniteFallBoots {
	public static final String MODID = "infinitefallboots";
	public static final String NAME = "Infinite Fall Boots";
	public static final String VERSION = "1.0.0";

	public static Logger logger;

	public static final EnchantmentInfiniteFall INFINITE_FALLING = new EnchantmentInfiniteFall();

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {

	}

	@SubscribeEvent
	public static void registerEnchants(Register<Enchantment> event) {
		event.getRegistry().register(INFINITE_FALLING);
	}
	
	@SubscribeEvent
	public static void onLanding(LivingFallEvent event) {
		EntityLivingBase elb = event.getEntityLiving();
		int level = EnchantmentHelper.getMaxEnchantmentLevel(INFINITE_FALLING, elb);
		if(level > 0) {
			if(event.getDistance() >= FallConfig.minTriggerHeight) {
				
				boolean notObstructed = true;
				double impactPosition = 0;
				
				for(int i = (int) elb.posY + 2; i < elb.world.provider.getHeight(); i++) {
					BlockPos pos = new BlockPos(elb.posX, i, elb.posZ);
					IBlockState state = elb.world.getBlockState(pos);
					if(state.isFullBlock() || state.isFullCube()) {
						notObstructed = false;
						impactPosition = i;
						break;
					}
				}
				
				
				if(notObstructed) {
					elb.setPosition(elb.posX, elb.world.provider.getHeight() + FallConfig.heightToAdd, elb.posZ);
					event.setDamageMultiplier(0);
				} else {
					elb.addVelocity(0, (impactPosition - elb.posY) / 2, 0);
					elb.attackEntityFrom(DamageSource.GENERIC, FallConfig.damageOnImpact);
					event.setDamageMultiplier(0);
				}
			}
		}
	}
	
	@SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent event) {
        if(event.getModID().equals(InfiniteFallBoots.MODID)) {
        	ConfigManager.sync(event.getModID(), Config.Type.INSTANCE);
        }
	}
}
