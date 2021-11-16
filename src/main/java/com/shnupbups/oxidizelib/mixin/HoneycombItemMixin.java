package com.shnupbups.oxidizelib.mixin;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import net.minecraft.item.HoneycombItem;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HoneycombItem.class)
public class HoneycombItemMixin {
	@Inject(method = "method_34723", at = @At("RETURN"), cancellable = true)
	private static void createUnwaxedToWaxedMap(CallbackInfoReturnable<BiMap> cir) {
		cir.setReturnValue(HashBiMap.create(cir.getReturnValue()));
	}

	@Inject(method = "method_34722", at = @At("RETURN"), cancellable = true)
	private static void createWaxedToUnwaxedMap(CallbackInfoReturnable<BiMap> cir) {
		cir.setReturnValue(HashBiMap.create(cir.getReturnValue()));
	}
}
