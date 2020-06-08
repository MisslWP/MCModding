package com.creativemd.ingameconfigmanager.api.utils.sorting;

import net.minecraft.item.ItemStack;

public abstract class SortingItem {

   public int stackSize = -1;


   protected abstract boolean isObject(ItemStack var1);

   public SortingItem setStackSize(int stackSize) {
      this.stackSize = stackSize;
      return this;
   }

   public boolean isObjectEqual(ItemStack stack) {
      return !this.isObject(stack)?false:stack.stackSize >= this.stackSize || this.stackSize <= 0;
   }
}
