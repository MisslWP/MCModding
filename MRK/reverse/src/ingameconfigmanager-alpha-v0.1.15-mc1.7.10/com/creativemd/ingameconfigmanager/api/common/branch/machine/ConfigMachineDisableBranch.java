package com.creativemd.ingameconfigmanager.api.common.branch.machine;

import com.creativemd.creativecore.client.avatar.Avatar;
import com.creativemd.creativecore.client.avatar.AvatarItemStack;
import com.creativemd.creativecore.common.utils.string.StringUtils;
import com.creativemd.ingameconfigmanager.api.common.branch.ConfigBranch;
import com.creativemd.ingameconfigmanager.api.common.branch.ConfigSegmentCollection;
import com.creativemd.ingameconfigmanager.api.common.machine.RecipeMachine;
import com.creativemd.ingameconfigmanager.api.common.segment.BooleanSegment;
import com.creativemd.ingameconfigmanager.api.common.segment.ConfigSegment;
import com.creativemd.ingameconfigmanager.api.common.segment.machine.DisableRecipeSegment;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import net.minecraft.item.ItemStack;

public class ConfigMachineDisableBranch extends ConfigBranch {

   public RecipeMachine machine;
   public ArrayList allRecipes;
   public boolean disableAll = false;


   public ConfigMachineDisableBranch(RecipeMachine machine, String name) {
      super(name);
      this.machine = machine;
      if(FMLCommonHandler.instance().getEffectiveSide().isClient()) {
         super.avatar = this.getAvatar();
      }

   }

   @SideOnly(Side.CLIENT)
   protected Avatar getAvatar() {
      return this.machine != null?new AvatarItemStack(this.machine.getAvatar()):null;
   }

   public void loadCore() {
      this.allRecipes = this.machine.getAllExitingRecipes();
   }

   public void createConfigSegments() {
      super.segments.add(new BooleanSegment("disableAll", "Disable all recipes", Boolean.valueOf(false)));

      for(int i = 0; i < this.allRecipes.size(); ++i) {
         super.segments.add(new DisableRecipeSegment(this.recipeToString(this.allRecipes.get(i)), Boolean.valueOf(true), this.machine, this.allRecipes.get(i)));
      }

   }

   public boolean doesInputSupportStackSize() {
      return this.machine.doesSupportStackSize();
   }

   public String recipeToString(Object recipe) {
      ItemStack[] input = new ItemStack[this.machine.getHeight() * this.machine.getWidth()];
      this.machine.fillGrid(input, recipe);
      ItemStack[] output = this.machine.getOutput(recipe);
      return StringUtils.ObjectsToString(new Object[]{input, output});
   }

   public boolean needPacket() {
      return true;
   }

   public void onBeforeReceived(boolean isServer) {
      for(int i = 0; i < super.segments.size(); ++i) {
         if(super.segments.get(i) instanceof DisableRecipeSegment) {
            ((ConfigSegment)super.segments.get(i)).value = Boolean.valueOf(true);
         }
      }

   }

   public void onRecieveFrom(boolean isServer, ConfigSegmentCollection collection) {
      this.machine.clearRecipeList();
      this.disableAll = ((Boolean)collection.getSegmentValue("disableAll")).booleanValue();
      if(!this.disableAll) {
         for(int i = 1; i < collection.asList().size(); ++i) {
            if(collection.asList().get(i) instanceof DisableRecipeSegment && ((Boolean)((ConfigSegment)collection.asList().get(i)).value).booleanValue()) {
               this.machine.addRecipeToList(((DisableRecipeSegment)collection.asList().get(i)).recipe);
            }
         }
      }

      if(!this.machine.sendingUpdate && this.machine.hasAddedBranch()) {
         this.machine.sendingUpdate = true;
         this.machine.addBranch.onRecieveFrom(isServer, new ConfigSegmentCollection(this.machine.addBranch.getConfigSegments()));
         this.machine.sendingUpdate = false;
      }

   }
}
