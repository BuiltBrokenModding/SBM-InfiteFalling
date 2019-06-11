package com.builtbroken.infinitefallboots;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(value = InfiniteFallBoots.MODID)
@Mod.EventBusSubscriber(modid = InfiniteFallBoots.MODID, bus = Mod.EventBusSubscriber.Bus.MOD) // mod bus for registry
public class InfiniteFallBoots {
    public static final String MODID = "infinitefallboots";

    public static final Enchantment INFINITE_FALLING = new ModEnchantment(Rarity.RARE, EnchantmentType.ARMOR_FEET, new EquipmentSlotType[] {EquipmentSlotType.FEET}).setRegistryName(new ResourceLocation(InfiniteFallBoots.MODID, "infinitefalling"));
    
    public InfiniteFallBoots() {
        FallConfig.setupConfig();
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, FallConfig.SERVER_CONFIG);
    }
    
    @SubscribeEvent
    public static void registerEnchants(Register<Enchantment> event) {
        event.getRegistry().register(INFINITE_FALLING);
    }

    @Mod.EventBusSubscriber(modid = InfiniteFallBoots.MODID)
    public static class FallHandler {

        @SubscribeEvent
        public static void onLanding(LivingFallEvent event) {
            LivingEntity elb = event.getEntityLiving();
            int level = EnchantmentHelper.getMaxEnchantmentLevel(INFINITE_FALLING, elb);
            if(level > 0) {
                if(event.getDistance() >= FallConfig.FALL_CONFIG.minTriggerHeight.get()) {

                    boolean notObstructed = true;
                    double impactPosition = 0;

                    for(int i = (int) elb.posY + 2; i < elb.world.getHeight(); i++) {
                        BlockPos pos = new BlockPos(elb.posX, i, elb.posZ);
                        BlockState state = elb.world.getBlockState(pos);
                        if(state.isNormalCube(elb.getEntityWorld(), pos)) {
                            notObstructed = false;
                            impactPosition = i;
                            break;
                        }
                    }


                    if(notObstructed) {
                        elb.setPosition(elb.posX, elb.world.getHeight() + FallConfig.FALL_CONFIG.heightToAdd.get(), elb.posZ);
                        event.setDamageMultiplier(0);
                    } else {
                        elb.addVelocity(0, (impactPosition - elb.posY) / 2, 0);
                        elb.attackEntityFrom(DamageSource.GENERIC, FallConfig.FALL_CONFIG.damageOnImpact.get().floatValue());
                        event.setDamageMultiplier(0);
                    }
                }
            }
        }

    }
    
    public static class ModEnchantment extends Enchantment {

        protected ModEnchantment(Enchantment.Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots) {
            super(rarityIn, typeIn, slots);
        }

    }
    
    public static class FallConfig {
        
        public static FallConfig FALL_CONFIG = null;

        public static ForgeConfigSpec SERVER_CONFIG = null;

        public static void setupConfig() {
            final Pair<FallConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(FallConfig::new);
            SERVER_CONFIG = specPair.getRight();
            FALL_CONFIG = specPair.getLeft();
        }
        
        /* Non-static members */
        
        public final ForgeConfigSpec.IntValue minTriggerHeight;
        public final ForgeConfigSpec.IntValue heightToAdd;
        public final ForgeConfigSpec.DoubleValue damageOnImpact;

        protected FallConfig(ForgeConfigSpec.Builder builder) {
            this.minTriggerHeight = builder.comment("The minimum height to fall initially in order to trigger infinite fall").defineInRange("min_trigger_height", 2, 0, Integer.MAX_VALUE);
            this.heightToAdd = builder.comment("Height that will be added to the world height for the infinite fall").defineInRange("height_to_add", 50, Integer.MIN_VALUE, Integer.MAX_VALUE);
            this.damageOnImpact = builder.comment("Amount of damage to deal when impacting a block above you").defineInRange("damage_on_impact", 0.5F, 0, Double.MAX_VALUE);
        }

    }

}
