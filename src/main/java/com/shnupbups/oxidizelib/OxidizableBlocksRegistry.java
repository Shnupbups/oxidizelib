package com.shnupbups.oxidizelib;

import net.minecraft.block.Oxidizable;

public class OxidizableBlocksRegistry {
	/**
	 * Registers multiple {@link OxidizableFamily}s.
	 *
	 * @param families the families to register
	 */
	public static void registerOxidizableFamilies(OxidizableFamily... families) {
		for (OxidizableFamily family : families) {
			registerOxidizableFamily(family);
		}
	}

	/**
	 * Registers multiple {@link OxidizableFamily}s.
	 *
	 * @param families the families to register
	 */
	public static void registerOxidizableFamilies(Iterable<OxidizableFamily> families) {
		for (OxidizableFamily family : families) {
			registerOxidizableFamily(family);
		}
	}

	/**
	 * Registers an {@link OxidizableFamily}.
	 *
	 * @param family the {@link OxidizableFamily} to register
	 */
	public static void registerOxidizableFamily(OxidizableFamily family) {
		Oxidizable.OXIDATION_LEVEL_INCREASES.get().putAll(family.oxidizationLevelIncreasesMap());
		Oxidizable.OXIDATION_LEVEL_DECREASES.get().putAll(family.oxidizationLevelDecreasesMap());
		WaxableBlocksRegistry.registerWaxablePairs(family.waxableBlockPairs());
	}
}
