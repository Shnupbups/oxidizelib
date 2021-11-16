package com.shnupbups.oxidizelib;

import java.util.Objects;

import net.minecraft.block.Block;

/**
 * Represents a block and its waxed counterpart.
 */
public record WaxableBlockPair(Block unwaxed, Block waxed) {
	public WaxableBlockPair {
		Objects.requireNonNull(unwaxed, "Unwaxed block cannot be null in WaxableBlock!");
		Objects.requireNonNull(waxed, "Waxed block cannot be null in WaxableBlock!");
	}
}
