package com.creativemd.ingameconfigmanager.mod.workbench;

import com.creativemd.creativecore.common.gui.controls.GuiControl;
import com.creativemd.creativecore.common.gui.controls.GuiStateButton;
import com.creativemd.creativecore.common.recipe.BetterShapedRecipe;
import com.creativemd.creativecore.common.recipe.RecipeLoader;
import com.creativemd.creativecore.common.recipe.entry.BetterShapelessRecipe;
import com.creativemd.creativecore.common.utils.stack.StackInfo;
import com.creativemd.ingameconfigmanager.api.common.machine.RecipeMachine;
import com.creativemd.ingameconfigmanager.api.common.segment.machine.AddRecipeSegment;
import com.creativemd.ingameconfigmanager.api.tab.ModTab;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;

public class WorkbenchMachine extends RecipeMachine {

   public WorkbenchMachine(ModTab tab, String name) {
      super(tab, name);
   }

   public int getOutputCount() {
      return 1;
   }

   @SideOnly(Side.CLIENT)
   public ItemStack getAvatar() {
      return new ItemStack(Blocks.crafting_table);
   }

   public int getWidth() {
      return 3;
   }

   public int getHeight() {
      return 3;
   }

   public void addRecipeToList(IRecipe recipe) {
      CraftingManager.getInstance().getRecipeList().add(recipe);
   }

   public void clearRecipeList() {
      CraftingManager.getInstance().getRecipeList().clear();
   }

   public ArrayList getAllExitingRecipes() {
      ArrayList recipes = new ArrayList();
      List vanillaRecipes = CraftingManager.getInstance().getRecipeList();

      for(int i = 0; i < vanillaRecipes.size(); ++i) {
         recipes.add((IRecipe)vanillaRecipes.get(i));
      }

      return recipes;
   }

   public ItemStack[] getOutput(IRecipe recipe) {
      return new ItemStack[]{recipe.getRecipeOutput()};
   }

   public void getInput(ItemStack[] grid, Object[] items, int size, int cols) {
      if(items != null) {
         if(cols == 0) {
            switch(size) {
            case 1:
               boolean var8 = true;
            case 2:
               cols = 2;
               break;
            case 3:
               cols = 3;
               break;
            case 4:
               cols = 2;
               break;
            case 5:
            default:
               cols = 3;
               break;
            case 6:
               cols = 3;
            }
         }

         for(int zahl = 0; zahl < size; ++zahl) {
            int row = zahl / cols;
            int index = row * 3 + zahl - row * cols;
            if(items.length > zahl && index < 9 && index > -1) {
               grid[index] = this.getItemStack(items[zahl]);
            }
         }

      }
   }

   public ItemStack getItemStack(Object object) {
      ItemStack[] result = this.ObjectoItemStack(object);
      ItemStack stack = null;
      if(result.length > 0) {
         stack = result[result.length - 1];
      }

      if(stack != null) {
         stack.stackSize = 1;
      }

      return stack;
   }

   public ItemStack[] ObjectoItemStack(Object object) {
      if(object instanceof Item) {
         return new ItemStack[]{new ItemStack((Item)object)};
      } else if(object instanceof Block) {
         return new ItemStack[]{new ItemStack((Block)object)};
      } else if(object instanceof ItemStack) {
         return new ItemStack[]{((ItemStack)object).copy()};
      } else if(object instanceof List) {
         List stacks = (List)object;
         ItemStack[] result = new ItemStack[stacks.size()];

         for(int zahl = 0; zahl < stacks.size(); ++zahl) {
            if(stacks.get(zahl) instanceof ItemStack) {
               result[zahl] = ((ItemStack)stacks.get(zahl)).copy();
            }
         }

         return result;
      } else {
         return new ItemStack[0];
      }
   }

   public void fillGrid(ItemStack[] grid, IRecipe recipe) {
      this.getInput(grid, RecipeLoader.getInput(recipe), recipe.getRecipeSize(), RecipeLoader.getWidth(recipe));
   }

   public void fillGridInfo(StackInfo[] grid, IRecipe recipe) {
      int i;
      if(recipe instanceof BetterShapedRecipe) {
         for(i = 0; i < ((BetterShapedRecipe)recipe).info.length; ++i) {
            int row = i / ((BetterShapedRecipe)recipe).getWidth();
            int index = row * 3 + (i - row * ((BetterShapedRecipe)recipe).getWidth());
            grid[index] = ((BetterShapedRecipe)recipe).info[i];
         }
      } else if(recipe instanceof BetterShapelessRecipe) {
         for(i = 0; i < ((BetterShapelessRecipe)recipe).info.size(); ++i) {
            grid[i] = (StackInfo)((BetterShapelessRecipe)recipe).info.get(i);
         }
      }

   }

   public void onBeforeSave(IRecipe recipe, NBTTagCompound nbt) {
      nbt.setBoolean("shaped", recipe instanceof BetterShapedRecipe);
   }

   public void parseExtraInfo(NBTTagCompound nbt, AddRecipeSegment segment, ArrayList guiControls, ArrayList containerControls) {
      for(int i = 0; i < guiControls.size(); ++i) {
         if(((GuiControl)guiControls.get(i)).is("type")) {
            nbt.setBoolean("shaped", ((GuiStateButton)guiControls.get(i)).getState() == 0);
         }
      }

   }

   public void onControlsCreated(IRecipe recipe, boolean isAdded, int x, int y, int maxWidth, ArrayList guiControls, ArrayList containerControls) {
      if(isAdded) {
         guiControls.add(new GuiStateButton("type", recipe instanceof BetterShapelessRecipe?1:0, x + maxWidth - 80, y, 70, 20, new String[]{"Shaped", "Shapeless"}));
      }

   }

   public IRecipe parseRecipe(StackInfo[] input, ItemStack[] output, NBTTagCompound nbt, int width, int height) {
      if(output.length == 1 && output[0] != null) {
         ItemStack result = output[0].copy();
         if(nbt.getBoolean("shaped")) {
            return new BetterShapedRecipe(width, input, result);
         }

         ArrayList info = new ArrayList();

         for(int i = 0; i < input.length; ++i) {
            if(input[i] != null) {
               info.add(input[i]);
            }
         }

         if(info.size() > 0) {
            return new BetterShapelessRecipe(info, result);
         }
      }

      return null;
   }

   public boolean doesSupportStackSize() {
      return false;
   }
}
