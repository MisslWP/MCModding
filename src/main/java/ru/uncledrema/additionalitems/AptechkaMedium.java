package ru.uncledrema.additionalitems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class AptechkaMedium extends AptechkaCore {
	public AptechkaMedium()
    {
		super(3);
    }
	
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
    {
		player.clearActivePotions();
		player.addPotionEffect(new PotionEffect(1,120,0,true));
		player.addPotionEffect(new PotionEffect(17,300,0,true));
		player.heal(15F);
        super.onEaten(stack, world, player);
        return stack;
    }
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
    {
        return 100;
    }
}
