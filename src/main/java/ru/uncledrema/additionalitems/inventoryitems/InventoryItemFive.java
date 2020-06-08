package ru.uncledrema.additionalitems.inventoryitems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

public class InventoryItemFive  extends InventoryItemRoot implements IInventory
{

	public InventoryItemFive(ItemStack stack) {
		super(stack, 5, "Inventory Item Five");
	}
	
}
