package com.creativemd.ingameconfigmanager.api.common.segment;

import com.creativemd.creativecore.common.container.SubContainer;
import com.creativemd.creativecore.common.gui.SubGui;
import com.creativemd.creativecore.common.gui.controls.GuiTextfield;
import com.creativemd.ingameconfigmanager.api.common.segment.TitleSegment;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;

public class IntegerSegment extends TitleSegment {

   public int min;
   public int max;


   public IntegerSegment(String id, String title, Integer defaultValue) {
      this(id, title, defaultValue, 0, Integer.MAX_VALUE);
   }

   public IntegerSegment(String id, String title, Integer defaultValue, int min, int max) {
      super(id, title, defaultValue);
      this.min = min;
      this.max = max;
   }

   public ArrayList createContainerControls(SubContainer gui, int x, int y, int maxWidth) {
      ArrayList controls = new ArrayList();
      return controls;
   }

   @SideOnly(Side.CLIENT)
   public ArrayList createGuiControls(SubGui gui, int x, int y, int maxWidth) {
      ArrayList controls = super.createGuiControls(gui, x, y, maxWidth);
      controls.add((new GuiTextfield(this.getID(), "" + super.value, x + maxWidth - 50, y, 40, 20)).setNumbersOnly());
      return controls;
   }

   public String createPacketInformation(boolean isServer) {
      if(!isServer && super.guiControls != null && super.guiControls.size() == 2) {
         int valueBefore = ((Integer)super.value).intValue();

         try {
            super.value = Integer.valueOf(Integer.parseInt(((GuiTextfield)super.guiControls.get(1)).text));
         } catch (Exception var4) {
            super.value = Integer.valueOf(valueBefore);
         }

         super.value = Integer.valueOf(Math.max(this.min, ((Integer)super.value).intValue()));
         super.value = Integer.valueOf(Math.min(this.max, ((Integer)super.value).intValue()));
      }

      return ((Integer)super.value).toString();
   }

   public void receivePacketInformation(String input) {
      super.value = Integer.valueOf(Integer.parseInt(input));
   }

   public boolean contains(String search) {
      return super.contains(search) || ((Integer)super.value).toString().contains(search);
   }
}
