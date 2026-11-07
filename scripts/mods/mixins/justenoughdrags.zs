#modloaded justenoughdrags
#loader mixin

import native.net.minecraftforge.fml.common.Loader;
import native.net.minecraftforge.fml.relauncher.FMLLaunchHandler;

/*
Disable Just Enough Drags' AE2 integration. AE2 Unofficial Extended Life registers its own JEI ghost
handler on AEBaseGui, but JED registers on the exact GUI classes (storage bus, interface, IO buses,
pattern terminal, fluid GUIs...), and HEI picks an exact-class match first, so JED's older handlers
shadowed AE2UEL's. Only AE2 is skipped: the other mods JED covers have no ghost handler of their own.

XNet is also skipped on the dedicated server: XNetReflectionHelper.init (JED preInit) loads XNet's
client-only GuiController and crashes the server. The XNet integration is GUI-only, so the client keeps it.
*/
#mixin {targets: "com.warmthdawn.justenoughdrags.compact.Enables"}
zenClass MixinEnables {
    #mixin Static
    #mixin Redirect
    #{
    #    method: "init",
    #    at: {
    #        value: "INVOKE",
    #        target: "Lnet/minecraftforge/fml/common/Loader;isModLoaded(Ljava/lang/String;)Z"
    #    }
    #}
    function skipAE2(modid as string) as bool {
        if (modid == "appliedenergistics2") {
            return false;
        }
        if (modid == "xnet" && FMLLaunchHandler.side().isServer()) {
            return false;
        }
        return Loader.isModLoaded(modid);
    }
}
