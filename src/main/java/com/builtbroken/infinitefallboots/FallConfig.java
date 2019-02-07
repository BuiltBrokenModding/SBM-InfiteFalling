package com.builtbroken.infinitefallboots;

import net.minecraftforge.common.config.Config;

@Config(modid = InfiniteFallBoots.MODID, name = "infinitefallboots")
public class FallConfig {
	
	@Config.LangKey("infinitefallboots.config.min_trigger_height")
	@Config.Comment("The minimum height to fall initially in order to trigger infinite fall")
	public static int minTriggerHeight = 2;
	@Config.LangKey("infinitefallboots.config.height_to_add")
	@Config.Comment("Height that will be added to the world height for the infinite fall")
	public static int heightToAdd = 50;
	@Config.LangKey("infinitefallboots.config.damage_on_impact")
	@Config.Comment("Amount of damage to deal when impacting a block above you")
	public static float damageOnImpact = 0.5F;
	
}