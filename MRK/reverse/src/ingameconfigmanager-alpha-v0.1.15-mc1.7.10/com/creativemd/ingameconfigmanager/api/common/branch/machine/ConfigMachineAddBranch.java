package com.creativemd.ingameconfigmanager.api.common.branch.machine;

import com.creativemd.creativecore.client.avatar.Avatar;
import com.creativemd.creativecore.client.avatar.AvatarItemStack;
import com.creativemd.ingameconfigmanager.api.common.branch.ConfigBranch;
import com.creativemd.ingameconfigmanager.api.common.branch.ConfigSegmentCollection;
import com.creativemd.ingameconfigmanager.api.common.machine.RecipeMachine;
import com.creativemd.ingameconfigmanager.api.common.segment.ConfigSegment;
import com.creativemd.ingameconfigmanager.api.common.segment.machine.AddRecipeSegment;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;

public class ConfigMachineAddBranch extends ConfigBranch {

   public RecipeMachine machine;
   public ArrayList allRecipes;
   public ArrayList disabledRecipes;


   public ConfigMachineAddBranch(RecipeMachine machine, String name) {
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

   public boolean doesInputSupportStackSize() {
      return this.machine.doesSupportStackSize();
   }

   public void loadCore() {}

   public void createConfigSegments() {
      super.segments.add(new AddRecipeSegment("0", this.machine, (Object)null));
   }

   public boolean needPacket() {
      return true;
   }

   public void onBeforeReceived(boolean isServer) {
      super.segments.clear();
   }

   public int findNextId(ConfigSegmentCollection collection) {
      int id;
      for(id = 0; collection.getSegmentByID("" + id) != null; ++id) {
         ;
      }

      return id;
   }

   public void onRecieveFrom(boolean isServer, ConfigSegmentCollection collection) {
      if(!this.machine.sendingUpdate) {
         if(this.machine.hasDisableBranch()) {
            this.machine.sendingUpdate = true;
            this.machine.disableBranch.onRecieveFrom(isServer, new ConfigSegmentCollection(this.machine.disableBranch.getConfigSegments()));
            this.machine.sendingUpdate = false;
         } else {
            this.machine.clearRecipeList();
         }
      }

      if(super.segments != null) {
         int i = 0;

         while(i < super.segments.size()) {
            if(((ConfigSegment)super.segments.get(i)).value != null) {
               this.machine.addRecipeToList(((ConfigSegment)super.segments.get(i)).value);
               ++i;
            } else {
               super.segments.remove(i);
            }
         }

         int id = this.findNextId(collection);
         super.segments.add(new AddRecipeSegment("" + id, this.machine, (Object)null));
      }

   }

   public ConfigSegment onFailedLoadingSegment(ConfigSegmentCollection collection, String id, String input, int currentIndex) {
      AddRecipeSegment segment = new AddRecipeSegment("" + this.findNextId(collection), this.machine, (Object)null);
      return segment;
   }

   @SideOnly(Side.CLIENT)
   public boolean isSearchable() {
      return false;
   }
}
