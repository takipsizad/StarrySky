package de.dafuqs.starrysky.spheroidtypes;

import de.dafuqs.starrysky.StarrySkyCommon;
import de.dafuqs.starrysky.advancements.SpheroidAdvancementIdentifier;
import de.dafuqs.starrysky.spheroids.StripesSpheroid;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.ChunkRandom;

import java.util.ArrayList;

public class StripesSpheroidType extends SpheroidType {

    private final ArrayList<BlockState> stripesBlockStates;

    public StripesSpheroidType(SpheroidAdvancementIdentifier spheroidAdvancementIdentifier, ArrayList<BlockState> stripesBlockStates, int minSize, int maxSize) {
        if(stripesBlockStates == null || stripesBlockStates.size() == 0) {
            StarrySkyCommon.LOGGER.error("ShellSpheroidType: Registered a SpheroidType with empty stripesBlockStates!");
        }

        this.spheroidAdvancementIdentifier = spheroidAdvancementIdentifier;
        this.minRadius = minSize;
        this.maxRadius = maxSize;
        this.stripesBlockStates = stripesBlockStates;
    }

    public ArrayList<BlockState> getStripesBlockStates() {
        return stripesBlockStates;
    }

    @Override
    public String getDescription() {
        return "StripesSpheroid";
    }

    public StripesSpheroid getRandomSphere(ChunkRandom chunkRandom) {
        return new StripesSpheroid(this, chunkRandom);
    }

}
