#modloaded cookingforblockheads
#loader mixin

import mixin.CallbackInfoReturnable;
import native.net.minecraft.block.Block;
import native.net.minecraft.block.state.IBlockState;
import native.net.minecraft.world.IBlockAccess;
import native.net.minecraft.util.math.BlockPos;
import native.net.minecraft.util.EnumFacing;

#mixin {targets: "net.minecraft.block.BlockPane"}
zenClass MixinBlockPane {

    /*
    BlockKitchen never overrides getBlockFaceShape, so every Cooking for Blockheads block
    reports SOLID sides and glass panes / iron bars reach out to connect to counters,
    cutting boards, sinks, etc. Stop panes attaching to them here instead of changing the
    face shape, so torches, levers and buttons can still be placed on their sides.
    func_193393_b is attachesTo (SRG-named at runtime); Botania panes reach it via super.
    Matched by registry id, not BlockKitchen: that class has a non-SideOnly method taking the
    client-only ITooltipFlag, and naming it in a mixin script risks a dedicated-server blacklist.
    The Kitchen Floor is the only CfB block outside BlockKitchen and is a real full block.
    */
    #mixin Inject
    #{
    #   method: "func_193393_b",
    #   at: {value: "HEAD"},
    #   cancellable: true
    #}
    function noKitchenConnect(world as IBlockAccess, state as IBlockState, pos as BlockPos, facing as EnumFacing, cir as CallbackInfoReturnable) as void {
        val id = Block.REGISTRY.getNameForObject(state.getBlock()).toString();
        if (id.startsWith("cookingforblockheads:") && id != "cookingforblockheads:kitchen_floor") {
            cir.setReturnValue(false);
        }
    }
}
