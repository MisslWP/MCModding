package ru.uncledrema.additionalitems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class AptechkaCore extends ItemFood
{
	private int uses;
	public AptechkaCore(int uses)
    {
		super(0, 0F, false);
		this.uses = uses;
        this.setMaxStackSize(1);
        this.setCreativeTab(AdditionalItemsMod.funTab);
        this.setAlwaysEdible();
        this.setMaxDamage(uses);
    }
	
	@Override
	public ItemStack onEaten(ItemStack stack, World p_77654_2_, EntityPlayer player)
    {
		stack.damageItem(1, player);
		if (stack.getItemDamage() == uses) --stack.stackSize;
		player.getFoodStats().func_151686_a(this, stack);
        p_77654_2_.playSoundAtEntity(player, "random.burp", 0.5F, p_77654_2_.rand.nextFloat() * 0.1F + 0.9F);
        this.onFoodEaten(stack, p_77654_2_, player);
        return stack;
    }
	
	public int getMaxUses()
    {
        return uses;
    }
	
	@Override
	public abstract int getMaxItemUseDuration(ItemStack stack);
}
