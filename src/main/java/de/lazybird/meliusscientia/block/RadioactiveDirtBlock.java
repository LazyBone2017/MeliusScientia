package de.lazybird.meliusscientia.block;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.DrownedEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RadioactiveDirtBlock extends Block {

    public RadioactiveDirtBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        super.onEntityWalk(worldIn, pos, entityIn);
        if(entityIn instanceof MonsterEntity){
            ((MonsterEntity)entityIn).addPotionEffect(new EffectInstance(Effects.SPEED, 100));
        }

        if(entityIn instanceof PlayerEntity){
            ((PlayerEntity)entityIn).addPotionEffect(new EffectInstance(Effects.SPEED, 100));
        }
    }
}
