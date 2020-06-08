package com.creativemd.ingameconfigmanager.api.utils.sorting.items;

import com.creativemd.ingameconfigmanager.api.utils.sorting.SortingItem;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class BlockSorting extends SortingItem {

   public Block block;


   public BlockSorting(Block block) {
      this.block = block;
   }

   protected boolean isObject(ItemStack stack) {
      Item item = stack.getItem();
      return item instanceof ItemBlock?Block.getBlockFromItem(item) == this.block:false;
   }
}
