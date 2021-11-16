package com.shnupbups.oxidizelib;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.Blocks;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class Tester implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger();

	@Override
	public void onInitialize() {
		if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
			// note that since ores do not implement Oxidizable that a warning will be shown in the log
			OxidizableFamily testFamily = new OxidizableFamily.Builder()
					.unaffected(Blocks.COPPER_ORE, Blocks.DEEPSLATE_COPPER_ORE)
					.exposed(Blocks.IRON_ORE, Blocks.DEEPSLATE_IRON_ORE)
					.weathered(Blocks.GOLD_ORE, Blocks.DEEPSLATE_GOLD_ORE)
					.oxidized(Blocks.DIAMOND_ORE, Blocks.DEEPSLATE_DIAMOND_ORE)
					.build();

			OxidizableBlocksRegistry.registerOxidizableFamily(testFamily);

			// assert that OxidizableFamily.Builder throws when a family is missing blocks
			try {
				new OxidizableFamily.Builder() // Has a null entry
						.unaffected(Blocks.COAL_ORE, Blocks.DEEPSLATE_COAL_ORE)
						.exposed(Blocks.REDSTONE_ORE, Blocks.DEEPSLATE_REDSTONE_ORE)
						.weathered(Blocks.LAPIS_ORE, Blocks.DEEPSLATE_LAPIS_ORE)
						.oxidized(Blocks.NETHER_GOLD_ORE, null)
						.build();

				new OxidizableFamily.Builder() // Is missing Oxidization levels
						.unaffected(Blocks.EMERALD_ORE, Blocks.DEEPSLATE_EMERALD_ORE)
						.build();

				throw new AssertionError("OxidizableFamily.Builder didn't throw when blocks were missing in a family!");
			} catch (NullPointerException e) {
				// expected behavior
				LOGGER.info("OxidizableFamily test passed!");
			}

			WaxableBlocksRegistry.registerWaxablePair(Blocks.QUARTZ_BLOCK, Blocks.SMOOTH_QUARTZ);

			try {
				WaxableBlocksRegistry.registerWaxablePair(null, Blocks.DEAD_BRAIN_CORAL);
				WaxableBlocksRegistry.registerWaxablePair(Blocks.BRAIN_CORAL, null);

				throw new AssertionError("WaxableBlocksRegistry didn't throw when blocks were missing in a pair!");
			} catch (NullPointerException e) {
				// expected behavior
				LOGGER.info("WaxableBlocksRegistry test passed!");
			}
		}
	}
}
