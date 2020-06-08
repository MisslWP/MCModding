package com.creativemd.ingameconfigmanager.mod.block;

import com.creativemd.creativecore.common.container.SubContainer;
import com.creativemd.creativecore.common.container.slot.SlotOutput;
import com.creativemd.creativecore.common.utils.InventoryUtils;
import com.creativemd.creativecore.common.utils.WorldUtils;
import com.creativemd.ingameconfigmanager.mod.block.AdvancedGridRecipe;
import com.creativemd.ingameconfigmanager.mod.block.BlockAdvancedWorkbench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class SubContainerAdvancedWorkbench extends SubContainer {

   public InventoryBasic crafting = new InventoryBasic("crafting", false, (int)Math.pow(6.0D, 2.0D));
   public InventoryBasic output = new InventoryBasic("output", false, 4);


   public SubContainerAdvancedWorkbench(EntityPlayer player) {
      super(player);
   }

   public void createControls() {
      int i;
      for(i = 0; i < 6; ++i) {
         for(int x = 0; x < 6; ++x) {
            this.addSlotToContainer(new Slot(this.crafting, i * 6 + x, 8 + x * 18, 5 + i * 18));
         }
      }

      for(i = 0; i < 4; ++i) {
         this.addSlotToContainer(new SlotOutput(this.output, i, 132 + (i - i / 2 * 2) * 18, 41 + i / 2 * 18));
      }

      this.addPlayerSlotsToContainer(this.player, 8, 120);
   }

   public void onGuiPacket(int controlID, NBTTagCompound nbt, EntityPlayer player) {
      if(controlID == 0) {
         AdvancedGridRecipe recipe = null;

         int i;
         for(i = 0; i < BlockAdvancedWorkbench.recipes.size(); ++i) {
            if(((AdvancedGridRecipe)BlockAdvancedWorkbench.recipes.get(i)).isValidRecipe(this.crafting, 6, 6)) {
               recipe = (AdvancedGridRecipe)BlockAdvancedWorkbench.recipes.get(i);
               break;
            }
         }

         if(recipe != null) {
            for(i = 0; i < recipe.output.length; ++i) {
               if(recipe.output[i] != null) {
                  ItemStack stack = recipe.output[i].copy();
                  if(!InventoryUtils.addItemStackToInventory(this.output, stack) && !InventoryUtils.addItemStackToInventory(player.inventory, stack)) {
                     WorldUtils.dropItem(player.worldObj, stack, (int)player.posX, (int)player.posY, (int)player.posZ);
                  }
               }
            }

            recipe.consumeRecipe(this.crafting, 6, 6);
         }
      }

   }

   public void onGuiClosed() {
      int i;
      for(i = 0; i < this.crafting.getSizeInventory(); ++i) {
         if(this.crafting.getStackInSlot(i) != null) {
            WorldUtils.dropItem(this.player.worldObj, this.crafting.getStackInSlot(i), (int)this.player.posX, (int)this.player.posY, (int)this.player.posZ);
         }
      }

      for(i = 0; i < this.output.getSizeInventory(); ++i) {
         if(this.output.getStackInSlot(i) != null) {
            WorldUtils.dropItem(this.player.worldObj, this.output.getStackInSlot(i), (int)this.player.posX, (int)this.player.posY, (int)this.player.posZ);
         }
      }

   }
}
