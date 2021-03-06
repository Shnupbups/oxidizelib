package com.shnupbups.oxidizelib.rei.category;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;

import com.shnupbups.oxidizelib.rei.OxidizePlugin;
import com.shnupbups.oxidizelib.rei.display.OxidizingDisplay;

public class OxidizingCategory implements DisplayCategory<OxidizingDisplay> {
	@Override
	public CategoryIdentifier<? extends OxidizingDisplay> getCategoryIdentifier() {
		return OxidizePlugin.OXIDIZING;
	}

	@Override
	public Renderer getIcon() {
		return EntryStacks.of(Items.CLOCK);
	}

	@Override
	public Text getTitle() {
		return new TranslatableText("category.oxidizelib.oxidizing");
	}

	@Override
	public List<Widget> setupDisplay(OxidizingDisplay display, Rectangle bounds) {
		Point startPoint = new Point(bounds.getCenterX() - 41, bounds.getCenterY() - 13);
		List<Widget> widgets = Lists.newArrayList();
		widgets.add(Widgets.createRecipeBase(bounds));
		widgets.add(Widgets.createArrow(new Point(startPoint.x + 27, startPoint.y + 4)));
		widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x + 61, startPoint.y + 5)));
		widgets.add(Widgets.createSlot(new Point(startPoint.x + 4, startPoint.y + 5)).entries(display.getIn()).markInput());
		widgets.add(Widgets.createSlot(new Point(startPoint.x + 61, startPoint.y + 5)).entries(display.getOut()).disableBackground().markInput());
		return widgets;
	}

	@Override
	public int getDisplayHeight() {
		return 36;
	}
}
