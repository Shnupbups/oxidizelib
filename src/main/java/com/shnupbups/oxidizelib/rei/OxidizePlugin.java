package com.shnupbups.oxidizelib.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.plugins.REIServerPlugin;

import com.shnupbups.oxidizelib.rei.display.OxidizingDisplay;
import com.shnupbups.oxidizelib.rei.display.ScrapingDisplay;
import com.shnupbups.oxidizelib.rei.display.WaxingDisplay;

public class OxidizePlugin implements REIServerPlugin {
	public static final String MOD_ID = "oxidizelib";
	public static final CategoryIdentifier<WaxingDisplay> WAXING = CategoryIdentifier.of(MOD_ID, "plugins/waxing");
	public static final CategoryIdentifier<ScrapingDisplay> SCRAPING = CategoryIdentifier.of(MOD_ID, "plugins/scraping");
	public static final CategoryIdentifier<OxidizingDisplay> OXIDIZING = CategoryIdentifier.of(MOD_ID, "plugins/oxidizing");

	@Override
	public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
		registry.register(WAXING, WaxingDisplay.serializer());
		registry.register(SCRAPING, ScrapingDisplay.serializer());
		registry.register(OXIDIZING, OxidizingDisplay.serializer());
	}
}
