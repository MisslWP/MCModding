package com.creativemd.ingameconfigmanager.api.common.segment.machine;

import com.creativemd.creativecore.common.container.SubContainer;
import com.creativemd.creativecore.common.container.slot.SlotControl;
import com.creativemd.creativecore.common.container.slot.SlotControlNoSync;
import com.creativemd.creativecore.common.container.slot.SlotPreview;
import com.creativemd.creativecore.common.gui.SubGui;
import com.creativemd.ingameconfigmanager.api.common.machine.RecipeMachine;
import com.creativemd.ingameconfigmanager.api.common.segment.ConfigSegment;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;

public class OutputSegment extends ConfigSegment {

   public RecipeMachine machine;


   public OutputSegment(String id, ItemStack[] defaultValue, RecipeMachine machine) {
      super(id, defaultValue);
      this.machine = machine;
   }

   public void empty() {
      for(int i = 0; i < super.containerControls.size(); ++i) {
         ((SlotControlNoSync)super.containerControls.get(i)).slot.putStack((ItemStack)null);
      }

   }

   public ArrayList createContainerControls(SubContainer gui, int x, int y, int maxWidth) {
      ArrayList controls = new ArrayList();
      if(super.value == null) {
         super.value = new ItemStack[this.machine.getOutputCount()];
      }

      for(int i = 0; i < ((ItemStack[])super.value).length; ++i) {
         InventoryBasic basic = new InventoryBasic("basic", false, 1);
         if(((ItemStack[])super.value)[i] != null) {
            basic.setInventorySlotContents(0, ((ItemStack[])super.value)[i].copy());
         }

         controls.add(new SlotControlNoSync(new SlotPreview(basic, 0, x, y + i * 18)));
      }

      return controls;
   }

   @SideOnly(Side.CLIENT)
   public ArrayList createGuiControls(SubGui gui, int x, int y, int maxWidth) {
      return new ArrayList();
   }

   public String createPacketInformation(boolean isServer) {
      if(!isServer && super.containerControls.size() > 0) {
         for(int i = 0; i < ((ItemStack[])super.value).length; ++i) {
            ((ItemStack[])super.value)[i] = ((SlotControl)super.containerControls.get(i)).slot.getStack();
         }
      }

      return null;
   }

   public void receivePacketInformation(String input) {}

   public boolean contains(String search) {
      return false;
   }
}
