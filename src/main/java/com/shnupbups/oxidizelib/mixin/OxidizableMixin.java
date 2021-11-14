package com.shnupbups.oxidizelib.mixin;

import java.util.Optional;

import net.minecraft.block.Block;
import net.minecraft.block.Oxidizable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.shnupbups.oxidizelib.OxidizeLib;

@Mixin(Oxidizable.class)
public interface OxidizableMixin {
	@Inject(method = "getDecreasedOxidationBlock", at = @At("RETURN"), cancellable = true)
	private static void getDecreasedOxidationBlockInject(Block block, CallbackInfoReturnable<Optional<Block>> cir) {
		if(cir.getReturnValue().isEmpty()) {
			cir.setReturnValue(OxidizeLib.getDecreasedOxidizationBlock(block));
		}
	}

	@Inject(method = "getIncreasedOxidationBlock", at = @At("RETURN"), cancellable = true)
	private static void getIncreasedOxidationBlockInject(Block block, CallbackInfoReturnable<Optional<Block>> cir) {
		if(cir.getReturnValue().isEmpty()) {
			cir.setReturnValue(OxidizeLib.getIncreasedOxidizationBlock(block));
		}
	}

	@Inject(method = "getUnaffectedOxidationBlock", at = @At("RETURN"), cancellable = true)
	private static void getUnaffectedOxidationBlockInject(Block block, CallbackInfoReturnable<Block> cir) {
		if(cir.getReturnValue().equals(block)) {
			cir.setReturnValue(OxidizeLib.getUnaffectedOxidizationBlock(block));
		}
	}
}
