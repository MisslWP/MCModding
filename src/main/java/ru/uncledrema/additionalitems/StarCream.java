package ru.uncledrema.additionalitems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class StarCream extends HealItem {
	
	public StarCream()
	{
		super(2);
	}

	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
	{
		player.addPotionEffect(new PotionEffect(1,120,0));
		player.heal(1F);
		super.onEaten(stack, world, player);
		return stack;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		// TODO Auto-generated method stub
		return 60;
	}

}
