package ru.uncledrema.additionalitems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ActivatedCoal extends HealItem {
	public ActivatedCoal()
    {
		super(3);
    }
	
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
    {
		player.clearActivePotions();
        super.onEaten(stack, world, player);
        return stack;
    }
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
    {
        return 30;
    }
}
