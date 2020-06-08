package com.creativemd.ingameconfigmanager.api.common.branch;

import com.creativemd.creativecore.client.avatar.Avatar;
import com.creativemd.ingameconfigmanager.api.common.branch.ConfigSegmentCollection;
import com.creativemd.ingameconfigmanager.api.common.segment.ConfigSegment;
import com.creativemd.ingameconfigmanager.api.tab.ModTab;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;

public abstract class ConfigBranch {

   public static ArrayList branches = new ArrayList();
   public ModTab tab;
   public String name;
   public final int id;
   @SideOnly(Side.CLIENT)
   public Avatar avatar;
   protected ArrayList segments = null;


   public static int indexOf(ConfigBranch branch) {
      return branches.indexOf(branch);
   }

   public static ConfigBranch getBranchByID(int id) {
      return id >= 0 && id < branches.size()?(ConfigBranch)branches.get(id):null;
   }

   public ConfigBranch(String name) {
      this.id = branches.size();
      branches.add(this);
      this.name = name;
      if(FMLCommonHandler.instance().getEffectiveSide().isClient()) {
         this.avatar = this.getAvatar();
      }

   }

   @SideOnly(Side.CLIENT)
   protected abstract Avatar getAvatar();

   @SideOnly(Side.CLIENT)
   public boolean isSearchable() {
      return true;
   }

   public ArrayList getConfigSegments() {
      if(this.segments == null) {
         this.segments = new ArrayList();
         this.createConfigSegments();
      }

      return this.segments;
   }

   public abstract void loadCore();

   public abstract void createConfigSegments();

   public abstract boolean needPacket();

   public void onRecieveFromPre(boolean isServer, ConfigSegmentCollection collection) {}

   public abstract void onRecieveFrom(boolean var1, ConfigSegmentCollection var2);

   public void onRecieveFromPost(boolean isServer, ConfigSegmentCollection collection) {}

   public void onPacketSend(boolean isServer, ConfigSegmentCollection collection) {}

   public void onBeforeReceived(boolean isServer) {}

   public ConfigSegment onFailedLoadingSegment(ConfigSegmentCollection collection, String id, String input, int currentIndex) {
      return null;
   }

   public boolean doesInputSupportStackSize() {
      return true;
   }

}
