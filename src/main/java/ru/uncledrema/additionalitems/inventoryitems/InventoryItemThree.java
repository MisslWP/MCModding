package ru.uncledrema.additionalitems.inventoryitems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

public class InventoryItemThree extends InventoryItemRoot implements IInventory
{
	public InventoryItemThree(ItemStack stack) {
		super(stack, 3, "Inventory Item Three");
	}
}
