package de.alberteinholz.ehtech.blocks.guis.guis.machines;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import de.alberteinholz.ehmooshroom.container.component.data.ConfigDataComponent.ConfigBehavior;
import de.alberteinholz.ehmooshroom.container.component.energy.AdvancedCapacitorComponent;
import de.alberteinholz.ehmooshroom.container.component.item.AdvancedInventoryComponent;
import de.alberteinholz.ehmooshroom.registry.RegistryHelper;
import de.alberteinholz.ehtech.TechMod;
import de.alberteinholz.ehtech.blocks.blockentities.containers.machines.MachineBlockEntity;
import de.alberteinholz.ehtech.blocks.components.machine.MachineDataComponent;
import de.alberteinholz.ehtech.blocks.guis.guis.ContainerGui;
import de.alberteinholz.ehtech.blocks.guis.widgets.Button;
import io.github.cottonmc.component.UniversalComponents;
import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WListPanel;
import nerdhub.cardinal.components.api.component.BlockComponentProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

public class MachineConfigGui extends ContainerGui {
    protected WLabel down = new WLabel(new TranslatableText("block.ehtech.machine_config.down"));
    protected WLabel up = new WLabel(new TranslatableText("block.ehtech.machine_config.up"));
    protected WLabel north = new WLabel(new TranslatableText("block.ehtech.machine_config.north"));
    protected WLabel south = new WLabel(new TranslatableText("block.ehtech.machine_config.south"));
    protected WLabel west = new WLabel(new TranslatableText("block.ehtech.machine_config.west"));
    protected WLabel east = new WLabel(new TranslatableText("block.ehtech.machine_config.east"));
    protected WListPanel<Identifier, ConfigEntry> configPanel;
    protected List<Identifier> configIds = getConfigComp().getIds();
    protected BiConsumer<Identifier, ConfigEntry> configBuilder = (id, entry) -> entry.build(id);
    /*FIXME: check this class for content that can be removed
    protected WLabel item;
    protected WLabel fluid;
    protected WLabel power;
    */
    protected Map<Integer, ConfigButton> configButtons = new HashMap<Integer, ConfigButton>();
    protected Button cancel;

    @SuppressWarnings("unchecked")
    public MachineConfigGui(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        this((ScreenHandlerType<SyncedGuiDescription>) RegistryHelper.getEntry(TechMod.HELPER.makeId("machine_config")).screenHandlerType, syncId, playerInventory, buf);
    }

    public MachineConfigGui(ScreenHandlerType<SyncedGuiDescription> type, int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        super(type, syncId, playerInventory, buf);
    }

    @Override
    protected void initWidgets() {
        super.initWidgets();
        configPanel = new WListPanel<>(configIds, ConfigEntry::new, configBuilder);
        /*
        item = new WLabel(new TranslatableText("block.ehtech.machine_config.item"));
        fluid = new WLabel(new TranslatableText("block.ehtech.machine_config.fluid"));
        power = new WLabel(new TranslatableText("block.ehtech.machine_config.power"));
        for (Identifier id : ConfigType.values()) for (Direction dir : Direction.values()) for (ConfigBehavior behavior : ConfigBehavior.values()) {
            ConfigButton button = new ConfigButton(id, dir, behavior);
            buttonIds.add(button);
            button.id = buttonIds.indexOf(button);
            configButtons.put(button.id, button);
            if (getConfigComp().getConfig(id, behavior, dir) == null) button.setEnabled(false);
            else button.setOnClick(getDefaultOnButtonClick(button));
        }
        */
        cancel = (Button) new Button().setLabel(new LiteralText("X"));
        cancel.tooltips.add("tooltip.ehtech.cancel_button");
        buttonIds.add(cancel);
        cancel.setOnClick(getDefaultOnButtonClick(cancel));
    }

    @Override
    protected void drawDefault() {
        ((WGridPanel) root).add(createPlayerInventoryPanel(), 0, 7);
        ((WGridPanel) root).add(down, 2, 1, 1, 1);
        ((WGridPanel) root).add(up, 3, 1, 1, 1);
        ((WGridPanel) root).add(north, 4, 1, 1, 1);
        ((WGridPanel) root).add(south, 5, 1, 1, 1);
        ((WGridPanel) root).add(west, 6, 1, 1, 1);
        ((WGridPanel) root).add(east, 7, 1, 1, 1);
        /*
        ((WGridPanel) root).add(item, 0, 4, 4, 2);
        ((WGridPanel) root).add(fluid, 0, 6, 4, 2);
        ((WGridPanel) root).add(power, 0, 8, 4, 2);
        configButtons.forEach((id, button) -> {
            ((WGridPanel) root).add(button, button.dir.ordinal() * 2 + 4 + (int) Math.floor((double) button.behavoir.ordinal() / 2.0), button.TYPE.ordinal() * 2 + 4 + (button.behavoir.ordinal() + 1) % 2);
        });
        */
        ((WGridPanel) root).add(cancel, 9, 5, 1, 1);
    }

    @Override
    public boolean onButtonClick(PlayerEntity player, int id) {
        if (configButtons.containsKey(id)) {
            ConfigButton button = configButtons.get(id);
            if (button.isEnabled()) {
                getConfigComp().changeConfig(button.id, button.behavior, button.dir);
                return true;
            }
        } else if (id == buttonIds.indexOf(cancel)) {
            if (!world.isClient) player.openHandledScreen((MachineBlockEntity) world.getBlockEntity(pos));
            return true;
        }
        return false;
    }
    
    protected MachineDataComponent getMachineDataComp() {
        return (MachineDataComponent) getDataComp().getComp(TechMod.HELPER.makeId("data_machine"));
    }

    protected AdvancedCapacitorComponent getCapacitorComp() {
        return (AdvancedCapacitorComponent) BlockComponentProvider.get(world.getBlockState(pos)).getComponent(world, pos, UniversalComponents.CAPACITOR_COMPONENT, null);
    }

    protected AdvancedInventoryComponent getMachineInvComp() {
        return (AdvancedInventoryComponent) getInvComp().getComp(TechMod.HELPER.makeId("inventory_machine"));
    }

    protected class ConfigEntry extends WGridPanel {
        public Identifier id;
        //is this needed?
        public List<ConfigButton> buttons = new ArrayList<>();

        public ConfigEntry() {
            grid = 9;
        }

        public void build(Identifier id) {
            this.id = id;
            add(new WLabel(new TranslatableText("block." + id.getNamespace() + ".machine_config." + id.getPath())), 0, 0, 4, 2);
            for (Direction dir : Direction.values()) for (ConfigBehavior behavior : ConfigBehavior.values()) {
                ConfigButton button = new ConfigButton(id, dir, behavior);
                buttonIds.add(button);
                //FIXME: delete: button.id = buttonIds.indexOf(button);
                configButtons.put(buttonIds.indexOf(button), button);
                if (getConfigComp().isAvailable(id, behavior, dir)) button.setEnabled(false);
                else button.setOnClick(getDefaultOnButtonClick(button));
                add(button, button.dir.ordinal() * 2 + 4 + (int) Math.floor((double) button.behavior.ordinal() / 2.0), (button.behavior.ordinal() + 1) % 2);
            }
            
        }
    }

    protected class ConfigButton extends Button {
        //TODO: s this needed?
        public final Identifier id;
        public final Direction dir;
        public final ConfigBehavior behavior;

        @SuppressWarnings("unchecked")
        public ConfigButton(Identifier id, Direction dir, ConfigBehavior behavior) {
            this.id = id;
            this.dir = dir;
            this.behavior = behavior;
            setSize(8, 8);
            resizeability = false;
            if (!isEnabled()) return;
            Supplier<?>[] suppliers = {
                () -> {
                    return behavior.name().toLowerCase();
                }, () -> {
                    return dir.getName();
                }, () -> {
                    return String.valueOf(getConfigComp().allowsConfig(id, behavior, dir));
                }, () -> {
                    return id.toString();
                }
            };
            advancedTooltips.put("tooltip.ehtech.config_button", (Supplier<Object>[]) suppliers);
        }

        @Override
        public void draw(MatrixStack matrices, int x, int y, int mouseX, int mouseY) {
            if (isEnabled()) withTint(getConfigComp().allowsConfig(id, behavior, dir) ? 0xFFFFFF00 : 0xFFFF0000);
            else advancedTooltips.remove("tooltip.ehtech.config_button");
            super.draw(matrices, x, y, mouseX, mouseY);
        }
    }
}