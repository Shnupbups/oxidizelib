package com.shnupbups.oxidizelib;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.item.HoneycombItem;

public class OxidizeLib {
	public static final String MOD_ID = "oxidizelib";

	public static final List<OxidizableFamily> OXIDIZABLE_FAMILIES = new ArrayList<>();

	private static Supplier<BiMap<Block, Block>> oxidizationLevelIncreases;
	private static Supplier<BiMap<Block, Block>> oxidizationLevelDecreases;
	private static Supplier<BiMap<Block, Block>> unwaxedToWaxed;
	private static Supplier<BiMap<Block, Block>> waxedToUnwaxed;

	private static boolean dirty = true;

	public static void registerOxidizableFamily(OxidizableFamily family) {
		OXIDIZABLE_FAMILIES.add(family);
		setDirty(true);
	}

	public static Supplier<BiMap<Block, Block>> getOxidizationLevelIncreases() {
		if(!isDirty() || oxidizationLevelIncreases != null) return oxidizationLevelIncreases;
		generateSuppliers();
		return oxidizationLevelIncreases;
	}

	public static Supplier<BiMap<Block, Block>> getOxidizationLevelDecreases() {
		if(!isDirty() || oxidizationLevelDecreases != null) return oxidizationLevelDecreases;
		generateSuppliers();
		return oxidizationLevelDecreases;
	}

	public static Supplier<BiMap<Block, Block>> getUnwaxedToWaxed() {
		if(!isDirty() || unwaxedToWaxed != null) return unwaxedToWaxed;
		generateSuppliers();
		return unwaxedToWaxed;
	}

	public static Supplier<BiMap<Block, Block>> getWaxedToUnwaxed() {
		if(!isDirty() || waxedToUnwaxed != null) return waxedToUnwaxed;
		generateSuppliers();
		return waxedToUnwaxed;
	}

	public static Optional<Block> getDecreasedOxidizationBlock(Block block) {
		return Optional.ofNullable(getOxidizationLevelDecreases().get().get(block));
	}

	public static Optional<BlockState> getDecreasedOxidizationState(BlockState state) {
		return getDecreasedOxidizationBlock(state.getBlock()).map((block) -> block.getStateWithProperties(state));
	}

	public static Optional<Block> getIncreasedOxidizationBlock(Block block) {
		return Optional.ofNullable(getOxidizationLevelIncreases().get().get(block));
	}

	public static Optional<BlockState> getIncreasedOxidizationState(BlockState state) {
		return getIncreasedOxidizationBlock(state.getBlock()).map((block) -> block.getStateWithProperties(state));
	}

	public static Optional<Block> getWaxedBlock(Block block) {
		return Optional.ofNullable(getUnwaxedToWaxed().get().get(block));
	}

	public static Optional<BlockState> getWaxedState(BlockState state) {
		return getWaxedBlock(state.getBlock()).map((block) -> block.getStateWithProperties(state));
	}

	public static Optional<Block> getUnwaxedBlock(Block block) {
		return Optional.ofNullable(getWaxedToUnwaxed().get().get(block));
	}

	public static Optional<BlockState> getUnwaxedState(BlockState state) {
		return getUnwaxedBlock(state.getBlock()).map((block) -> block.getStateWithProperties(state));
	}

	public static Block getUnaffectedOxidizationBlock(Block block) {
		Block block2 = block;

		for(Block block3 = getOxidizationLevelDecreases().get().get(block); block3 != null; block3 = getOxidizationLevelDecreases().get().get(block3)) {
			block2 = block3;
		}

		return block2;
	}

	public static BlockState getUnaffectedOxidizationState(BlockState state) {
		return getUnaffectedOxidizationBlock(state.getBlock()).getStateWithProperties(state);
	}

	private static boolean isDirty() {
		return dirty;
	}

	private static void setDirty(boolean dirty) {
		OxidizeLib.dirty = dirty;
	}

	private static void generateSuppliers() {
		ImmutableBiMap.Builder<Block, Block> oxidationLevelBuilder = ImmutableBiMap.builder();

		oxidationLevelBuilder.putAll(Oxidizable.OXIDATION_LEVEL_INCREASES.get());

		for(OxidizableFamily family:OXIDIZABLE_FAMILIES) {
			oxidationLevelBuilder.putAll(family.getOxidizationLevelIncreasesMap());
		}

		oxidizationLevelIncreases = Suppliers.memoize(oxidationLevelBuilder::build);
		oxidizationLevelDecreases = Suppliers.memoize(() -> oxidizationLevelIncreases.get().inverse());

		ImmutableBiMap.Builder<Block, Block> waxingBuilder = ImmutableBiMap.builder();

		waxingBuilder.putAll(HoneycombItem.UNWAXED_TO_WAXED_BLOCKS.get());

		for(OxidizableFamily family:OXIDIZABLE_FAMILIES) {
			waxingBuilder.putAll(family.getUnwaxedToWaxedMap());
		}

		unwaxedToWaxed = Suppliers.memoize(waxingBuilder::build);
		waxedToUnwaxed = Suppliers.memoize(() -> unwaxedToWaxed.get().inverse());

		setDirty(false);
	}
}
