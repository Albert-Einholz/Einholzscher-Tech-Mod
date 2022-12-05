package de.einholz.ehtech;

import de.einholz.ehmooshroom.util.Helper;
import de.einholz.ehmooshroom.util.LoggerHelper;
import de.einholz.ehtech.registry.Registry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;

public class TechMod implements ModInitializer, ClientModInitializer {
	public static final Helper HELPER = new Helper("ehtech");
	public static final LoggerHelper LOGGER = new LoggerHelper(HELPER.MOD_ID, "https://github.com/Albert-Einholz/Einholzscher-Tech-Mod/issues");

	@Override
	public void onInitialize() {
		Registry.register();
		//TODO make better
		//Component registration
		/*
		SideConfigComponent.SIDE_CONFIG_LOOKUP.registerForBlockEntities((be, v) -> {
			return null;
		}, RegistryHelper.getEntry(TechMod.HELPER.makeId("coal_generator")).blockEntityType, RegistryHelper.getEntry(TechMod.HELPER.makeId("ore_grower")).blockEntityType);
		EnergyComponent.ENERGY_LOOKUP.registerForBlockEntities((be, dir) -> {
			return null;
		}, RegistryHelper.getEntry(TechMod.HELPER.makeId("coal_generator")).blockEntityType, RegistryHelper.getEntry(TechMod.HELPER.makeId("ore_grower")).blockEntityType);
		HeatComponent.HEAT_LOOKUP.registerForBlockEntities((be, v) -> {
			return null;
		}, RegistryHelper.getEntry(TechMod.HELPER.makeId("coal_generator")).blockEntityType);
		ItemComponent.ITEM_INTERNAL_LOOKUP.registerForBlockEntities((be, dir) -> {
			return null;
		}, RegistryHelper.getEntry(TechMod.HELPER.makeId("coal_generator")).blockEntityType, RegistryHelper.getEntry(TechMod.HELPER.makeId("ore_grower")).blockEntityType);
		ItemComponent.ITEM_INPUT_LOOKUP.registerForBlockEntities((be, dir) -> {
			return null;
		}, RegistryHelper.getEntry(TechMod.HELPER.makeId("coal_generator")).blockEntityType, RegistryHelper.getEntry(TechMod.HELPER.makeId("ore_grower")).blockEntityType);
		*/
		//Api expose
        /*
		BlockComponents.exposeApi(SideConfigComponent.SIDE_CONFIG, SideConfigComponent.SIDE_CONFIG_LOOKUP, (comp, v) -> {
			return comp;
		}, RegistryHelper.getEntry(TechMod.HELPER.makeId("coal_generator")).blockEntityType, RegistryHelper.getEntry(TechMod.HELPER.makeId("ore_grower")).blockEntityType);
		BlockComponents.exposeApi(EnergyComponent.ENERGY, EnergyComponent.ENERGY_LOOKUP, (comp, dir) -> {
			return comp;
		}, RegistryHelper.getEntry(TechMod.HELPER.makeId("coal_generator")).blockEntityType, RegistryHelper.getEntry(TechMod.HELPER.makeId("ore_grower")).blockEntityType);
		BlockComponents.exposeApi(HeatComponent.HEAT, HeatComponent.HEAT_LOOKUP, (comp, v) -> {
			return comp;
		}, RegistryHelper.getEntry(TechMod.HELPER.makeId("coal_generator")).blockEntityType);
		BlockComponents.exposeApi(ItemComponent.ITEM_INTERNAL, ItemComponent.ITEM_INTERNAL_LOOKUP, (comp, type) -> {
			return comp;
		}, RegistryHelper.getEntry(TechMod.HELPER.makeId("coal_generator")).blockEntityType, RegistryHelper.getEntry(TechMod.HELPER.makeId("ore_grower")).blockEntityType);
		BlockComponents.exposeApi(ItemComponent.ITEM_INPUT, ItemComponent.ITEM_INPUT_LOOKUP, (comp, type) -> {
			return comp;
		}, RegistryHelper.getEntry(TechMod.HELPER.makeId("coal_generator")).blockEntityType, RegistryHelper.getEntry(TechMod.HELPER.makeId("ore_grower")).blockEntityType);
		BlockComponents.exposeApi(MachineComponent.MACHINE, MachineComponent.MACHINE_LOOKUP, (comp, v) -> {
			return comp;
		}, RegistryHelper.getEntry(TechMod.HELPER.makeId("coal_generator")).blockEntityType, RegistryHelper.getEntry(TechMod.HELPER.makeId("ore_grower")).blockEntityType);
        */
    }

	@Environment(EnvType.CLIENT)
	@Override
	public void onInitializeClient() {}
}
