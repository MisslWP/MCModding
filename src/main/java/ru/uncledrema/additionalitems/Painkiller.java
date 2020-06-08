package ru.uncledrema.additionalitems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class Painkiller extends HealItem {
	public Painkiller()
    {
		super(1);
    }
	
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
    {
		player.addPotionEffect(new PotionEffect(22,1200,0,true));
        super.onEaten(stack, world, player);
        return stack;
    }
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
    {
        return 30;
    }
}
