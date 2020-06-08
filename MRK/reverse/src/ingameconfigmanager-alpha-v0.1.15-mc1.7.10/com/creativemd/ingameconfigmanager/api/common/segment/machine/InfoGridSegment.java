package com.creativemd.ingameconfigmanager.api.common.segment.machine;

import com.creativemd.creativecore.common.container.SubContainer;
import com.creativemd.creativecore.common.gui.SubGui;
import com.creativemd.creativecore.common.utils.stack.StackInfo;
import com.creativemd.creativecore.common.utils.string.StringUtils;
import com.creativemd.ingameconfigmanager.api.common.container.controls.InfoSlotControl;
import com.creativemd.ingameconfigmanager.api.common.segment.ConfigSegment;
import com.creativemd.ingameconfigmanager.api.common.segment.machine.RecipeSegment;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import net.minecraft.item.ItemStack;

public class InfoGridSegment extends ConfigSegment {

   public RecipeSegment parent;


   public InfoGridSegment(String id, StackInfo[] defaultValue, RecipeSegment parent) {
      super(id, defaultValue);
      this.parent = parent;
   }

   public void empty() {
      for(int i = 0; i < super.containerControls.size(); ++i) {
         ((InfoSlotControl)super.containerControls.get(i)).slot.putStack((ItemStack)null);
         ((InfoSlotControl)super.containerControls.get(i)).info = null;
      }

   }

   public ArrayList createContainerControls(SubContainer gui, int x, int y, int maxWidth) {
      ArrayList slots = new ArrayList();
      if(super.value == null) {
         super.value = new StackInfo[this.parent.machine.getWidth() * this.parent.machine.getHeight()];
      }

      for(int i = 0; i < ((StackInfo[])super.value).length; ++i) {
         slots.add(new InfoSlotControl(5 + x + i * 18 - i / this.parent.machine.getWidth() * this.parent.machine.getWidth() * 18, y + i / this.parent.machine.getWidth() * 18, ((StackInfo[])super.value)[i]));
      }

      return slots;
   }

   @SideOnly(Side.CLIENT)
   public ArrayList createGuiControls(SubGui gui, int x, int y, int maxWidth) {
      return new ArrayList();
   }

   public String createPacketInformation(boolean isServer) {
      if(!isServer && super.containerControls.size() == ((StackInfo[])super.value).length) {
         for(int i = 0; i < ((StackInfo[])super.value).length; ++i) {
            ((StackInfo[])super.value)[i] = ((InfoSlotControl)super.containerControls.get(i)).info;
         }
      }

      return null;
   }

   public void receivePacketInformation(String input) {
      super.value = new StackInfo[this.parent.machine.getWidth() * this.parent.machine.getHeight()];
      Object[] objects = StringUtils.StringToObjects(input);

      for(int i = 0; i < objects.length; ++i) {
         ((StackInfo[])super.value)[i] = (StackInfo)objects[i];
      }

   }

   public boolean contains(String search) {
      return false;
   }
}
