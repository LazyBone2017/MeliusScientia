package de.lazybird.meliusscientia.eventsubscriber;

import de.lazybird.meliusscientia.MeliusScientia;
import de.lazybird.meliusscientia.Util;
import de.lazybird.meliusscientia.effects.RadiationEffect;
import de.lazybird.meliusscientia.init.BiomeInit;
import de.lazybird.meliusscientia.init.ModItem;
import de.lazybird.meliusscientia.init.ModSound;
import de.lazybird.meliusscientia.init.PotionInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "meliusscientia", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventSubscriber {

    private static int geigerCounterSoundTicks = 0;
    private static int geigerCounterSoundLength = 20;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event){
        if(event.player.world.getBiome(event.player.getPosition()) == BiomeInit.nuked_biome.get()){
            PlayerEntity player = event.player;

            // Geiger-Müller Counter
            if (player.inventory.getCurrentItem().getItem().getRegistryName().toString().equals(MeliusScientia.MODID + ":geiger_counter")) {
                if (geigerCounterSoundTicks == 0) {
                    player.playSound(ModSound.geiger_counter.get(), 1, 1);
                } else if (geigerCounterSoundTicks >= geigerCounterSoundLength) {
                    geigerCounterSoundTicks = -1;
                }
                geigerCounterSoundTicks++;
            } else {
                if (geigerCounterSoundTicks > 0 && geigerCounterSoundTicks < geigerCounterSoundLength)
                    geigerCounterSoundTicks++;
                else if (geigerCounterSoundTicks >= geigerCounterSoundLength) {
                    geigerCounterSoundTicks = 0;
                }
            }
            if(Util.isWearingRPS(player))return;
            if(player.isInWater() && player.getActivePotionEffect(PotionInit.radiation_effect.get()) != null){
                if(player.getActivePotionEffect(PotionInit.radiation_effect.get()).getAmplifier() != 0)return;
                player.removePotionEffect(PotionInit.radiation_effect.get());
                player.addPotionEffect(new EffectInstance(PotionInit.radiation_effect.get(), RadiationEffect.timings[1][0], 1, false, false)); //Level 2
                return;
            }
            if(player.getActivePotionEffect(PotionInit.radiation_effect.get()) == null){
                player.addPotionEffect(new EffectInstance(PotionInit.radiation_effect.get(), RadiationEffect.timings[0][0], 0, false, false)); //Level 1
            }
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event){
        if(event.phase == TickEvent.Phase.START){

        }
    }

    @SubscribeEvent
    public static void onPlayerRightClickItem(PlayerInteractEvent.RightClickItem event){
        if(!event.getPlayer().world.isRemote && event.getItemStack().getItem() == ModItem.bottle_u03.get()){
            event.getPlayer().addPotionEffect(new EffectInstance(PotionInit.radiation_effect.get(), RadiationEffect.timings[2][0], 2, false, false)); //Level 1
        }
    }
}
