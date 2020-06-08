package com.creativemd.ingameconfigmanager.api.common.container.controls;

import com.creativemd.creativecore.client.avatar.AvatarItemStack;
import com.creativemd.creativecore.common.gui.controls.GuiAvatarLabel;
import com.creativemd.creativecore.common.gui.controls.GuiComboBox;
import com.creativemd.creativecore.common.gui.controls.GuiComboBoxExtension;
import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class GuiInvSelectorExtension extends GuiComboBoxExtension {

   public ArrayList stacks;


   public GuiInvSelectorExtension(String name, EntityPlayer player, GuiComboBox comboBox, int x, int y, int width, int height, ArrayList lines, ArrayList stacks) {
      super(name, player, comboBox, x, y, width, height, lines);
      this.stacks = stacks;
      this.refreshControls();
   }

   public void refreshControls() {
      if(this.stacks != null) {
         this.gui.controls.clear();

         for(int i = 0; i < this.lines.size(); ++i) {
            int color = 14737632;
            if(i == this.selected) {
               color = 16777000;
            }

            GuiAvatarLabel label = new GuiAvatarLabel((String)this.lines.get(i), 3, 1 + i * 22, color, new AvatarItemStack((ItemStack)this.stacks.get(i)));
            label.width = this.width - 20;
            label.height = 22;
            this.addControl(label);
         }
      }

   }
}
