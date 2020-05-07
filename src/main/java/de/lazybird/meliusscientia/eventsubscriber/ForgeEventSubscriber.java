package de.lazybird.meliusscientia.eventsubscriber;

import de.lazybird.meliusscientia.effects.RadiationEffect;
import de.lazybird.meliusscientia.init.BiomeInit;
import de.lazybird.meliusscientia.init.PotionInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "meliusscientia", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventSubscriber {


    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event){

        if(event.player.world.getBiome(event.player.getPosition()) == BiomeInit.nuked_biome.get()){
            //TODO add radiation poisoning @TODO.md, add protection
            PlayerEntity player = event.player;
            if(player.isInWater() && player.getActivePotionEffect(PotionInit.radiation_effect.get()).getAmplifier() == 0){
                player.removePotionEffect(PotionInit.radiation_effect.get());
                player.addPotionEffect(new EffectInstance(PotionInit.radiation_effect.get(), RadiationEffect.timings[0][0], 1, false, false));
                return;
            }
            if(player.getActivePotionEffect(PotionInit.radiation_effect.get()) == null){
                player.addPotionEffect(new EffectInstance(PotionInit.radiation_effect.get(), RadiationEffect.timings[2][0], 2, false, false)); //23 min -> 27600 //TEST 5min
            }
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event){
        if(event.phase == TickEvent.Phase.START){

        }
    }
}
