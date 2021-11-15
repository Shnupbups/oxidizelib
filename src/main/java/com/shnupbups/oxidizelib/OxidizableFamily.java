package com.shnupbups.oxidizelib;

import static net.minecraft.block.Oxidizable.OxidizationLevel;

import java.util.HashMap;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.Block;
import net.minecraft.block.Oxidizable;

public record OxidizableFamily(
		ImmutableMap<OxidizationLevel, Block> unwaxed,
		ImmutableMap<OxidizationLevel, Block> waxed) {
	private static final Logger LOGGER = LogManager.getLogger();

	public Block unwaxed(OxidizationLevel level) {
		return unwaxed().get(level);
	}

	public Block waxed(OxidizationLevel level) {
		return waxed().get(level);
	}

	public BiMap<Block, Block> oxidizationLevelIncreasesMap() {
		return ImmutableBiMap.<Block, Block>builder()
				.put(unwaxed(OxidizationLevel.UNAFFECTED), unwaxed(OxidizationLevel.EXPOSED))
				.put(unwaxed(OxidizationLevel.EXPOSED), unwaxed(OxidizationLevel.WEATHERED))
				.put(unwaxed(OxidizationLevel.WEATHERED), unwaxed(OxidizationLevel.OXIDIZED))
				.build();
	}

	public BiMap<Block, Block> oxidizationLevelDecreasesMap() {
		return oxidizationLevelIncreasesMap().inverse();
	}

	public BiMap<Block, Block> unwaxedToWaxedMap() {
		return ImmutableBiMap.<Block, Block>builder()
				.put(unwaxed(OxidizationLevel.UNAFFECTED), waxed(OxidizationLevel.UNAFFECTED))
				.put(unwaxed(OxidizationLevel.EXPOSED), waxed(OxidizationLevel.EXPOSED))
				.put(unwaxed(OxidizationLevel.WEATHERED), waxed(OxidizationLevel.WEATHERED))
				.put(unwaxed(OxidizationLevel.OXIDIZED), waxed(OxidizationLevel.OXIDIZED))
				.build();
	}

	public BiMap<Block, Block> waxedToUnwaxedMap() {
		return unwaxedToWaxedMap().inverse();
	}

	public static class Builder {
		private final HashMap<OxidizationLevel, Block> unwaxed = new HashMap<>();
		private final HashMap<OxidizationLevel, Block> waxed = new HashMap<>();

		public Builder add(OxidizationLevel level, Block unwaxed, Block waxed) {
			if (!(unwaxed instanceof Oxidizable)) {
				LOGGER.warn("Block " + unwaxed + " is not oxidizable, but added to OxidizableFamily as unwaxed block. This is likely an error!");
			}
			this.unwaxed.put(level, unwaxed);
			this.waxed.put(level, waxed);
			return this;
		}

		public Builder unaffected(Block unwaxed, Block waxed) {
			return add(OxidizationLevel.UNAFFECTED, unwaxed, waxed);
		}

		public Builder weathered(Block unwaxed, Block waxed) {
			return add(OxidizationLevel.WEATHERED, unwaxed, waxed);
		}

		public Builder exposed(Block unwaxed, Block waxed) {
			return add(OxidizationLevel.EXPOSED, unwaxed, waxed);
		}

		public Builder oxidized(Block unwaxed, Block waxed) {
			return add(OxidizationLevel.OXIDIZED, unwaxed, waxed);
		}

		public OxidizableFamily build() {
			for (OxidizationLevel level : OxidizationLevel.values()) {
				if (!unwaxed.containsKey(level) || unwaxed.get(level) == null) {
					throw new IllegalStateException("OxidizableFamily is missing unwaxed variant for " + level + "!");
				}
				if (!waxed.containsKey(level) || waxed.get(level) == null) {
					throw new IllegalStateException("OxidizableFamily is missing waxed variant for " + level + "!");
				}
			}
			return new OxidizableFamily(ImmutableMap.copyOf(unwaxed), ImmutableMap.copyOf(waxed));
		}
	}
}
