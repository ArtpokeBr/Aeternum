#modloaded nb
#loader mixin
#sideonly server

import native.net.minecraft.block.Block;
import native.net.minecraft.block.state.IBlockState;

/*
Unseen's Nether Backport's structure worldgen calls Block.isTranslucent(IBlockState), which is
client-only (stripped on dedicated servers), so servers crash with NoSuchMethodError as soon as
it tries to place a structure. Vanilla's isTranslucent just returns the `translucent` field the
Block constructor sets to !material.blocksLight(), so compute that instead.
*/
#mixin {targets: "com.unseen.nb.common.world.WorldGenNetherStructures"}
zenClass MixinWorldGenNetherStructures {
    #mixin Static
    #mixin Redirect
    #{
    #    method: "getGroundFromAbove",
    #    at: {
    #        value: "INVOKE",
    #        target: "Lnet/minecraft/block/Block;func_149751_l(Lnet/minecraft/block/state/IBlockState;)Z"
    #    }
    #}
    function isTranslucentOnServer(block as Block, state as IBlockState) as bool {
        return !state.func_185904_a().func_76228_b();
    }
}
