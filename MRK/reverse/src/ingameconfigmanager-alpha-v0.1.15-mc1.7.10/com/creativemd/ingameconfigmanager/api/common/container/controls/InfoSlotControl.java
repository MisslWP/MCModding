package com.creativemd.ingameconfigmanager.api.common.container.controls;

import com.creativemd.creativecore.common.container.slot.SlotControlNoSync;
import com.creativemd.creativecore.common.container.slot.SlotPreview;
import com.creativemd.creativecore.common.gui.controls.GuiControl;
import com.creativemd.creativecore.common.gui.controls.container.GuiSlotControl;
import com.creativemd.creativecore.common.utils.stack.StackInfo;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;

public class InfoSlotControl extends SlotControlNoSync {

   public StackInfo info;
   public InventoryBasic inventory;


   public InfoSlotControl(int x, int y, StackInfo info) {
      super((Slot)null);
      this.info = info;
      this.inventory = new InventoryBasic("slot", false, 1);
      this.slot = new SlotPreview(this.inventory, 0, x, y);
      if(info != null) {
         this.slot.putStack(info.getItemStack());
      }

   }

   @SideOnly(Side.CLIENT)
   public GuiControl createGuiControl() {
      return new GuiSlotControl(this.slot.xDisplayPosition, this.slot.yDisplayPosition, this);
   }
}
