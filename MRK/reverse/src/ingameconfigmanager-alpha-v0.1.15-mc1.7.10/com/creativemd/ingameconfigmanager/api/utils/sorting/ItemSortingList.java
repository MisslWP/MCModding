package com.creativemd.ingameconfigmanager.api.utils.sorting;

import com.creativemd.ingameconfigmanager.api.utils.sorting.SortingItem;
import java.util.ArrayList;
import net.minecraft.item.ItemStack;

public class ItemSortingList extends ArrayList {

   public boolean isWhiteList = true;


   public ItemSortingList setBlackList() {
      this.isWhiteList = false;
      return this;
   }

   public boolean isObjectValid(ItemStack stack) {
      if(stack != null && stack.getItem() != null) {
         for(int i = 0; i < this.size(); ++i) {
            if(((SortingItem)this.get(i)).isObject(stack)) {
               return this.isWhiteList;
            }
         }
      }

      return !this.isWhiteList;
   }
}
