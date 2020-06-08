package ru.uncledrema.additionalitems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class AptechkaBest extends AptechkaCore {
	public AptechkaBest()
    {
		super(4);
		this.setFull3D();
    }
	
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
    {
		player.clearActivePotions();
		player.addPotionEffect(new PotionEffect(1,260,0,true));
		player.addPotionEffect(new PotionEffect(22,1800,0,true));
		player.heal(20F);
        super.onEaten(stack, world, player);
        return stack;
    }
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
    {
        return 140;
    }
}
