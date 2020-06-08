package ru.uncledrema.additionalitems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class BuffItem extends HealItem {
	
	private int dur;
	private boolean cures;
	public BuffItem(int dur, boolean cures)
	{
		super(64);
		this.dur = dur;
		this.cures = cures;
	}

	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
	{
		if (cures) {
			player.clearActivePotions();
			player.heal(20F);
			player.addPotionEffect(new PotionEffect(8,dur,1));
			player.addPotionEffect(new PotionEffect(10,dur,19));
			player.addPotionEffect(new PotionEffect(11,dur,19));
			player.addPotionEffect(new PotionEffect(5,dur,9));
			player.addPotionEffect(new PotionEffect(23,dur,9));
		}
		
		player.addPotionEffect(new PotionEffect(1,dur,9));
		player.addPotionEffect(new PotionEffect(3,dur,9));
		player.addPotionEffect(new PotionEffect(12,dur,9));
		player.addPotionEffect(new PotionEffect(13,dur,9));
		player.addPotionEffect(new PotionEffect(16,dur,9));
		player.addPotionEffect(new PotionEffect(21,dur,19));
		player.addPotionEffect(new PotionEffect(22,dur,19));
		super.onEaten(stack, world, player);
		return stack;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		// TODO Auto-generated method stub
		if (cures) return 10;
		else return 30;
		
	}

}
