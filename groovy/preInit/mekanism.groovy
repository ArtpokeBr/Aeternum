import mekanism.api.infuse.InfuseRegistry
import mekanism.api.infuse.InfuseType

//Electrotine Infuse Type =========================================================================

//Registered directly instead of mods.mekanism.infusion.addType(), which calls Minecraft.getMinecraft()
//and would crash the dedicated server. Mekanism's own TextureStitchEvent.Pre handler sets the icon
//(groovy/assets/aeternum/textures/blocks/infuse/electrotine.png, Mekanism's Redstone one recoloured).
InfuseRegistry.registerInfuseType(new InfuseType('ELECTROTINE', resource('aeternum:blocks/infuse/electrotine')).setTranslationKey('electrotine'))

//=================================================================================================
