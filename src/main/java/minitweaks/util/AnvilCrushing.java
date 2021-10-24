package minitweaks.util;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AnvilCrushing {
    private static final Map<Block, Block> ANVIL_BLOCK_TO_RAW_ORES = new ImmutableMap.Builder<Block, Block>()
    // iron, gold
    .put(Blocks.IRON_BLOCK, Blocks.RAW_IRON_BLOCK)
    .put(Blocks.GOLD_BLOCK, Blocks.RAW_GOLD_BLOCK)
    // (non-waxed) copper blocks
    .put(Blocks.COPPER_BLOCK, Blocks.RAW_COPPER_BLOCK)
    .put(Blocks.EXPOSED_COPPER, Blocks.RAW_COPPER_BLOCK)
    .put(Blocks.WEATHERED_COPPER, Blocks.RAW_COPPER_BLOCK)
    .put(Blocks.OXIDIZED_COPPER, Blocks.RAW_COPPER_BLOCK)
    // create map
    .build();

    public static void tryRawOreCrush(World world, BlockPos pos) {
        // get block below landing position
        Block convertedBlock = ANVIL_BLOCK_TO_RAW_ORES.get(world.getBlockState(pos).getBlock());

        // check if converted
        if(convertedBlock != null) {
            // break block and set to new block
            world.breakBlock(pos, false);
            world.setBlockState(pos, convertedBlock.getDefaultState());
        }
    }
}
