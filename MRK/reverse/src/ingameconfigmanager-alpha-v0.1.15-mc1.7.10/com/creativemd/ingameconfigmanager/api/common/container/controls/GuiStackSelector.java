package com.creativemd.ingameconfigmanager.api.common.container.controls;

import com.creativemd.ingameconfigmanager.api.common.container.controls.GuiInvSelector;
import com.creativemd.ingameconfigmanager.api.common.container.controls.GuiInvSelectorExtension;
import net.minecraft.entity.player.EntityPlayer;

public class GuiStackSelector extends GuiInvSelector {

   public GuiStackSelector(String name, int x, int y, int width, EntityPlayer player, boolean onlyBlocks) {
      super(name, x, y, width, player, onlyBlocks);
   }

   public void openBox() {
      this.extension = new GuiInvSelectorExtension(this.name + "extension", this.parent.container.player, this, this.posX, this.posY + this.height, this.width, 200, this.lines, super.stacks);
      this.parent.controls.add(this.extension);
      this.extension.parent = this.parent;
      this.extension.moveControlToTop();
      this.extension.init();
      this.parent.refreshControls();
      this.extension.rotation = this.rotation;
   }
}
