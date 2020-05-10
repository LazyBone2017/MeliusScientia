package de.lazybird.meliusscientia.init;

import de.lazybird.meliusscientia.MeliusScientia;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModSound {

    public static final DeferredRegister<SoundEvent> SOUNDS = new DeferredRegister<>(ForgeRegistries.SOUND_EVENTS, MeliusScientia.MODID);

    public static final RegistryObject<SoundEvent> geiger_counter = SOUNDS.register("geiger_counter", () -> new SoundEvent(new ResourceLocation(MeliusScientia.MODID, "geiger_counter")));
    public static final RegistryObject<SoundEvent> combustion_generator = SOUNDS.register("combustion_generator", () -> new SoundEvent(new ResourceLocation(MeliusScientia.MODID, "combustion_generator")));
}
