
content.createItem("copper_chipset").register()
content.createItem("compressed_electrotine").register()
content.createItem("compressed_glowstone").register()
content.createItem("quicksilver_dust").register()

//IE Metal Press mold for Rockhounding Chemistry coils (recipes in groovy/postInit/mods/rockhound.groovy)
def moldCoil = content.createItem("mold_coil")
moldCoil.setMaxStackSize(1)
moldCoil.register()
