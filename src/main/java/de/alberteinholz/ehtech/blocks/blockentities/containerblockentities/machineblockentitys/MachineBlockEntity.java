package de.alberteinholz.ehtech.blocks.blockentities.containerblockentities.machineblockentitys;

import de.alberteinholz.ehtech.TechMod;
import de.alberteinholz.ehtech.blocks.blockentities.containerblockentities.ContainerBlockEntity;
import de.alberteinholz.ehtech.blocks.directionalblocks.containerblocks.components.ContainerInventoryComponent;
import de.alberteinholz.ehtech.blocks.directionalblocks.containerblocks.machineblocks.components.MachineCapacitorComponent;
import de.alberteinholz.ehtech.blocks.directionalblocks.containerblocks.machineblocks.components.MachineDataProviderComponent;
import de.alberteinholz.ehtech.blocks.recipes.Input;
import de.alberteinholz.ehtech.blocks.recipes.MachineRecipe;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Tickable;

public abstract class MachineBlockEntity extends ContainerBlockEntity implements Tickable {
    protected MachineRecipe recipe;
    public MachineCapacitorComponent capacitor = initializeCapacitorComponent();

    public MachineBlockEntity(BlockEntityType<?> type) {
        super(type);
        inventory.stacks.put("power_input", new ContainerInventoryComponent.Slot(ContainerInventoryComponent.Slot.Type.OTHER));
        inventory.stacks.put("power_output", new ContainerInventoryComponent.Slot(ContainerInventoryComponent.Slot.Type.OTHER));
        inventory.stacks.put("upgrade", new ContainerInventoryComponent.Slot(ContainerInventoryComponent.Slot.Type.OTHER));
        inventory.stacks.put("network", new ContainerInventoryComponent.Slot(ContainerInventoryComponent.Slot.Type.OTHER));
    }

    @Override
    public void tick() {
        //only for testing TODO: remove
        if (inventory.getItemStack("power_input").getItem() == Items.BEDROCK && capacitor.getCurrentEnergy() < capacitor.getMaxEnergy()) {
            capacitor.generateEnergy(world, pos, 4);
        }
        //end
        markDirty();
    }

    public boolean containsItemIngredients(Input.ItemIngredient... ingredients) {
        boolean bl = true;
        for (Input.ItemIngredient ingredient : ingredients) {
            if (!inventory.containsInput(ingredient)) {
                bl = false;
            }
        }
        return bl;
    }

    public boolean containsFluidIngredients(Input.FluidIngredient... ingredients) {
        boolean bl = true;
        for (Input.FluidIngredient ingredient : ingredients) {
            TechMod.LOGGER.wip("Containment Check for " + ingredient);
            //TODO
        }
        return bl;
    }

    //only by overriding
    public boolean containsBlockIngredients(Input.BlockIngredient... ingredients) {
        return true;
    }

    //only by overriding
    public boolean containsEntityIngredients(Input.EntityIngredient... ingredients) {
        return true;
    }

    //only by overriding
    public boolean containsDataIngredients(Input.DataIngredient... ingredients) {
        return true;
    }

    public boolean isActivated() {
        MachineDataProviderComponent.ActivationState activationState = ((MachineDataProviderComponent) data).getActivationState();
        if (activationState == MachineDataProviderComponent.ActivationState.ALWAYS_ON) {
            return true;
        } else if(activationState == MachineDataProviderComponent.ActivationState.REDSTONE_ON) {
            return world.isReceivingRedstonePower(pos);
        } else if(activationState == MachineDataProviderComponent.ActivationState.REDSTONE_OFF) {
            return !world.isReceivingRedstonePower(pos);
        } else {
            return false;
        }
    }

    @Override
    public void fromTag(CompoundTag tag) {
        super.fromTag(tag);
        if (world != null) {
            capacitor.fromTag(tag);
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        if (world != null) {
            capacitor.toTag(tag);
        }
        return tag;
    }
    
    protected abstract MachineCapacitorComponent initializeCapacitorComponent();
}