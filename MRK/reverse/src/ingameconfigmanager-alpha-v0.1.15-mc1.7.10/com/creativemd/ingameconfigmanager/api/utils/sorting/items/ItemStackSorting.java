package com.creativemd.ingameconfigmanager.api.utils.sorting.items;

import com.creativemd.ingameconfigmanager.api.utils.sorting.items.ItemSorting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemStackSorting extends ItemSorting {

   public int damage;
   public boolean NeedNBT;
   public NBTTagCompound nbt;


   public ItemStackSorting(ItemStack stack) {
      this(stack, false);
   }

   public ItemStackSorting(ItemStack stack, boolean NeedNBT) {
      super(stack.getItem());
      this.damage = stack.getItemDamage();
      this.NeedNBT = NeedNBT;
      this.nbt = stack.stackTagCompound;
   }

   protected boolean isObject(ItemStack stack) {
      if(super.isObject(stack) && stack.getItemDamage() == this.damage) {
         if(!this.NeedNBT) {
            return true;
         }

         if(this.nbt == null) {
            return stack.stackTagCompound == this.nbt;
         }

         if(stack.stackTagCompound != null) {
            return this.nbt.equals(stack.stackTagCompound);
         }
      }

      return false;
   }
}
