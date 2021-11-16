package com.shnupbups.oxidizelib;

import net.minecraft.block.Block;
import net.minecraft.item.HoneycombItem;

public class WaxableBlocksRegistry {
	/**
	 * Registers a waxable block pair.
	 * Unnecessary if part of a registered {@link OxidizableFamily}.
	 *
	 * @param blocks the blocks to register
	 * @see OxidizableBlocksRegistry#registerOxidizableFamily(OxidizableFamily)
	 * @see #registerWaxablePair(Block, Block)
	 */
	public static void registerWaxablePair(WaxableBlockPair blocks) {
		HoneycombItem.UNWAXED_TO_WAXED_BLOCKS.get().put(blocks.unwaxed(), blocks.waxed());
		HoneycombItem.WAXED_TO_UNWAXED_BLOCKS.get().put(blocks.waxed(), blocks.unwaxed());
	}

	/**
	 * Registers a waxable block.
	 * Unnecessary if part of a registered {@link OxidizableFamily}.
	 *
	 * @param unwaxed the unwaxed variant
	 * @param waxed   the waxed variant
	 * @see OxidizableBlocksRegistry#registerOxidizableFamily(OxidizableFamily)
	 * @see #registerWaxablePair(WaxableBlockPair)
	 */
	public static void registerWaxablePair(Block unwaxed, Block waxed) {
		registerWaxablePair(new WaxableBlockPair(unwaxed, waxed));
	}

	/**
	 * Registers multiple waxable blocks.
	 * Unnecessary if part of a registered {@link OxidizableFamily}.
	 *
	 * @param blocks the blocks to register
	 * @see OxidizableBlocksRegistry#registerOxidizableFamily(OxidizableFamily)
	 */
	public static void registerWaxablePairs(WaxableBlockPair... blocks) {
		for (WaxableBlockPair pair : blocks) {
			registerWaxablePair(pair);
		}
	}

	/**
	 * Registers multiple waxable blocks.
	 * Unnecessary if part of a registered {@link OxidizableFamily}.
	 *
	 * @param blocks the blocks to register
	 * @see OxidizableBlocksRegistry#registerOxidizableFamily(OxidizableFamily)
	 */
	public static void registerWaxablePairs(Iterable<WaxableBlockPair> blocks) {
		for (WaxableBlockPair pair : blocks) {
			registerWaxablePair(pair);
		}
	}
}
