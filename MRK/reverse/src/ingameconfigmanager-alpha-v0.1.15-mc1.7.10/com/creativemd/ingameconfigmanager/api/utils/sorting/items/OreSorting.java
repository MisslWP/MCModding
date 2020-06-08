package com.creativemd.ingameconfigmanager.api.utils.sorting.items;

import com.creativemd.ingameconfigmanager.api.utils.sorting.SortingItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreSorting extends SortingItem {

   public boolean canContain;
   public String ore;


   public OreSorting(String ore) {
      this(ore, false);
   }

   public OreSorting(String ore, boolean canContain) {
      this.ore = ore.toLowerCase();
      this.canContain = canContain;
   }

   protected boolean isObject(ItemStack stack) {
      int[] ores = OreDictionary.getOreIDs(stack);

      for(int i = 0; i < ores.length; ++i) {
         if(this.canContain) {
            if(OreDictionary.getOreName(ores[i]).toLowerCase().contains(this.ore)) {
               return true;
            }

            if(OreDictionary.getOreName(ores[i]).toLowerCase().equals(this.ore)) {
               return true;
            }
         }
      }

      return false;
   }
}
