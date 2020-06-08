package ru.uncledrema.additionalitems.inventoryitems;

import java.util.UUID;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import ru.uncledrema.additionalitems.AdditionalItemsMod;

public class ItemStoreThree extends Item
{
	private IIcon icon;
	public ItemStoreThree()
	{
		// ItemStacks that store an NBT Tag Compound are limited to stack size of 1
		setMaxStackSize(1);
		// you'll want to set a creative tab as well, so you can get your item
		setCreativeTab(AdditionalItemsMod.funTab);
	}
	
    /**
     * How long it takes to use or consume an item
     */
	// Without this method, your inventory will NOT work!!!
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 1; // return any value greater than zero
	}
   
    @Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player)
	{
		if (!world.isRemote)
		{
			// If player not sneaking, open the inventory gui
			if (!player.isSneaking()) {
				
				if (!itemstack.hasTagCompound() || (itemstack.hasTagCompound() & itemstack.getTagCompound().getString("playerBound").equals(""))) {
					NBTTagCompound nbt = new NBTTagCompound();
					nbt.setString("playerBound", player.getUniqueID().toString());
					itemstack.setTagCompound(nbt);
				}
				if (itemstack.hasTagCompound() && itemstack.getTagCompound().getString("playerBound").equals(player.getUniqueID().toString())) {
					player.openGui(InventoryItemMain.instance, InventoryItemMain.GUI_ITEM_INV_THREE, world, 0, 0, 0);
				}
				else if (itemstack.hasTagCompound() && !itemstack.getTagCompound().getString("playerBound").equals(player.getUniqueID().toString()) && !itemstack.getTagCompound().getString("playerBound").equals(""))
						{
							System.out.println("Warning! Player " + player.getDisplayName() + " with UUID: " + player.getUniqueID() + " is trying to open a bag bound to UUID: " + itemstack.getTagCompound().getString("playerBound"));
							MinecraftServer.getServer().getCommandManager().executeCommand(MinecraftServer.getServer(), "say Эй, " + player.getDisplayName() + " убери руки от этого ящика! Он не твоооооой!");
						}
			}
			
		}
		
		return itemstack;
	}

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
       this.icon = register.registerIcon("inventoryitemmod:bagalpha");
    }

    public IIcon getIconFromDamage(int meta) {
       return this.icon;
    }
}
