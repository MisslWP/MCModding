package com.creativemd.ingameconfigmanager.mod.general;

import com.creativemd.creativecore.client.avatar.Avatar;
import com.creativemd.creativecore.client.avatar.AvatarItemStack;
import com.creativemd.ingameconfigmanager.api.common.branch.ConfigBranch;
import com.creativemd.ingameconfigmanager.api.common.branch.ConfigSegmentCollection;
import com.creativemd.ingameconfigmanager.api.common.segment.BooleanSegment;
import com.creativemd.ingameconfigmanager.api.common.segment.IntegerSegment;
import com.creativemd.ingameconfigmanager.api.core.InGameConfigManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class GeneralBranch extends ConfigBranch {

   public GeneralBranch(String name) {
      super(name);
   }

   @SideOnly(Side.CLIENT)
   protected Avatar getAvatar() {
      return new AvatarItemStack(new ItemStack(Items.paper));
   }

   public void loadCore() {}

   public void createConfigSegments() {
      super.segments.add(new BooleanSegment("overrideWorkbench", "override default workbench", Boolean.valueOf(false)));
      super.segments.add(new IntegerSegment("maxSegments", "segments per packet", Integer.valueOf(10), 1, 1000));
   }

   public boolean needPacket() {
      return true;
   }

   public void onRecieveFrom(boolean isServer, ConfigSegmentCollection collection) {
      InGameConfigManager.overrideWorkbench = ((Boolean)collection.getSegmentValue("overrideWorkbench")).booleanValue();
      InGameConfigManager.maxSegments = ((Integer)collection.getSegmentValue("maxSegments")).intValue();
   }
}
