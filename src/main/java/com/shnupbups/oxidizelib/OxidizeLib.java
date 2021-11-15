package com.shnupbups.oxidizelib;

import java.util.Optional;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Oxidizable;
import net.minecraft.item.HoneycombItem;

import net.fabricmc.loader.api.FabricLoader;

public class OxidizeLib {
	public static final String MOD_ID = "oxidizelib";

	private static final BiMap<Block, Block> OXIDIZATION_LEVEL_INCREASES = HashBiMap.create();
	private static final BiMap<Block, Block> OXIDIZATION_LEVEL_DECREASES = HashBiMap.create();
	private static final BiMap<Block, Block> UNWAXED_TO_WAXED_BLOCKS = HashBiMap.create();
	private static final BiMap<Block, Block> WAXED_TO_UNWAXED_BLOCKS = HashBiMap.create();

	static {
		OXIDIZATION_LEVEL_INCREASES.putAll(Oxidizable.OXIDATION_LEVEL_INCREASES.get());
		OXIDIZATION_LEVEL_DECREASES.putAll(Oxidizable.OXIDATION_LEVEL_DECREASES.get());
		UNWAXED_TO_WAXED_BLOCKS.putAll(HoneycombItem.UNWAXED_TO_WAXED_BLOCKS.get());
		WAXED_TO_UNWAXED_BLOCKS.putAll(HoneycombItem.WAXED_TO_UNWAXED_BLOCKS.get());

		if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
			OxidizableFamily testFamily = new OxidizableFamily.Builder()
					.unaffected(Blocks.COPPER_ORE, Blocks.DEEPSLATE_COPPER_ORE)
					.exposed(Blocks.IRON_ORE, Blocks.DEEPSLATE_IRON_ORE)
					.weathered(Blocks.GOLD_ORE, Blocks.DEEPSLATE_GOLD_ORE)
					.oxidized(Blocks.DIAMOND_ORE, Blocks.DEEPSLATE_DIAMOND_ORE)
					.build();

			OxidizableFamily testFamily2 = new OxidizableFamily.Builder()
					.unaffected(Blocks.COAL_ORE, Blocks.DEEPSLATE_COAL_ORE)
					.exposed(Blocks.REDSTONE_ORE, Blocks.DEEPSLATE_REDSTONE_ORE)
					.weathered(Blocks.LAPIS_ORE, Blocks.DEEPSLATE_LAPIS_ORE)
					.oxidized(Blocks.EMERALD_ORE, Blocks.DEEPSLATE_EMERALD_ORE)
					.build();

			registerOxidizableFamilies(testFamily, testFamily2);
		}
	}

	public static void registerOxidizableFamilies(OxidizableFamily... families) {
		for (OxidizableFamily family : families) {
			registerOxidizableFamily(family);
		}
	}

	public static void registerOxidizableFamily(OxidizableFamily family) {
		OXIDIZATION_LEVEL_INCREASES.putAll(family.oxidizationLevelIncreasesMap());
		OXIDIZATION_LEVEL_DECREASES.putAll(family.oxidizationLevelDecreasesMap());
		UNWAXED_TO_WAXED_BLOCKS.putAll(family.unwaxedToWaxedMap());
		WAXED_TO_UNWAXED_BLOCKS.putAll(family.waxedToUnwaxedMap());
	}

	public static BiMap<Block, Block> getOxidizationLevelIncreases() {
		return OXIDIZATION_LEVEL_INCREASES;
	}

	public static BiMap<Block, Block> getOxidizationLevelDecreases() {
		return OXIDIZATION_LEVEL_DECREASES;
	}

	public static BiMap<Block, Block> getUnwaxedToWaxedBlocks() {
		return UNWAXED_TO_WAXED_BLOCKS;
	}

	public static BiMap<Block, Block> getWaxedToUnwaxedBlocks() {
		return WAXED_TO_UNWAXED_BLOCKS;
	}

	public static Optional<Block> getDecreasedOxidizationBlock(Block block) {
		return Optional.ofNullable(getOxidizationLevelDecreases().get(block));
	}

	public static Optional<BlockState> getDecreasedOxidizationState(BlockState state) {
		return getDecreasedOxidizationBlock(state.getBlock()).map((block) -> block.getStateWithProperties(state));
	}

	public static Optional<Block> getIncreasedOxidizationBlock(Block block) {
		return Optional.ofNullable(getOxidizationLevelIncreases().get(block));
	}

	public static Optional<BlockState> getIncreasedOxidizationState(BlockState state) {
		return getIncreasedOxidizationBlock(state.getBlock()).map((block) -> block.getStateWithProperties(state));
	}

	public static Optional<Block> getWaxedBlock(Block block) {
		return Optional.ofNullable(getUnwaxedToWaxedBlocks().get(block));
	}

	public static Optional<BlockState> getWaxedState(BlockState state) {
		return getWaxedBlock(state.getBlock()).map((block) -> block.getStateWithProperties(state));
	}

	public static Optional<Block> getUnwaxedBlock(Block block) {
		return Optional.ofNullable(getWaxedToUnwaxedBlocks().get(block));
	}

	public static Optional<BlockState> getUnwaxedState(BlockState state) {
		return getUnwaxedBlock(state.getBlock()).map((block) -> block.getStateWithProperties(state));
	}

	public static Block getUnaffectedOxidizationBlock(Block block) {
		Block block2 = block;

		for (Block block3 = getOxidizationLevelDecreases().get(block); block3 != null; block3 = getOxidizationLevelDecreases().get(block3)) {
			block2 = block3;
		}

		return block2;
	}

	public static BlockState getUnaffectedOxidizationState(BlockState state) {
		return getUnaffectedOxidizationBlock(state.getBlock()).getStateWithProperties(state);
	}
}
