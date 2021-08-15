package de.alberteinholz.ehtech.blocks.blockentities.containers.machines.consumers;

import de.alberteinholz.ehmooshroom.container.component.data.ConfigDataComponent.ConfigBehavior;
import de.alberteinholz.ehmooshroom.container.component.item.AdvancedInventoryComponent;
import de.alberteinholz.ehmooshroom.container.component.item.AdvancedInventoryComponent.Slot.Type;
import de.alberteinholz.ehmooshroom.recipes.AdvancedRecipe;
import de.alberteinholz.ehmooshroom.recipes.Input.BlockIngredient;
import de.alberteinholz.ehmooshroom.registry.RegistryEntry;
import de.alberteinholz.ehmooshroom.registry.RegistryHelper;
import de.alberteinholz.ehtech.TechMod;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class OreGrowerBlockEntity extends ConsumerBlockEntity {
    public OreGrowerBlockEntity() {
        this(RegistryHelper.getEntry(TechMod.HELPER.makeId("ore_grower")));
    }

    public OreGrowerBlockEntity(RegistryEntry registryEntry) {
        super(registryEntry);
        addComponent(TechMod.HELPER.makeId("ore_grower_input_inv_1"), new AdvancedInventoryComponent(new Type[] {Type.INPUT}, TechMod.HELPER.MOD_ID, new String[] {"input_seed"}));
        getConfigComp().setConfigAvailability(new Identifier[] {getFirstInputInvComp().getId()}, new ConfigBehavior[] {ConfigBehavior.SELF_INPUT, ConfigBehavior.FOREIGN_INPUT}, null, true);
    }

    public AdvancedInventoryComponent getFirstInputInvComp() {
        return (AdvancedInventoryComponent) getImmutableComps().get(TechMod.HELPER.makeId("ore_grower_input_inv_1"));
    }

    @Override
    public boolean process() {
        if (!containsBlockIngredients(((AdvancedRecipe) getMachineDataComp().getRecipe(world)).input.blocks)) {
            cancel();
            return false;
        } else return super.process();
    }

    @Override
    public void task() {
        super.task();
        AdvancedRecipe recipe = (AdvancedRecipe) getMachineDataComp().getRecipe(world);
        BlockPos target = pos.offset(world.getBlockState(pos).get(Properties.FACING));
        //TODO: Make particle amount configurable?
        for (int i = 0; i < 4; i++) {
            int side = world.random.nextInt(5);
            double x = side == 0 ? 0 : side == 1 ? 1 : world.random.nextDouble();
            double y = side == 2 ? 0 : side == 3 ? 1 : world.random.nextDouble();
            double z = side == 4 ? 0 : side == 5 ? 1 : world.random.nextDouble();
            world.addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK, recipe.output.blocks[0]), target.getX() + x, target.getY() + y, target.getZ() + z, 0.1, 0.1, 0.1);
        }
    }

    @Override
    public void complete() {
        world.setBlockState(pos.offset(world.getBlockState(pos).get(Properties.FACING)), ((AdvancedRecipe) getMachineDataComp().getRecipe(world)).output.blocks[0]);
        super.complete();
    }

    @Override
    public boolean containsBlockIngredients(BlockIngredient... ingredients) {
        return ingredients[0].ingredient.contains(world.getBlockState(pos.offset(world.getBlockState(pos).get(Properties.FACING))).getBlock());
    }
}