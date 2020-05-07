package de.lazybird.meliusscientia.effects;

import de.lazybird.meliusscientia.init.BiomeInit;
import de.lazybird.meliusscientia.init.PotionInit;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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
            {6000, 2400}, // level 1
            {6000, 2400}, // level 2
            {2400, 1200}  // level 3
    };

    public RadiationEffect(EffectType typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        if(entityLivingBaseIn.world.isRemote())return;
        switch (amplifier){
            //TODO add probabilities
            case 0: { //in biome
                if(duration == timings[0][0]) {
                    System.out.println("RADIATION 1");
                    if(prob(5, 50))entityLivingBaseIn.addPotionEffect(new EffectInstance(Effects.NAUSEA, 1200, 0, false, false)); //TEST for 1 min
                }
                else if(duration == timings[0][0] / 2 && entityLivingBaseIn.world.getBiome(entityLivingBaseIn.getPosition()) == BiomeInit.nuked_biome.get()){
                        entityLivingBaseIn.removePotionEffect(PotionInit.radiation_effect.get());
                        entityLivingBaseIn.addPotionEffect(new EffectInstance(PotionInit.radiation_effect.get(), timings[1][0], 1));
                }
                else if(duration == timings[0][1]){
                    entityLivingBaseIn.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 1200, 0, false, false)); //TEST for 1 min
                }
                else if(duration == 1){
                    if(prob(10, 10))entityLivingBaseIn.addPotionEffect(new EffectInstance(Effects.POISON, 200, 0, false, false));
                }
                break;
            }
            case 1: { //in contaminated water or level up
                if(duration == timings[1][0]) {
                    System.out.println("RADIATION 2");
                    if(prob(50, 100))entityLivingBaseIn.addPotionEffect(new EffectInstance(Effects.NAUSEA, 1200, 0, false, false)); //TEST for 1 min
                }
                else if(duration == timings[1][1]){
                    entityLivingBaseIn.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 1200, 0, false, false)); //TEST for 1 min
                }
                else if(duration == 1){
                    if(prob(50, 50))entityLivingBaseIn.addPotionEffect(new EffectInstance(Effects.POISON, 200, 1, false, false));
                }
                break;
            }
            case 2: { //ingested contaminated material
                if(duration == timings[2][0]) {
                    System.out.println("RADIATION 3");
                    entityLivingBaseIn.addPotionEffect(new EffectInstance(Effects.NAUSEA, 1200, 0, false, false)); //TEST for 1 min
                }
                else if(duration == timings[2][1]){
                    entityLivingBaseIn.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 1200, 0, false, false)); //TEST for 1 min
                }
                else if(duration == 1){
                    entityLivingBaseIn.addPotionEffect(new EffectInstance(Effects.POISON, 200, 0, false, false));
                }
                //TODO walking ghost phase
            }
        }
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
