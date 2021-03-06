package de.dafuqs.starrysky.spheroid.spheroids.unique;

import de.dafuqs.starrysky.Support;
import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.dimension.SpheroidDecorator;
import de.dafuqs.starrysky.spheroid.SpheroidEntitySpawnDefinition;
import de.dafuqs.starrysky.spheroid.spheroids.Spheroid;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTables;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;

import java.util.ArrayList;
import java.util.Random;

public class EndCitySpheroid extends Spheroid {

    private final BlockState AIR = Blocks.AIR.getDefaultState();
    private final BlockState PURPUR_BLOCK = Blocks.PURPUR_BLOCK.getDefaultState();
    private final BlockState PURPUR_PILLAR = Blocks.PURPUR_PILLAR.getDefaultState();
    private final BlockState MAGENTA_STAINED_GLASS = Blocks.MAGENTA_STAINED_GLASS.getDefaultState();
    private final BlockState END_STONE_BRICKS = Blocks.END_STONE_BRICKS.getDefaultState();
    private final BlockState END_ROD = Blocks.END_ROD.getDefaultState(); // TODO
    private final BlockState DRAGON_HEAD = Blocks.DRAGON_HEAD.getDefaultState(); // TODO

    private final Identifier END_CITY_TREASURE_CHEST = LootTables.END_CITY_TREASURE_CHEST;

    private final int shellRadius;
    private final ArrayList<BlockPos> interiorDecoratorPositions = new ArrayList<>();


    public EndCitySpheroid(ChunkRandom random, SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, int radius, ArrayList<SpheroidDecorator> spheroidDecorators, ArrayList<SpheroidEntitySpawnDefinition> entityTypesToSpawn, int shellRadius) {
        super(spheroidAdvancementIdentifier, random, spheroidDecorators, radius, entityTypesToSpawn);
        this.shellRadius = shellRadius;
    }

    @Override
    public String getDescription() {
        return "+++ EndCitySpheroid +++" +
                "\nPosition: x=" + this.getPosition().getX() + " y=" + this.getPosition().getY() + " z=" + this.getPosition().getZ() +
                "\nRadius: " + this.radius +
                "\nShellRadius: " + this.shellRadius;
    }

    @Override
    public void generate(Chunk chunk) {
        int chunkX = chunk.getPos().x;
        int chunkZ = chunk.getPos().z;

        int x = this.getPosition().getX();
        int y = this.getPosition().getY();
        int z = this.getPosition().getZ();

        random.setSeed(chunkX * 341873128712L + chunkZ * 132897987541L);
        for (int x2 = Math.max(chunkX * 16, x - this.radius); x2 <= Math.min(chunkX * 16 + 15, x + this.radius); x2++) {
            for (int y2 = y - this.radius; y2 <= y + this.radius; y2++) {
                for (int z2 = Math.max(chunkZ * 16, z - this.radius); z2 <= Math.min(chunkZ * 16 + 15, z + this.radius); z2++) {

                    BlockPos currBlockPos = new BlockPos(x2, y2, z2);
                    long d = Math.round(Support.getDistance(x, y, z, x2, y2, z2));

                    if (d <= (this.radius - this.shellRadius)) {
                        chunk.setBlockState(currBlockPos, PURPUR_BLOCK, false);
                    } else if (d <= this.radius) {
                        if(y2 % 2 == 0) {
                            chunk.setBlockState(currBlockPos, END_STONE_BRICKS, false);
                        } else {
                            chunk.setBlockState(currBlockPos, PURPUR_BLOCK, false);
                        }
                    }

                    if(d < this.getRadius() - 9 && (y2 % 10 == (this.position.getY() + 9) % 10 && x2 % 10 == (this.position.getX()) % 10 && z2 % 10 == (this.position.getZ()) % 10)) {
                        interiorDecoratorPositions.add(currBlockPos);
                    }
                }
            }
        }
    }

    /**
     * EndCitySpheroid uses the decorator to place all the
     * internal rooms more easily
     * @param world The world
     * @param random The decoration random
     */
    @Override
    public void decorate(StructureWorldAccess world, Random random) {
        for (BlockPos interiorDecoratorPosition : interiorDecoratorPositions) {
            int randomStructure = random.nextInt(7);
            switch (randomStructure) {
                case 0:
                    placeSolid(world, interiorDecoratorPosition);
                    break;
                case 1:
                    placeEmpty(world, interiorDecoratorPosition);
                    break;
                case 2:
                    placeElytra(world, interiorDecoratorPosition);
                    break;
                case 3:
                    placeTreasure(world, interiorDecoratorPosition);
                    break;
                case 4:
                    placeBrewingStand(world, interiorDecoratorPosition);
                    break;
                default: // double chance
                    placeShulkerSpawner(world, interiorDecoratorPosition);
                    break;
            }
        }
    }

    private void placeSolid(WorldAccess worldAccess, BlockPos blockPos) {
        for (int x2 = - 4; x2 < 5; x2++) {
            for (int y2 = 0; y2 < 9; y2++) {
                for (int z2 = -4; z2 < 5; z2++) {
                    BlockPos destinationBlockPos = blockPos.add(x2, y2, z2);
                    worldAccess.setBlockState(destinationBlockPos, PURPUR_BLOCK, 3);
                }
            }
        }
    }

    private void placeEmpty(WorldAccess worldAccess, BlockPos blockPos) {
        for (int x2 = - 4; x2 < 5; x2++) {
            for (int y2 = 0; y2 < 9; y2++) {
                for (int z2 = -4; z2 < 5; z2++) {
                    BlockPos destinationBlockPos = blockPos.add(x2, y2, z2);
                    worldAccess.setBlockState(destinationBlockPos, AIR, 3);
                }
            }
        }
    }

    private void placeShulkerSpawner(WorldAccess worldAccess, BlockPos blockPos) {
        for (int x2 = - 4; x2 < 5; x2++) {
            for (int y2 = 0; y2 < 9; y2++) {
                for (int z2 = -4; z2 < 5; z2++) {
                    BlockPos destinationBlockPos = blockPos.add(x2, y2, z2);
                    worldAccess.setBlockState(destinationBlockPos, AIR, 3);
                }
            }
        }

        BlockPos spawnerPos = blockPos.up(4);
        for (int x2 = -1; x2 < 2; x2++) {
            for (int y2 = -1; y2 < 2; y2++) {
                for (int z2 = -1; z2 < 2; z2++) {
                    BlockPos destinationBlockPos = spawnerPos.add(x2, y2, z2);
                    worldAccess.setBlockState(destinationBlockPos, MAGENTA_STAINED_GLASS, 3);
                }
            }
        }

        worldAccess.setBlockState(spawnerPos.up(2), PURPUR_PILLAR, 3);
        worldAccess.setBlockState(spawnerPos.up(3), PURPUR_PILLAR, 3);
        worldAccess.setBlockState(spawnerPos.up(4), PURPUR_PILLAR, 3);
        placeSpawner(worldAccess, spawnerPos.up(), EntityType.SHULKER);
    }

    private void placeBrewingStand(WorldAccess worldAccess, BlockPos blockPos) {
        for (int x2 = - 4; x2 < 5; x2++) {
            for (int y2 = 0; y2 < 9; y2++) {
                for (int z2 = -4; z2 < 5; z2++) {
                    BlockPos destinationBlockPos = blockPos.add(x2, y2, z2);
                    worldAccess.setBlockState(destinationBlockPos, AIR, 3);
                }
            }
        }

        worldAccess.setBlockState(blockPos, PURPUR_PILLAR, 3);
        worldAccess.setBlockState(blockPos.up(), Blocks.BREWING_STAND.getDefaultState(), 3);

        // TODO: Which slot is it?
        /*BlockEntity blockEntity = worldAccess.getBlockEntity(blockPos.down());

        ItemStack healingPotionStack = new ItemStack(Items.POTION, 1);
        CompoundTag potionTag = new CompoundTag();
        potionTag.putString("Potion", "minecraft:strong_healing");
        healingPotionStack.setTag(potionTag);

        if (blockEntity instanceof BrewingStandBlockEntity) {
            ((BrewingStandBlockEntity) blockEntity).setStack(6, healingPotionStack);
            ((BrewingStandBlockEntity) blockEntity).setStack(7, healingPotionStack);
            ((BrewingStandBlockEntity) blockEntity).setStack(8, healingPotionStack);
        }*/
    }

    private void placeTreasure(WorldAccess worldAccess, BlockPos blockPos) {
        for (int x2 = - 4; x2 < 5; x2++) {
            for (int y2 = 0; y2 < 9; y2++) {
                for (int z2 = -4; z2 < 5; z2++) {
                    BlockPos destinationBlockPos = blockPos.add(x2, y2, z2);
                    worldAccess.setBlockState(destinationBlockPos, AIR, 3);
                }
            }
        }

        BlockState enderChestBlockState = Blocks.ENDER_CHEST.getDefaultState();
        BlockPos randomPos = blockPos.add(random.nextInt(9) - 4, 0, random.nextInt(9) - 4);
        worldAccess.setBlockState(randomPos, enderChestBlockState, 3);

        // may override the ender chest in very rare circumstances
        placeCenterChestWithLootTable(worldAccess.getChunk(blockPos),blockPos, END_CITY_TREASURE_CHEST, random,false);
    }

    private void placeElytra(WorldAccess worldAccess, BlockPos blockPos) {
        for (int x2 = - 4; x2 < 5; x2++) {
            for (int y2 = 0; y2 < 9; y2++) {
                for (int z2 = -4; z2 < 5; z2++) {
                    BlockPos destinationBlockPos = blockPos.add(x2, y2, z2);
                    worldAccess.setBlockState(destinationBlockPos, AIR, 3);
                }
            }
        }

        worldAccess.setBlockState(blockPos, PURPUR_PILLAR, 3);
        worldAccess.setBlockState(blockPos.up(), Blocks.CHEST.getDefaultState(), 3);
        ItemStack elytraItemStack = new ItemStack(Items.ELYTRA, 1);

        BlockEntity blockEntity = worldAccess.getBlockEntity(blockPos.up());
        if(blockEntity instanceof ChestBlockEntity) {
            ChestBlockEntity chestBlockEntity = (ChestBlockEntity) blockEntity;
            chestBlockEntity.setStack(0, elytraItemStack);
        }
    }

}
