#modloaded reim
#loader mixin
#sideonly client

import native.net.minecraftforge.fml.common.Loader;

/*
Rather Enough Immersive Multiblocks (REIM) 0.4 was built against an older Immersive Technology.
Its Immersive Technology list names classes this pack's MCT Immersive Technology no longer has
(TileEntityITMultiblockPartBoiler, the metal-package CoolingTower, GenericMultiblockIngredient), and
the ones it still has are Immersive Convergence ICMultiblocks, not the IE IMultiblock REIM renders.
The first missing class threw NoClassDefFoundError in JEIPlugin.register, so HEI dropped the whole
plugin and the IE and Immersive Petroleum structure pages vanished too.

Make REIM see Immersive Technology as absent. Every Immersive Technology reference sits behind the
immersiveTech flag, so the classes are never resolved. Immersive Convergence already shows the
Immersive Technology multiblocks and their catalysts in JEI.
*/
#mixin {targets: "BananaFructa.reim.JEIPlugin"}
zenClass MixinREIMJEIPlugin {
    #mixin Redirect
    #{
    #    method: "<init>",
    #    at: {
    #        value: "INVOKE",
    #        target: "Lnet/minecraftforge/fml/common/Loader;isModLoaded(Ljava/lang/String;)Z"
    #    }
    #}
    function skipImmersiveTech(modid as string) as bool {
        if (modid == "immersivetech") {
            return false;
        }
        return Loader.isModLoaded(modid);
    }
}
