import mekanism.api.infuse.InfuseRegistry
import mekanism.api.infuse.InfuseType

//Electrotine & Glowstone Infuse Types ============================================================

//Registered directly instead of mods.mekanism.infusion.addType(), which calls Minecraft.getMinecraft()
//and would crash the dedicated server. Mekanism's own TextureStitchEvent.Pre handler sets the icons
//(groovy/assets/aeternum/textures/blocks/infuse/, Mekanism's Redstone one recoloured).
//groovy/sideOnly.json strips InfuseType's client-only sprite/setIcon on the server so Groovy can reflect it there.
InfuseRegistry.registerInfuseType(new InfuseType('ELECTROTINE', resource('aeternum:blocks/infuse/electrotine')).setTranslationKey('electrotine'))
InfuseRegistry.registerInfuseType(new InfuseType('GLOWSTONE', resource('aeternum:blocks/infuse/glowstone')).setTranslationKey('glowstone'))

//=================================================================================================
