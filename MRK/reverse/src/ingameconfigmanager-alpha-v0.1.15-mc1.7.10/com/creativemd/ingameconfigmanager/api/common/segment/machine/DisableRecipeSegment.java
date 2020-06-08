package com.creativemd.ingameconfigmanager.api.common.segment.machine;

import com.creativemd.creativecore.common.gui.SubGui;
import com.creativemd.creativecore.common.gui.controls.GuiButton;
import com.creativemd.creativecore.common.gui.controls.GuiStateButton;
import com.creativemd.ingameconfigmanager.api.common.machine.RecipeMachine;
import com.creativemd.ingameconfigmanager.api.common.segment.machine.GridSegment;
import com.creativemd.ingameconfigmanager.api.common.segment.machine.RecipeSegment;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class DisableRecipeSegment extends RecipeSegment {

   public DisableRecipeSegment(String id, Boolean defaultValue, RecipeMachine machine, Object recipe) {
      super(id, defaultValue, machine, recipe);
   }

   public void addSubSegments() {
      ItemStack[] items = new ItemStack[super.machine.getWidth() * super.machine.getHeight()];
      super.machine.fillGrid(items, super.recipe);
      this.addSubSegment((new GridSegment("grid", items, super.machine)).setOffset(30, 5));
      this.addSubSegment((new GridSegment("result", super.machine.getOutput(super.recipe), super.machine)).setOffset(130, 5 + super.machine.getHeight() / 2 * 18));
   }

   @SideOnly(Side.CLIENT)
   public ArrayList createGuiControls(SubGui gui, int x, int y, int maxWidth) {
      ArrayList controls = super.createGuiControls(gui, x, y, maxWidth);
      controls.add(new GuiStateButton("Enabled", ((Boolean)super.value).booleanValue()?0:1, x + 150, y + 20, 50, 20, new String[]{"Enabled", "Disabled"}));
      return controls;
   }

   public String createPacketInformation(boolean isServer) {
      if(!isServer && super.guiControls != null) {
         for(int i = 0; i < super.guiControls.size(); ++i) {
            if(super.guiControls.get(i) instanceof GuiButton) {
               super.value = Boolean.valueOf(((GuiButton)super.guiControls.get(i)).caption.equals("Enabled"));
            }
         }
      }

      return !((Boolean)super.value).booleanValue()?"false":null;
   }

   public void receivePacketInformation(String input) {
      super.value = Boolean.valueOf(false);
   }

   public boolean contains(String search) {
      ItemStack[] items = new ItemStack[super.machine.getWidth() * super.machine.getHeight()];
      super.machine.fillGrid(items, super.recipe);

      for(int i = 0; i < items.length; ++i) {
         if(items[i] != null && items[i].getItem() instanceof ItemBlock) {
            if(Block.blockRegistry.getNameForObject(Block.getBlockFromItem(items[i].getItem())).toLowerCase().contains(search)) {
               return true;
            }

            if(Item.itemRegistry.getNameForObject(items[i].getItem()).toLowerCase().contains(search)) {
               return true;
            }
         }
      }

      if(this.getID().toLowerCase().contains(search)) {
         return true;
      } else if(((Boolean)super.value).booleanValue()) {
         return "enabled".contains(search);
      } else {
         return "disabled".contains(search);
      }
   }
}
