package de.dafuqs.starrysky.spheroid.lists;

import de.dafuqs.starrysky.dimension.SpheroidLoader;
import de.dafuqs.starrysky.StarrySkyCommon;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static de.dafuqs.starrysky.dimension.SpheroidLoader.SpheroidDimensionType.OVERWORLD;

public class SpheroidListModernIndustrialization extends SpheroidList {

    private static final String MOD_ID = "modern_industrialization";

    public static boolean shouldGenerate() {
        return FabricLoader.getInstance().isModLoaded(MOD_ID) && StarrySkyCommon.STARRY_SKY_CONFIG.generateModernIndustrializationSpheroids;
    }

    public static void setup(SpheroidLoader spheroidLoader) {
        StarrySkyCommon.LOGGER.info("Loading Modern Industrialization integration...");

        BlockState modern_industrialization_copper_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"copper_ore")).getDefaultState();
        BlockState modern_industrialization_tin_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"tin_ore")).getDefaultState();
        BlockState modern_industrialization_lead_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"lead_ore")).getDefaultState();
        BlockState modern_industrialization_lignite_coal_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"lignite_coal_ore")).getDefaultState();
        BlockState modern_industrialization_nickel_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"nickel_ore")).getDefaultState();
        BlockState modern_industrialization_salt_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"salt_ore")).getDefaultState();
        BlockState modern_industrialization_silver_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"silver_ore")).getDefaultState();
        BlockState modern_industrialization_antimony_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"antimony_ore")).getDefaultState();
        BlockState modern_industrialization_bauxite_ore = Registry.BLOCK.get(new Identifier(MOD_ID,"bauxite_ore")).getDefaultState();

        spheroidLoader.registerDynamicOre(OVERWORLD, "copper", modern_industrialization_copper_ore);
        spheroidLoader.registerDynamicOre(OVERWORLD, "tin", modern_industrialization_tin_ore);
        spheroidLoader.registerDynamicOre(OVERWORLD, "lignite_coal", modern_industrialization_lignite_coal_ore);
        spheroidLoader.registerDynamicOre(OVERWORLD, "nickel", modern_industrialization_nickel_ore);
        spheroidLoader.registerDynamicOre(OVERWORLD, "salt", modern_industrialization_salt_ore);
        spheroidLoader.registerDynamicOre(OVERWORLD, "silver", modern_industrialization_silver_ore);
        spheroidLoader.registerDynamicOre(OVERWORLD, "lead", modern_industrialization_lead_ore);
        spheroidLoader.registerDynamicOre(OVERWORLD, "antimony", modern_industrialization_antimony_ore);
        spheroidLoader.registerDynamicOre(OVERWORLD, "bauxite", modern_industrialization_bauxite_ore);
    }

}
