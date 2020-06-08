package com.creativemd.ingameconfigmanager.api.common.segment;

import com.creativemd.creativecore.common.container.SubContainer;
import com.creativemd.creativecore.common.gui.SubGui;
import com.creativemd.creativecore.common.gui.controls.GuiComboBox;
import com.creativemd.ingameconfigmanager.api.common.segment.TitleSegment;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.commons.lang3.ArrayUtils;

public class SelectSegment extends TitleSegment {

   public String[] options;


   public SelectSegment(String id, String title, String defaultValue, String ... options) {
      super(id, title, defaultValue);
      this.options = options;
   }

   public SelectSegment(String id, String Title, int index, String ... options) {
      this(id, Title, options[index], options);
      this.options = options;
   }

   public int getIndex() {
      return ArrayUtils.indexOf(this.options, super.value);
   }

   @SideOnly(Side.CLIENT)
   public ArrayList createGuiControls(SubGui gui, int x, int y, int maxWidth) {
      ArrayList controls = super.createGuiControls(gui, x, y, maxWidth);
      GuiComboBox box = new GuiComboBox(super.title, x + maxWidth - 90, y, 80, new ArrayList(Arrays.asList(this.options)));
      box.caption = (String)super.value;
      controls.add(box);
      return controls;
   }

   public ArrayList createContainerControls(SubContainer gui, int x, int y, int maxWidth) {
      return new ArrayList();
   }

   public String createPacketInformation(boolean isServer) {
      if(!isServer && super.guiControls != null && super.guiControls.size() == 2) {
         super.value = ((GuiComboBox)super.guiControls.get(1)).caption;
      }

      return (String)super.value;
   }

   public void receivePacketInformation(String input) {
      super.value = input;
   }
}
