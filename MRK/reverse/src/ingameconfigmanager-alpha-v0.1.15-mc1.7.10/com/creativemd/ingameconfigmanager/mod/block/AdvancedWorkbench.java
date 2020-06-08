package com.creativemd.ingameconfigmanager.mod.block;

import com.creativemd.creativecore.common.gui.controls.GuiControl;
import com.creativemd.creativecore.common.gui.controls.GuiTextfield;
import com.creativemd.creativecore.common.utils.stack.StackInfo;
import com.creativemd.ingameconfigmanager.api.common.machine.RecipeMachine;
import com.creativemd.ingameconfigmanager.api.common.segment.machine.AddRecipeSegment;
import com.creativemd.ingameconfigmanager.api.tab.ModTab;
import com.creativemd.ingameconfigmanager.mod.block.AdvancedGridRecipe;
import com.creativemd.ingameconfigmanager.mod.block.BlockAdvancedWorkbench;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class AdvancedWorkbench extends RecipeMachine {

   public AdvancedWorkbench(ModTab tab, String name) {
      super(tab, name);
   }

   public int getWidth() {
      return 6;
   }

   public int getHeight() {
      return 6;
   }

   public int getOutputCount() {
      return 4;
   }

   public boolean hasDisableBranch() {
      return false;
   }

   public void addRecipeToList(AdvancedGridRecipe recipe) {
      BlockAdvancedWorkbench.recipes.add(recipe);
   }

   public void clearRecipeList() {
      BlockAdvancedWorkbench.recipes.clear();
   }

   public ItemStack[] getOutput(AdvancedGridRecipe recipe) {
      return recipe.output;
   }

   public ArrayList getAllExitingRecipes() {
      return new ArrayList(BlockAdvancedWorkbench.recipes);
   }

   public void fillGrid(ItemStack[] grid, AdvancedGridRecipe recipe) {}

   public void fillGridInfo(StackInfo[] grid, AdvancedGridRecipe recipe) {
      for(int i = 0; i < recipe.input.length; ++i) {
         int row = i / recipe.width;
         int index = row * this.getWidth() + (i - row * recipe.width);
         grid[index] = recipe.input[i];
      }

   }

   public AdvancedGridRecipe parseRecipe(StackInfo[] input, ItemStack[] output, NBTTagCompound nbt, int width, int height) {
      return input.length > 0 && output.length > 0?new AdvancedGridRecipe(output, width, height, input, nbt.getInteger("duration")):null;
   }

   public void onBeforeSave(AdvancedGridRecipe recipe, NBTTagCompound nbt) {
      nbt.setInteger("duration", recipe.duration);
   }

   public void parseExtraInfo(NBTTagCompound nbt, AddRecipeSegment segment, ArrayList guiControls, ArrayList containerControls) {
      for(int i = 0; i < guiControls.size(); ++i) {
         if(((GuiControl)guiControls.get(i)).is("duration")) {
            boolean duration = false;

            int var9;
            try {
               var9 = Integer.parseInt(((GuiTextfield)guiControls.get(i)).text);
            } catch (Exception var8) {
               var9 = 0;
            }

            nbt.setInteger("duration", var9);
         }
      }

   }

   public void onControlsCreated(AdvancedGridRecipe recipe, boolean isAdded, int x, int y, int maxWidth, ArrayList guiControls, ArrayList containerControls) {
      if(isAdded) {
         guiControls.add((new GuiTextfield("duration", recipe != null?"" + recipe.duration:"0", x + maxWidth - 80, y, 70, 20)).setNumbersOnly());
      }

   }

   @SideOnly(Side.CLIENT)
   public ItemStack getAvatar() {
      return new ItemStack(Blocks.crafting_table);
   }

   public boolean doesSupportStackSize() {
      return true;
   }
}
