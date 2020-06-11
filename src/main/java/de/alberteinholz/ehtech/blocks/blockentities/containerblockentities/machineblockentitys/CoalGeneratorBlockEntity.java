package de.alberteinholz.ehtech.blocks.blockentities.containerblockentities.machineblockentitys;

import de.alberteinholz.ehtech.blocks.directionalblocks.containerblocks.components.ContainerInventoryComponent;
import de.alberteinholz.ehtech.blocks.directionalblocks.containerblocks.components.InventoryWrapper;
import de.alberteinholz.ehtech.blocks.directionalblocks.containerblocks.machineblocks.components.CoalGeneratorDataProviderComponent;
import de.alberteinholz.ehtech.blocks.directionalblocks.containerblocks.machineblocks.components.MachineCapacitorComponent;
import de.alberteinholz.ehtech.registry.BlockRegistry;
import io.github.cottonmc.component.energy.type.EnergyTypes;
import net.minecraft.block.entity.BlockEntityType;

public class CoalGeneratorBlockEntity extends MachineBlockEntity {
    public CoalGeneratorBlockEntity() {
        this(BlockRegistry.COAL_GENERATOR.blockEntityType);
    }

    public CoalGeneratorBlockEntity(BlockEntityType<?> type) {
        super(type);
        inventory.stacks.put("coal_input", new ContainerInventoryComponent.Slot(ContainerInventoryComponent.Slot.Type.INPUT));
    }

    @Override
    public void tick() {
        CoalGeneratorDataProviderComponent data = (CoalGeneratorDataProviderComponent) this.data;
        boolean isRunning = data.progress.getBarCurrent() > data.progress.getBarMinimum() && isActivated() ? true : false;
        if (!isRunning && isActivated()) {
            world.getRecipeManager().getFirstMatch(BlockRegistry.COAL_GENERATOR.recipeType, new InventoryWrapper(pos), world).ifPresent(r -> this.recipe = r);
            if (recipe != null) {
                isRunning = true;
            }
        }
        if (capacitor.getCurrentEnergy() < capacitor.getMaxEnergy() && isRunning) {
            if (data.progress.getBarCurrent() == data.progress.getBarMinimum()) {
                inventory.getItemStack("coal_input").decrement(recipe.input.items[0].amount);
            }
            data.setProgress(data.progress.getBarCurrent() + recipe.timeModifier * data.getSpeed());
            data.setHeat(data.heat.getBarCurrent() + recipe.generates * data.getSpeed() * data.getEfficiency());
            data.setPowerPerTick((int) (data.getEfficiency() * data.getSpeed() * (data.heat.getBarCurrent() - data.heat.getBarMinimum()) / (data.heat.getBarMaximum() - data.heat.getBarMinimum()) * 3 + 1));
            capacitor.generateEnergy(world, pos, data.getPowerPerTick());
        } else {
            if (data.heat.getBarCurrent() > data.heat.getBarMinimum()) {
                data.setHeat(data.heat.getBarCurrent() - 0.1);
            }
            data.setPowerPerTick(0);
        }
        if (data.progress.getBarCurrent() > data.progress.getBarMaximum()) {
            data.setProgress(data.progress.getBarMinimum());
            recipe = null;
        }
        if (data.heat.getBarCurrent() > data.heat.getBarMaximum()) {
            data.setHeat(data.heat.getBarMaximum());
        } else if (data.heat.getBarCurrent() < data.heat.getBarMinimum()) {
            data.setHeat(data.heat.getBarMinimum());
        }
        super.tick();
    }

    @Override
    protected MachineCapacitorComponent initializeCapacitorComponent() {
        return new MachineCapacitorComponent(EnergyTypes.ULTRA_LOW_VOLTAGE);
    }

    @Override
    protected ContainerInventoryComponent initializeInventoryComponent() {
        return new ContainerInventoryComponent();
    }

    @Override
    protected CoalGeneratorDataProviderComponent initializeDataProviderComponent() {
        return new CoalGeneratorDataProviderComponent();
    }
}