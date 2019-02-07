package com.builtbroken.infinitefallboots;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.ResourceLocation;

public class EnchantmentInfiniteFall extends Enchantment {

	protected EnchantmentInfiniteFall() {
		super(Rarity.RARE, EnumEnchantmentType.ARMOR_FEET, new EntityEquipmentSlot[] {EntityEquipmentSlot.FEET});
		this.setRegistryName(new ResourceLocation(InfiniteFallBoots.MODID, "infinitefalling"));
		this.setName("infinitefalling");
	}

}
