package de.alberteinholz.ehtech.blocks.blockentities.containers.machines.generators;

import de.alberteinholz.ehmooshroom.container.component.data.ConfigDataComponent.ConfigBehavior;
import de.alberteinholz.ehmooshroom.registry.RegistryEntry;
import de.alberteinholz.ehtech.blocks.blockentities.containers.machines.MachineBlockEntity;
import io.github.cottonmc.component.energy.type.EnergyType;
import io.github.cottonmc.component.energy.type.EnergyTypes;
import net.minecraft.util.Identifier;

public abstract class GeneratorBlockEntity extends MachineBlockEntity {
    public GeneratorBlockEntity(RegistryEntry registryEntry) {
        this(registryEntry, EnergyTypes.ULTRA_LOW_VOLTAGE);
    }

    public GeneratorBlockEntity(RegistryEntry registryEntry, EnergyType energyType) {
        super(registryEntry, energyType);
        getConfigComp().setConfigAvailability(new Identifier[] {getMachineCapacitorComp().getId()}, new ConfigBehavior[] {ConfigBehavior.SELF_OUTPUT, ConfigBehavior.FOREIGN_OUTPUT}, null, true);
    }
}