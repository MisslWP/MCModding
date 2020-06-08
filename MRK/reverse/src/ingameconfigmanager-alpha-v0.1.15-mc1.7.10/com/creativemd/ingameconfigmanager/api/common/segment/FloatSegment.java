package com.creativemd.ingameconfigmanager.api.common.segment;

import com.creativemd.creativecore.common.container.SubContainer;
import com.creativemd.creativecore.common.gui.SubGui;
import com.creativemd.creativecore.common.gui.controls.GuiTextfield;
import com.creativemd.ingameconfigmanager.api.common.segment.TitleSegment;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;

public class FloatSegment extends TitleSegment {

   public float min;
   public float max;


   public FloatSegment(String id, String title, Float defaultValue) {
      this(id, title, defaultValue, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY);
   }

   public FloatSegment(String id, String title, Float defaultValue, float min, float max) {
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
      controls.add((new GuiTextfield(this.getID(), "" + super.value, x + maxWidth - 50, y, 40, 20)).setFloatOnly());
      return controls;
   }

   public String createPacketInformation(boolean isServer) {
      if(!isServer && super.guiControls != null && super.guiControls.size() == 2) {
         float valueBefore = ((Float)super.value).floatValue();

         try {
            super.value = Float.valueOf(Float.parseFloat(((GuiTextfield)super.guiControls.get(1)).text));
         } catch (Exception var4) {
            super.value = Float.valueOf(valueBefore);
         }

         super.value = Float.valueOf(Math.max(this.min, ((Float)super.value).floatValue()));
         super.value = Float.valueOf(Math.min(this.max, ((Float)super.value).floatValue()));
      }

      return ((Float)super.value).toString();
   }

   public void receivePacketInformation(String input) {
      super.value = Float.valueOf(Float.parseFloat(input));
   }

   public boolean contains(String search) {
      return super.contains(search) || ((Float)super.value).toString().contains(search);
   }
}
