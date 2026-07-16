package minitweaks.util;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Map;

public class AnvilCrushing {
    private static final Map<Block, Block> ANVIL_BLOCK_TO_RAW_ORES = new ImmutableMap.Builder<Block, Block>()
    // iron, gold
    .put(Blocks.IRON_BLOCK, Blocks.RAW_IRON_BLOCK)
    .put(Blocks.GOLD_BLOCK, Blocks.RAW_GOLD_BLOCK)
    // (non-waxed) copper blocks
    .put(Blocks.COPPER_BLOCK.weathering().unaffected(), Blocks.RAW_COPPER_BLOCK)
    .put(Blocks.COPPER_BLOCK.weathering().exposed(), Blocks.RAW_COPPER_BLOCK)
    .put(Blocks.COPPER_BLOCK.weathering().weathered(), Blocks.RAW_COPPER_BLOCK)
    .put(Blocks.COPPER_BLOCK.weathering().oxidized(), Blocks.RAW_COPPER_BLOCK)
    // create map
    .build();

    public static void tryRawOreCrush(Level level, BlockPos pos) {
        // get block below landing position
        Block convertedBlock = ANVIL_BLOCK_TO_RAW_ORES.get(level.getBlockState(pos).getBlock());

        // check if converted
        if(convertedBlock != null) {
            // break block and set to new block
            level.destroyBlock(pos, false);
            level.setBlockAndUpdate(pos, convertedBlock.defaultBlockState());
        }
    }
}
