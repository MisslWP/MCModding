package com.creativemd.ingameconfigmanager.api.common.container;

import com.creativemd.creativecore.common.container.SubContainer;
import com.creativemd.creativecore.common.gui.premade.SubContainerDialog;
import com.creativemd.ingameconfigmanager.api.common.branch.ConfigBranch;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class SubContainerBranch extends SubContainer {

   public ConfigBranch branch;


   public SubContainerBranch(EntityPlayer player, ConfigBranch branch) {
      super(player);
      this.branch = branch;
   }

   public void createControls() {}

   public void onGuiPacket(int controlID, NBTTagCompound nbt, EntityPlayer player) {}

   public SubContainer createLayerFromPacket(World world, EntityPlayer player, NBTTagCompound nbt) {
      return (SubContainer)(nbt.getBoolean("ItemDialog")?new SubContainerDialog(player):super.createLayerFromPacket(world, player, nbt));
   }
}
