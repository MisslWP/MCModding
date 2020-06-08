package ru.uncledrema.additionalitems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class IRP extends ItemFood
{
	public IRP()
    {
        super(20, 2.0F, false);
        this.setMaxStackSize(1);
        this.setCreativeTab(AdditionalItemsMod.funTab);
    }
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
    {
		player.addPotionEffect(new PotionEffect(2,200,1,true));
        super.onEaten(stack, world, player);
        return stack;
    }
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
    {
        return 140;
    }
}
