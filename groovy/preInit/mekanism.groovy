import mekanism.api.infuse.InfuseRegistry
import mekanism.api.infuse.InfuseType

//Electrotine Infuse Type =========================================================================

//Registered directly instead of mods.mekanism.infusion.addType(), which calls Minecraft.getMinecraft()
//and would crash the dedicated server. Mekanism's own TextureStitchEvent.Pre handler sets the icon.
InfuseRegistry.registerInfuseType(new InfuseType('ELECTROTINE', resource('projectred:blocks/world/electrotine_block')).setTranslationKey('electrotine'))

//=================================================================================================
