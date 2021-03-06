package de.dafuqs.starrysky;

import de.dafuqs.starrysky.advancements.ProximityAdvancementCheckEvent;
import de.dafuqs.starrysky.commands.StarrySkyCommands;
import de.dafuqs.starrysky.configs.StarrySkyConfig;
import de.dafuqs.starrysky.dimension.DecoratorFeatures;
import de.dafuqs.starrysky.dimension.StarrySkyDimension;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import me.shedaniel.cloth.api.common.events.v1.PlayerChangeWorldCallback;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StarrySkyCommon implements ModInitializer {

    public static final String MOD_ID = "starry_sky";

    public static StarrySkyConfig STARRY_SKY_CONFIG;
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static ServerWorld starryWorld;
    public static ServerWorld starryWorldNether;
    public static ServerWorld starryWorldEnd;

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.

        //Set up config
        LOGGER.info("[StarrySky] Starting up...");
        AutoConfig.register(StarrySkyConfig.class, JanksonConfigSerializer::new);
        STARRY_SKY_CONFIG = AutoConfig.getConfigHolder(StarrySkyConfig.class).getConfig();

        // Register all the stuff
        StarrySkyDimension.setupDimension();
        StarrySkyDimension.setupPortals();
        StarrySkyCommands.initialize();
        DecoratorFeatures.initialize();

        // triggers everytime a world is loaded
        // so for overworld, nether, ... (they all share the same seed)
        ServerWorldEvents.LOAD.register((server, world) -> {
            if(world.getRegistryKey().equals(StarrySkyDimension.STARRY_SKY_WORLD_KEY)) {
                StarrySkyCommon.starryWorld = world;
            } else if(world.getRegistryKey().equals(StarrySkyDimension.STARRY_SKY_NETHER_WORLD_KEY)) {
                StarrySkyCommon.starryWorldNether = world;
            } else if(world.getRegistryKey().equals(StarrySkyDimension.STARRY_SKY_END_WORLD_KEY)) {
                StarrySkyCommon.starryWorldEnd = world;
            }
        });

        ServerTickEvents.END_SERVER_TICK.register(new ProximityAdvancementCheckEvent());

        PlayerChangeWorldCallback.EVENT.register(new PlayerChangeWorldCallback() {
            @Override
            public void onChangeWorld(ServerPlayerEntity serverPlayerEntity, RegistryKey<World> registryKey, RegistryKey<World> registryKey1) {

            }
        });

        LOGGER.info("[StarrySky] Finished loading.");
    }

    public static boolean inStarryWorld(ServerPlayerEntity serverPlayerEntity) {
        return serverPlayerEntity.getEntityWorld().equals(StarrySkyCommon.starryWorld) || serverPlayerEntity.getEntityWorld().equals(starryWorldNether) || serverPlayerEntity.getEntityWorld().equals(starryWorldEnd);
    }

}
