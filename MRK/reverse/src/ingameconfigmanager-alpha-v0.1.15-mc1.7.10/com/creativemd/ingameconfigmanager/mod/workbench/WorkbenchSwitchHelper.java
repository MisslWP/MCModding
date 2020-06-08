package com.creativemd.ingameconfigmanager.mod.workbench;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class WorkbenchSwitchHelper {

   public static ArrayList findMatchingRecipe(InventoryCrafting par1InventoryCrafting, World par2World) {
      int i = 0;
      ItemStack itemstack = null;
      ItemStack itemstack1 = null;
      ArrayList result = new ArrayList();

      int j;
      for(j = 0; j < par1InventoryCrafting.getSizeInventory(); ++j) {
         ItemStack recipes = par1InventoryCrafting.getStackInSlot(j);
         if(recipes != null) {
            if(i == 0) {
               itemstack = recipes;
            }

            if(i == 1) {
               itemstack1 = recipes;
            }

            ++i;
         }
      }

      if(i == 2 && itemstack.getItem() == itemstack1.getItem() && itemstack.stackSize == 1 && itemstack1.stackSize == 1 && itemstack.getItem().isRepairable()) {
         Item var13 = itemstack.getItem();
         int var14 = var13.getMaxDamage() - itemstack.getItemDamageForDisplay();
         int var15 = var13.getMaxDamage() - itemstack1.getItemDamageForDisplay();
         int l = var14 + var15 + var13.getMaxDamage() * 5 / 100;
         int i1 = var13.getMaxDamage() - l;
         if(i1 < 0) {
            i1 = 0;
         }

         result.add(new ItemStack(itemstack.getItem(), 1, i1));
      } else {
         List var12 = CraftingManager.getInstance().getRecipeList();

         for(j = 0; j < var12.size(); ++j) {
            IRecipe irecipe = (IRecipe)var12.get(j);
            if(irecipe.matches(par1InventoryCrafting, par2World)) {
               ItemStack output = irecipe.getCraftingResult(par1InventoryCrafting);
               if(output != null) {
                  result.add(output);
               }
            }
         }
      }

      return result;
   }
}
