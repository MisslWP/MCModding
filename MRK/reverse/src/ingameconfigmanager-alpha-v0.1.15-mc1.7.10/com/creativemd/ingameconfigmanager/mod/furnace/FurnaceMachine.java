package com.creativemd.ingameconfigmanager.mod.furnace;

import com.creativemd.creativecore.common.gui.controls.GuiControl;
import com.creativemd.creativecore.common.gui.controls.GuiLabel;
import com.creativemd.creativecore.common.gui.controls.GuiTextfield;
import com.creativemd.creativecore.common.utils.stack.StackInfo;
import com.creativemd.creativecore.common.utils.stack.StackInfoBlock;
import com.creativemd.creativecore.common.utils.stack.StackInfoFuel;
import com.creativemd.creativecore.common.utils.stack.StackInfoItem;
import com.creativemd.creativecore.common.utils.stack.StackInfoItemStack;
import com.creativemd.creativecore.common.utils.stack.StackInfoMaterial;
import com.creativemd.creativecore.common.utils.stack.StackInfoOre;
import com.creativemd.ingameconfigmanager.api.common.machine.RecipeMachine;
import com.creativemd.ingameconfigmanager.api.common.segment.machine.AddRecipeSegment;
import com.creativemd.ingameconfigmanager.api.tab.ModTab;
import com.creativemd.ingameconfigmanager.mod.furnace.FurnaceRecipe;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.oredict.OreDictionary;

public class FurnaceMachine extends RecipeMachine {

   public FurnaceMachine(ModTab tab, String name) {
      super(tab, name);
   }

   public int getWidth() {
      return 1;
   }

   public int getHeight() {
      return 1;
   }

   public int getOutputCount() {
      return 1;
   }

   public void addRecipeToList(FurnaceRecipe recipe) {
      StackInfo info = recipe.input[0];
      ItemStack output = recipe.output[0];
      if(info != null && output != null) {
         if(info instanceof StackInfoBlock) {
            FurnaceRecipes.smelting().func_151393_a(((StackInfoBlock)info).block, output, recipe.experience);
         }

         if(info instanceof StackInfoItem) {
            FurnaceRecipes.smelting().func_151396_a(((StackInfoItem)info).item, output, recipe.experience);
         }

         if(info instanceof StackInfoItemStack) {
            FurnaceRecipes.smelting().func_151394_a(((StackInfoItemStack)info).stack.copy(), output, recipe.experience);
         }

         ArrayList stacks;
         if(info instanceof StackInfoOre) {
            stacks = OreDictionary.getOres(((StackInfoOre)info).ore);

            for(int iterator = 0; iterator < stacks.size(); ++iterator) {
               FurnaceRecipes.smelting().func_151394_a((ItemStack)stacks.get(iterator), output, 0.1F);
            }
         }

         Iterator var8;
         if(info instanceof StackInfoMaterial) {
            new ArrayList();
            var8 = Block.blockRegistry.getKeys().iterator();

            while(var8.hasNext()) {
               Object i = var8.next();
               Block block = Block.getBlockFromName((String)i);
               if(block != null && block.getMaterial() == ((StackInfoMaterial)info).material) {
                  FurnaceRecipes.smelting().func_151393_a(block, output, recipe.experience);
               }
            }
         }

         if(info instanceof StackInfoFuel) {
            stacks = new ArrayList();
            var8 = Item.itemRegistry.iterator();

            while(var8.hasNext()) {
               Item var9 = (Item)var8.next();
               if(var9 != null && var9.getCreativeTab() != null) {
                  var9.getSubItems(var9, (CreativeTabs)null, stacks);
               }
            }

            for(int var10 = 0; var10 < stacks.size(); ++var10) {
               if(TileEntityFurnace.isItemFuel((ItemStack)stacks.get(var10))) {
                  FurnaceRecipes.smelting().func_151394_a((ItemStack)stacks.get(var10), output, 0.1F);
               }
            }
         }
      }

   }

   public void clearRecipeList() {
      FurnaceRecipes.smelting().getSmeltingList().clear();
   }

   public ItemStack[] getOutput(FurnaceRecipe recipe) {
      return recipe.output;
   }

   public ArrayList getAllExitingRecipes() {
      ArrayList recipes = new ArrayList();
      Object[] array = FurnaceRecipes.smelting().getSmeltingList().keySet().toArray();

      for(int zahl = 0; zahl < array.length; ++zahl) {
         if(array[zahl] != null) {
            Object object = FurnaceRecipes.smelting().getSmeltingList().get(array[zahl]);
            if(object instanceof ItemStack && ((ItemStack)object).getItem() != null) {
               recipes.add(new FurnaceRecipe((ItemStack)object, array[zahl], FurnaceRecipes.smelting().func_151398_b((ItemStack)object)));
            }
         }
      }

      return recipes;
   }

   public void fillGrid(ItemStack[] grid, FurnaceRecipe recipe) {
      if(recipe.input[0] != null) {
         grid[0] = recipe.input[0].getItemStack();
      }

   }

   public void fillGridInfo(StackInfo[] grid, FurnaceRecipe recipe) {
      grid[0] = recipe.input[0];
   }

   public FurnaceRecipe parseRecipe(StackInfo[] input, ItemStack[] output, NBTTagCompound nbt, int width, int height) {
      return input.length == 1 && input[0] != null && output.length == 1 && output[0] != null?new FurnaceRecipe(output[0], input[0], nbt.getFloat("exp")):null;
   }

   public ItemStack getAvatar() {
      return new ItemStack(Blocks.furnace);
   }

   public void onBeforeSave(FurnaceRecipe recipe, NBTTagCompound nbt) {
      nbt.setFloat("exp", recipe.experience);
   }

   public void parseExtraInfo(NBTTagCompound nbt, AddRecipeSegment segment, ArrayList guiControls, ArrayList containerControls) {
      for(int i = 0; i < guiControls.size(); ++i) {
         if(((GuiControl)guiControls.get(i)).is("exp")) {
            float exp = 0.0F;

            try {
               exp = Float.parseFloat(((GuiTextfield)guiControls.get(i)).text);
            } catch (Exception var8) {
               exp = 0.0F;
            }

            nbt.setFloat("exp", exp);
         }
      }

   }

   public void onControlsCreated(FurnaceRecipe recipe, boolean isAdded, int x, int y, int maxWidth, ArrayList guiControls, ArrayList containerControls) {
      if(isAdded) {
         guiControls.add((new GuiTextfield("exp", recipe != null?"" + recipe.experience:"0.0", x + maxWidth - 80, y, 70, 20)).setFloatOnly());
      } else {
         guiControls.add(new GuiLabel("exp: " + recipe.experience, x + maxWidth - 80, y));
      }

   }

   public boolean doesSupportStackSize() {
      return false;
   }
}
