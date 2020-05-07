package de.lazybird.meliusscientia.init;

import de.lazybird.meliusscientia.MeliusScientia;
import de.lazybird.meliusscientia.effects.RadiationEffect;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class PotionInit {

    public static final DeferredRegister<Effect> EFFECTS = new DeferredRegister<>(ForgeRegistries.POTIONS,
            MeliusScientia.MODID);

    public static final DeferredRegister<Potion> POTIONS = new DeferredRegister<>(ForgeRegistries.POTION_TYPES,
            MeliusScientia.MODID);



    public static final RegistryObject<Effect> radiation_effect = EFFECTS.register("radiation_effect",
            () -> new RadiationEffect(EffectType.HARMFUL, 37848743));

    public static final RegistryObject<Potion> radiation_potion = POTIONS.register("radiation_potion",
            () -> new Potion(new EffectInstance(radiation_effect.get())));
}
