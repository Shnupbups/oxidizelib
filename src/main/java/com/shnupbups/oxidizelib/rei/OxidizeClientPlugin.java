package com.shnupbups.oxidizelib.rei;

import java.util.Comparator;
import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.block.Oxidizable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.*;
import net.minecraft.tag.Tag;
import net.minecraft.tag.TagGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.entry.EntryRegistry;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;

import com.shnupbups.oxidizelib.rei.category.OxidizingCategory;
import com.shnupbups.oxidizelib.rei.category.ScrapingCategory;
import com.shnupbups.oxidizelib.rei.category.WaxingCategory;
import com.shnupbups.oxidizelib.rei.display.OxidizingDisplay;
import com.shnupbups.oxidizelib.rei.display.ScrapingDisplay;
import com.shnupbups.oxidizelib.rei.display.WaxingDisplay;

public class OxidizeClientPlugin implements REIClientPlugin {

	@Override
	public void registerCategories(CategoryRegistry registry) {
		registry.add(
				new WaxingCategory(),
				new ScrapingCategory(),
				new OxidizingCategory()
		);
		registry.removePlusButton(OxidizePlugin.WAXING);
		registry.removePlusButton(OxidizePlugin.SCRAPING);
		registry.removePlusButton(OxidizePlugin.OXIDIZING);
		registry.addWorkstations(OxidizePlugin.WAXING, EntryIngredients.of(Items.HONEYCOMB));
		Set<Item> axes = Sets.newHashSet();
		EntryRegistry.getInstance().getEntryStacks().filter(stack -> stack.getValueType() == ItemStack.class).map(stack -> ((ItemStack) stack.getValue()).getItem()).forEach(item -> {
			if (item instanceof AxeItem && axes.add(item)) {
				registry.addWorkstations(OxidizePlugin.SCRAPING, EntryStacks.of(item));
			}
		});
		TagGroup<Item> itemTagCollection = MinecraftClient.getInstance().getNetworkHandler().getTagManager().getOrCreateTagGroup(Registry.ITEM_KEY);
		Tag<Item> axesTag = itemTagCollection.getTag(new Identifier("c", "axes"));
		if (axesTag != null) {
			for (Item item : axesTag.values()) {
				if (axes.add(item)) registry.addWorkstations(OxidizePlugin.SCRAPING, EntryStacks.of(item));
			}
		}
	}

	@Override
	public void registerDisplays(DisplayRegistry registry) {
		HoneycombItem.UNWAXED_TO_WAXED_BLOCKS.get().entrySet().stream().sorted(Comparator.comparing(b -> Registry.BLOCK.getId(b.getKey()))).forEach(set -> {
			registry.add(new WaxingDisplay(EntryStacks.of(set.getKey()), EntryStacks.of(set.getValue())));
		});
		HoneycombItem.WAXED_TO_UNWAXED_BLOCKS.get().entrySet().stream().sorted(Comparator.comparing(b -> Registry.BLOCK.getId(b.getKey()))).forEach(set -> {
			registry.add(new ScrapingDisplay(EntryStacks.of(set.getKey()), EntryStacks.of(set.getValue())));
		});
		Oxidizable.OXIDATION_LEVEL_DECREASES.get().entrySet().stream().sorted(Comparator.comparing(b -> Registry.BLOCK.getId(b.getKey()))).forEach(set -> {
			registry.add(new ScrapingDisplay(EntryStacks.of(set.getKey()), EntryStacks.of(set.getValue())));
		});
		Oxidizable.OXIDATION_LEVEL_INCREASES.get().entrySet().stream().sorted(Comparator.comparing(b -> Registry.BLOCK.getId(b.getKey()))).forEach(set -> {
			registry.add(new OxidizingDisplay(EntryStacks.of(set.getKey()), EntryStacks.of(set.getValue())));
		});
	}
}
