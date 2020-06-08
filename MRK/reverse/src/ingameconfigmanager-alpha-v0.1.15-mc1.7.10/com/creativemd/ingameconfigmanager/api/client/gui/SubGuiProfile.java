package com.creativemd.ingameconfigmanager.api.client.gui;

import com.creativemd.creativecore.common.gui.SubGui;
import com.creativemd.creativecore.common.gui.controls.GuiButton;
import com.creativemd.creativecore.common.gui.controls.GuiComboBox;
import com.creativemd.creativecore.common.gui.controls.GuiTextfield;
import com.creativemd.creativecore.common.gui.event.ControlClickEvent;
import com.creativemd.ingameconfigmanager.api.core.InGameConfigManager;
import com.n247s.api.eventapi.eventsystem.CustomEventSubscribe;
import java.util.ArrayList;
import net.minecraft.client.gui.FontRenderer;

public class SubGuiProfile extends SubGui {

   public SubGuiProfile() {
      super(250, 250);
   }

   public void createControls() {
      ArrayList lines = new ArrayList();

      for(int box = 0; box < InGameConfigManager.profiles.size(); ++box) {
         lines.add(InGameConfigManager.profiles.get(box));
      }

      GuiComboBox var3 = new GuiComboBox("profiles", 5, 5, 100, lines);
      var3.caption = InGameConfigManager.profileName;
      this.controls.add(var3);
      this.controls.add(new GuiButton("Remove", 120, 5, 40, 20));
      this.controls.add(new GuiTextfield("Name", "", 5, 40, 100, 20));
      this.controls.add(new GuiButton("Add", 120, 40, 40, 20));
      this.controls.add(new GuiButton("Cancel", 5, 226, 40, 20));
      this.controls.add(new GuiButton("Save", 205, 226, 40, 20));
   }

   @CustomEventSubscribe
   public void onButtonClicked(ControlClickEvent event) {
      if(event.source instanceof GuiButton) {
         if(event.source.is("Cancel")) {
            InGameConfigManager.openModsGui(this.container.player);
         } else {
            GuiComboBox field;
            if(event.source.is("Save")) {
               field = (GuiComboBox)this.getControl("profiles");
               InGameConfigManager.profileName = field.caption;
               InGameConfigManager.profiles = (ArrayList)field.lines.clone();
               InGameConfigManager.saveProfiles();
               InGameConfigManager.loadConfig();
               InGameConfigManager.openModsGui(this.container.player);
            } else if(event.source.is("remove")) {
               if(InGameConfigManager.profiles.size() > 1) {
                  field = (GuiComboBox)this.getControl("profiles");
                  field.lines.remove(field.caption);
                  field.caption = (String)field.lines.get(0);
               }
            } else if(event.source.is("add")) {
               GuiTextfield field1 = (GuiTextfield)this.getControl("name");
               if(!field1.text.equals("")) {
                  GuiComboBox combobox = (GuiComboBox)this.getControl("profiles");
                  combobox.lines.add(field1.text);
               }
            }
         }
      }

   }

   public void drawOverlay(FontRenderer fontRenderer) {}
}
