package com.creativemd.ingameconfigmanager.api.common.container;

import com.creativemd.creativecore.common.container.SubContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class SubContainerMods extends SubContainer {

   public SubContainerMods(EntityPlayer player) {
      super(player);
   }

   public void createControls() {}

   public void onGuiPacket(int controlID, NBTTagCompound nbt, EntityPlayer player) {}
}
