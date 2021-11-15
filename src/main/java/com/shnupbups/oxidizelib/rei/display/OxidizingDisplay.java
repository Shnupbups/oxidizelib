package com.shnupbups.oxidizelib.rei.display;

import java.util.Collections;
import java.util.List;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;

import com.shnupbups.oxidizelib.rei.OxidizePlugin;

public class OxidizingDisplay extends BasicDisplay {
	public OxidizingDisplay(EntryStack<?> in, EntryStack<?> out) {
		this(Collections.singletonList(EntryIngredient.of(in)), Collections.singletonList(EntryIngredient.of(out)));
	}

	public OxidizingDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
		super(inputs, outputs);
	}

	public static Serializer<OxidizingDisplay> serializer() {
		return Serializer.ofSimpleRecipeLess(OxidizingDisplay::new);
	}

	public final EntryIngredient getIn() {
		return getInputEntries().get(0);
	}

	public final EntryIngredient getOut() {
		return getOutputEntries().get(0);
	}

	@Override
	public CategoryIdentifier<?> getCategoryIdentifier() {
		return OxidizePlugin.OXIDIZING;
	}
}
