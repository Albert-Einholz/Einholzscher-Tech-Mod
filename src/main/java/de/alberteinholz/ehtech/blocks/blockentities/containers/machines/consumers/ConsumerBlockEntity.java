package de.alberteinholz.ehtech.blocks.blockentities.containers.machines.consumers;

import de.alberteinholz.ehmooshroom.container.component.data.ConfigDataComponent.ConfigBehavior;
import de.alberteinholz.ehmooshroom.registry.RegistryEntry;
import de.alberteinholz.ehtech.TechMod;
import de.alberteinholz.ehtech.blocks.blockentities.containers.machines.MachineBlockEntity;
import io.github.cottonmc.component.energy.type.EnergyType;
import io.github.cottonmc.component.energy.type.EnergyTypes;
import net.minecraft.util.Identifier;

public abstract class ConsumerBlockEntity extends MachineBlockEntity {
    public ConsumerBlockEntity(String titelTranslationKey, RegistryEntry registryEntry) {
        this(titelTranslationKey, registryEntry, EnergyTypes.ULTRA_LOW_VOLTAGE);
    }

    public ConsumerBlockEntity(String titelTranslationKey, RegistryEntry registryEntry, EnergyType energyType) {
        super(titelTranslationKey, registryEntry, energyType);
        getConfigComp().setConfigAvailability(new Identifier[]{TechMod.HELPER.makeId("capacitor_machine")}, new ConfigBehavior[]{ConfigBehavior.SELF_INPUT, ConfigBehavior.FOREIGN_INPUT}, null, true);
    }
}