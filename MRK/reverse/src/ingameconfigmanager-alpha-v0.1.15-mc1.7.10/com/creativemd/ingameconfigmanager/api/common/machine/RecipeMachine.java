package com.creativemd.ingameconfigmanager.api.common.machine;

import com.creativemd.creativecore.common.utils.stack.StackInfo;
import com.creativemd.ingameconfigmanager.api.common.branch.machine.ConfigMachineAddBranch;
import com.creativemd.ingameconfigmanager.api.common.branch.machine.ConfigMachineDisableBranch;
import com.creativemd.ingameconfigmanager.api.common.segment.machine.AddRecipeSegment;
import com.creativemd.ingameconfigmanager.api.tab.ModTab;
import java.util.ArrayList;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class RecipeMachine {

   public boolean sendingUpdate;
   public String name;
   public ConfigMachineDisableBranch disableBranch;
   public ConfigMachineAddBranch addBranch;


   public RecipeMachine(ModTab tab, String name) {
      this.name = name;
      if(this.hasDisableBranch()) {
         this.disableBranch = new ConfigMachineDisableBranch(this, name + " Disable");
         tab.addBranch(this.disableBranch);
      }

      if(this.hasAddedBranch()) {
         this.addBranch = new ConfigMachineAddBranch(this, name + " Add");
         tab.addBranch(this.addBranch);
      }

   }

   public boolean hasDisableBranch() {
      return true;
   }

   public boolean hasAddedBranch() {
      return true;
   }

   public abstract int getWidth();

   public abstract int getHeight();

   public abstract int getOutputCount();

   public abstract void addRecipeToList(Object var1);

   public abstract void clearRecipeList();

   public abstract ItemStack[] getOutput(Object var1);

   public void onControlsCreated(Object recipe, boolean isAdded, int x, int y, int maxWidth, ArrayList guiControls, ArrayList containerControls) {}

   public ArrayList getCustomSegments() {
      return new ArrayList();
   }

   public abstract ArrayList getAllExitingRecipes();

   public abstract void fillGrid(ItemStack[] var1, Object var2);

   public abstract boolean doesSupportStackSize();

   public abstract void fillGridInfo(StackInfo[] var1, Object var2);

   public void onBeforeSave(Object recipe, NBTTagCompound nbt) {}

   public void parseExtraInfo(NBTTagCompound nbt, AddRecipeSegment segment, ArrayList guiControls, ArrayList containerControls) {}

   public abstract Object parseRecipe(StackInfo[] var1, ItemStack[] var2, NBTTagCompound var3, int var4, int var5);

   public abstract ItemStack getAvatar();
}
