package ru.uncledrema.additionalitems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public abstract class HealItem extends ItemFood {
	public HealItem(int count)
    {
		super(0, 0F, false);
        this.setMaxStackSize(count);
        this.setCreativeTab(AdditionalItemsMod.funTab);
        this.setAlwaysEdible();
    }
	
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
    {
        super.onEaten(stack, world, player);
        return stack;
    }
	
	@Override
	public abstract int getMaxItemUseDuration(ItemStack stack);
}
