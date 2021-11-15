package com.shnupbups.oxidizelib.mixin;

import java.util.Optional;

import net.minecraft.block.BlockState;
import net.minecraft.item.HoneycombItem;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.shnupbups.oxidizelib.OxidizeLib;

@Mixin(HoneycombItem.class)
public class HoneycombItemMixin {
	@Inject(method = "getWaxedState", at = @At("RETURN"), cancellable = true)
	private static void getWaxedStateInject(BlockState state, CallbackInfoReturnable<Optional<BlockState>> cir) {
		if (cir.getReturnValue().isEmpty()) {
			cir.setReturnValue(OxidizeLib.getWaxedState(state));
		}
	}
}
