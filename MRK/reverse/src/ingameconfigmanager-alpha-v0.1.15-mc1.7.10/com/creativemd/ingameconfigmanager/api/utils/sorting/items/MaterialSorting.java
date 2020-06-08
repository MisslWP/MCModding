package com.creativemd.ingameconfigmanager.api.utils.sorting.items;

import com.creativemd.ingameconfigmanager.api.utils.sorting.SortingItem;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class MaterialSorting extends SortingItem {

   public Material material;


   public MaterialSorting(Material material) {
      this.material = material;
   }

   protected boolean isObject(ItemStack stack) {
      return stack.getItem() instanceof ItemBlock?Block.getBlockFromItem(stack.getItem()).getMaterial() == this.material:false;
   }
}
