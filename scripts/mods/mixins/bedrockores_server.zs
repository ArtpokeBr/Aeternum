#modloaded bedrockores
#loader mixin
#sideonly server

import mixin.CallbackInfoReturnable;
import native.net.minecraft.block.state.IBlockState;
import native.net.minecraft.world.IBlockAccess;
import native.net.minecraft.util.math.BlockPos;
import native.li.cil.bedrockores.util.WorldUtils;
import native.li.cil.bedrockores.common.tileentity.TileEntityBedrockOre;

#mixin {targets: "li.cil.bedrockores.common.block.BlockBedrockOre"}
zenClass MixinBlockBedrockOre {

    /*
    When the block accessor isn't a world Bedrock Ores will try to get the BlockRenderLayer,
    even if not on the client, and crashes on the missing client method.
    https://github.com/jbredwards/Fluidlogged-API/issues/276
    On a dedicated server every branch of getActualState resolves to "ore state from the
    tile entity, else the plain state", so do exactly that up front.
    Don't import/name BlockRenderLayer here: mixin scripts compile before Forge can provide
    that class on a dedicated server, and the failed early lookup makes Cleanroom refuse to
    load it for the rest of the session (crashes Ender IO and anything else extending Block).
    */
    #mixin Inject
    #{
    #   method: "func_176221_a",
    #   at: {value: "HEAD"},
    #   cancellable: true
    #}
    function noRenderLayerOnServer(state as IBlockState, world as IBlockAccess, pos as BlockPos, cir as CallbackInfoReturnable) as void {
        val tile = WorldUtils.getTileEntityThreadsafe(world, pos);
        if (tile instanceof TileEntityBedrockOre) {
            val ore = (tile as TileEntityBedrockOre).getOreBlockState();
            if (!isNull(ore)) {
                cir.setReturnValue(ore);
                return;
            }
        }
        cir.setReturnValue(state);
    }
}
