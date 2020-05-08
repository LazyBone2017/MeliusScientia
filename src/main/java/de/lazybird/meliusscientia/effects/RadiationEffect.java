package de.lazybird.meliusscientia.effects;

import de.lazybird.meliusscientia.init.BiomeInit;
import de.lazybird.meliusscientia.init.PotionInit;
import de.lazybird.meliusscientia.util.ModDamageSources;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RadiationEffect extends Effect {

    private int duration;
    private final Random random = new Random();
    public static int[][] timings = { //at duration == x, first: total length, second, timing of secondary effect
            {15600, 2400}, // level 1
            {15600, 6000}, // level 2
            {20400, 13200, 4800}  // level 3
    };

    Effect[] randomEffects = {Effects.ABSORPTION, Effects.HASTE, Effects.JUMP_BOOST, Effects.REGENERATION, Effects.STRENGTH};

    public RadiationEffect(EffectType typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        if(entityLivingBaseIn.world.isRemote())return;
        switch (amplifier){
            case 0: { //in biome
                if(duration == timings[0][0]) {
                    System.out.println("RADIATION 1");
                    if(prob(5, 50))entityLivingBaseIn.addPotionEffect(new EffectInstance(Effects.NAUSEA, 1200, 0, true, false)); //TEST for 1 min
                }
                else if(duration == timings[0][0] / 2 && entityLivingBaseIn.world.getBiome(entityLivingBaseIn.getPosition()) == BiomeInit.nuked_biome.get()){
                        entityLivingBaseIn.removePotionEffect(PotionInit.radiation_effect.get());
                        entityLivingBaseIn.addPotionEffect(new EffectInstance(PotionInit.radiation_effect.get(), timings[1][0], 1));
                }
                else if(duration == timings[0][1]){
                    entityLivingBaseIn.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 2400, 0, true, false));
                    entityLivingBaseIn.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 1200, 0, true, false));
                }
                else if(duration == 1){
                    if(prob(10, 10))entityLivingBaseIn.addPotionEffect(new EffectInstance(Effects.POISON, 200, 0, true, false));
                }
                break;
            }
            case 1: { //in contaminated water or level up
                if(duration == timings[1][0]) {
                    System.out.println("RADIATION 2");
                    if(prob(50, 100))entityLivingBaseIn.addPotionEffect(new EffectInstance(Effects.NAUSEA, 2400, 0, true, false));
                }
                else if(duration == timings[1][1]){
                    entityLivingBaseIn.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 6000, 0, true, false));
                    entityLivingBaseIn.addPotionEffect(new EffectInstance(Effects.HUNGER, 5000, 0, true, false));
                }
                else if(duration == 1){
                    if(prob(50, 50))entityLivingBaseIn.addPotionEffect(new EffectInstance(Effects.POISON, 200, 1, true, false));
                }
                break;
            }
            case 2: { //ingested contaminated material
                if(duration == timings[2][0]) {
                    System.out.println("RADIATION 3");
                    entityLivingBaseIn.addPotionEffect(new EffectInstance(Effects.NAUSEA, 3600, 0, true, false));
                }
                else if(duration == timings[2][1]){
                    entityLivingBaseIn.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 8400, 0, true, false));
                    entityLivingBaseIn.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 2400, 0, true, false));
                    entityLivingBaseIn.addPotionEffect(new EffectInstance(Effects.BLINDNESS, 1200, 0, true, false));
                }
                else if(duration == timings[2][2]){
                    //TODO add prob
                    //WALKING GHOST PHASE
                    if(!prob(20, 50)){
                        entityLivingBaseIn.removePotionEffect(PotionInit.radiation_effect.get());
                        return;
                    }
                    addRandomEffect(entityLivingBaseIn, 300);
                }
                else if(duration == timings[2][2] - 300){
                    addRandomEffect(entityLivingBaseIn, 1200);
                }
                else if(duration == timings[2][2] - 1500){
                    addRandomEffect(entityLivingBaseIn, 2000);
                }
                else if(duration == timings[2][2] - 3500){
                    addRandomEffect(entityLivingBaseIn, 100);
                }
                //DEATH
                else if(duration == timings[2][2] - 3600){
                    entityLivingBaseIn.addPotionEffect(new EffectInstance(Effects.POISON, 2400));
                }
                else if(duration < timings[2][2] - 3600 && duration % 100 == 0){
                    entityLivingBaseIn.addPotionEffect(new EffectInstance(Effects.BLINDNESS, 20, 0, true, false));
                    entityLivingBaseIn.attackEntityFrom(ModDamageSources.radiationDamage, 1);
                }

            }
        }
    }

    private void addRandomEffect(LivingEntity player, int rDuration){
        player.addPotionEffect(new EffectInstance(randomEffects[random.nextInt(randomEffects.length)], rDuration, 0, true, false));
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        this.duration = duration;
        return true;
    }

    private boolean prob(int percentMin, int percentMax){
        System.out.println(percentMin + "% - " + percentMax + "%");
        int dif = percentMax - percentMin;
        int percent = percentMin + ( dif != 0 ? random.nextInt(dif) : dif);
        boolean hit = random.nextInt(100) >= percent;
        System.out.println("HIT? " + hit);
        return hit;
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        //ret.add(new ItemStack(Items.MILK_BUCKET));
        return ret;
    }
}
