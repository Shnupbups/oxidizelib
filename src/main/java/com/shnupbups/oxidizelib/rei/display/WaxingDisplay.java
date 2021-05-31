package com.shnupbups.oxidizelib.rei.display;

import java.util.Collections;
import java.util.List;

import com.shnupbups.oxidizelib.rei.OxidizePlugin;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;

public class WaxingDisplay extends BasicDisplay {
	public WaxingDisplay(EntryStack<?> in, EntryStack<?> out) {
		this(Collections.singletonList(EntryIngredient.of(in)), Collections.singletonList(EntryIngredient.of(out)));
	}

	public WaxingDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
		super(inputs, outputs);
	}

	public final EntryIngredient getIn() {
		return getInputEntries().get(0);
	}

	public final EntryIngredient getOut() {
		return getOutputEntries().get(0);
	}

	@Override
	public CategoryIdentifier<?> getCategoryIdentifier() {
		return OxidizePlugin.WAXING;
	}

	public static BasicDisplay.Serializer<WaxingDisplay> serializer() {
		return BasicDisplay.Serializer.ofSimpleRecipeLess(WaxingDisplay::new);
	}
}