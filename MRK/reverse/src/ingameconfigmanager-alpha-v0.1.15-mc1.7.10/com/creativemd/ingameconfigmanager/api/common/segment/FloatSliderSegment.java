package com.creativemd.ingameconfigmanager.api.common.segment;

import com.creativemd.creativecore.common.gui.SubGui;
import com.creativemd.creativecore.common.gui.controls.GuiAnalogeSlider;
import com.creativemd.ingameconfigmanager.api.common.segment.FloatSegment;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;

public class FloatSliderSegment extends FloatSegment {

   public FloatSliderSegment(String id, String title, Float defaultValue, float min, float max) {
      super(id, title, defaultValue, min, max);
   }

   @SideOnly(Side.CLIENT)
   public ArrayList createGuiControls(SubGui gui, int x, int y, int maxWidth) {
      ArrayList controls = super.createGuiControls(gui, x, y, maxWidth);
      controls.remove(1);
      controls.add(new GuiAnalogeSlider(this.getID(), x + maxWidth - 80, y, 70, 20, 0, ((Float)super.value).floatValue(), super.min, super.max));
      return controls;
   }

   public String createPacketInformation(boolean isServer) {
      if(!isServer && super.guiControls != null && super.guiControls.size() == 2) {
         float valueBefore = ((Float)super.value).floatValue();

         try {
            super.value = Float.valueOf(((GuiAnalogeSlider)super.guiControls.get(1)).value);
         } catch (Exception var4) {
            super.value = Float.valueOf(valueBefore);
         }

         super.value = Float.valueOf(Math.max(super.min, ((Float)super.value).floatValue()));
         super.value = Float.valueOf(Math.min(super.max, ((Float)super.value).floatValue()));
      }

      return ((Float)super.value).toString();
   }
}
