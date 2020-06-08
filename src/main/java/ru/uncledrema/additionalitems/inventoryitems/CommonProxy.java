package ru.uncledrema.additionalitems.inventoryitems;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class CommonProxy implements IGuiHandler
{
	public void registerRenderers() {}

	@Override
	public Object getServerGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z)
	{
		// Hooray, no 'magic' numbers - we know exactly which Gui this refers to
		if (guiId == InventoryItemMain.GUI_ITEM_INV_THREE)
		{
			// Use the player's held item to create the inventory
			return new ContainerItem( player, player.inventory, new InventoryItemThree( player.getHeldItem() ) );
		}
		else if (guiId == InventoryItemMain.GUI_ITEM_INV_TWO)
		{
			return new ContainerItem(player, player.inventory, new InventoryItemTwo(player.getHeldItem() ) );
		}
		else if (guiId == InventoryItemMain.GUI_ITEM_INV_FIVE)
		{
			return new ContainerItem(player, player.inventory, new InventoryItemFive(player.getHeldItem() ) );
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z)
	{
		if (guiId == InventoryItemMain.GUI_ITEM_INV_THREE)
		{
			// We have to cast the new container as our custom class
			// and pass in currently held item for the inventory
			return new GuiItemInventory((ContainerItem) new ContainerItem(player, player.inventory, new InventoryItemThree(player.getHeldItem() ) ));
		}
		else if (guiId == InventoryItemMain.GUI_ITEM_INV_TWO)
		{
			return new GuiItemInventory((ContainerItem) new ContainerItem(player, player.inventory, new InventoryItemTwo(player.getHeldItem() ) ));
		}
		else if (guiId == InventoryItemMain.GUI_ITEM_INV_FIVE)
		{
			return new GuiItemInventory((ContainerItem) new ContainerItem(player, player.inventory, new InventoryItemFive(player.getHeldItem() ) ));
		}
		return null;
	}
}