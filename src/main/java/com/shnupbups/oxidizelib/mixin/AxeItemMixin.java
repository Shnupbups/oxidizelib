package com.shnupbups.oxidizelib.mixin;

import java.util.Optional;

import com.google.common.collect.BiMap;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HoneycombItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.shnupbups.oxidizelib.OxidizeLib;

@Mixin(AxeItem.class)
public class AxeItemMixin {
	@Redirect(method = "useOnBlock(Lnet/minecraft/item/ItemUsageContext;)Lnet/minecraft/util/ActionResult;", at = @At(value = "INVOKE", target = "Ljava/util/Optional;ofNullable(Ljava/lang/Object;)Ljava/util/Optional;"))
	public <T> Optional<T> redirectGetUnwaxedState(T blockState, ItemUsageContext context) {
		World world = context.getWorld();
		BlockPos pos = context.getBlockPos();
		BlockState state = world.getBlockState(pos);
		return (Optional<T>) Optional.ofNullable((Block)((BiMap) HoneycombItem.WAXED_TO_UNWAXED_BLOCKS.get()).get(state.getBlock())).or(() -> OxidizeLib.getUnwaxedBlock(state.getBlock()));
	}
}
