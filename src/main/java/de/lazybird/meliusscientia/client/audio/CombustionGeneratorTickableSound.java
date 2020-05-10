package de.lazybird.meliusscientia.client.audio;

import de.lazybird.meliusscientia.init.ModSound;
import net.minecraft.client.audio.TickableSound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class CombustionGeneratorTickableSound extends TickableSound {

    public CombustionGeneratorTickableSound() {
        super(ModSound.combustion_generator.get(), SoundCategory.BLOCKS);
        repeat = true;
        donePlaying = false;
    }

    @Override
    public void tick() {

    }

    public boolean isRepeating(){
        return repeat;
    }

    public void setRepeating(boolean r){
        repeat = r;
    }

    public boolean donePlaying(){
        return donePlaying;
    }

    public void setDone(boolean d){
        donePlaying = d;
    }

}
