package de.einholz.ehtech.gui.gui;

import de.einholz.ehmooshroom.storage.AdvInv;
import de.einholz.ehmooshroom.storage.storages.SingleBlockStorage;
import de.einholz.ehtech.TechMod;
import de.einholz.ehtech.block.entity.OreGrowerBE;
import de.einholz.ehtech.registry.ScreenHandlerReg;
import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandlerType;

public class OreGrowerGui extends MachineGui {
    protected WItemSlot oreInSlot;

    protected OreGrowerGui(ScreenHandlerType<? extends SyncedGuiDescription> type, int syncId, PlayerInventory playerInv, PacketByteBuf buf) {
        super(type, syncId, playerInv, buf);
    }

    public static OreGrowerGui init(int syncId, PlayerInventory playerInv, PacketByteBuf buf) {
        return init(new OreGrowerGui(ScreenHandlerReg.ORE_GROWER, syncId, playerInv, buf));
    }

    public static OreGrowerGui init(OreGrowerGui gui) {
        gui.progressBarBG = TechMod.HELPER.makeId("textures/gui/container/machine/oregrower/elements/progress_bar_bg.png");
        gui.progressBarFG = TechMod.HELPER.makeId("textures/gui/container/machine/oregrower/elements/progress_bar_fg.png");
        gui.oreInSlot = WItemSlot.of(gui.getOreGrowerInv(), ((AdvInv) gui.getOreGrowerInv()).getSlotIndex(OreGrowerBE.ORE_IN));
        return (OreGrowerGui) MachineGui.init(gui);
    }

    protected Inventory getOreGrowerInv() {
        return ((OreGrowerBE) getBE()).getOreGrowerInv();
    }

    protected SingleBlockStorage getOreGrowerBlock() {
        return ((OreGrowerBE) getBE()).getOreGrowerBlock();
    }

    @Override
    public void drawDefault() {
        super.drawDefault();
        ((WGridPanel) rootPanel).add(oreInSlot, 2, 3);
        ((WGridPanel) rootPanel).add(progressBar, 3, 3, 2, 1);
    }
}
