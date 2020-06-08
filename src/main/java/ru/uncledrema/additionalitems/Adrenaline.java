package ru.uncledrema.additionalitems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class Adrenaline extends HealItem {
	public Adrenaline()
    {
		super(1);
    }
	
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
    {
		player.addPotionEffect(new PotionEffect(1,300,1,true));
		player.addPotionEffect(new PotionEffect(17,600,2,true));
		player.heal(1F);
        super.onEaten(stack, world, player);
        return stack;
    }
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
    {
        return 30;
    }
}
