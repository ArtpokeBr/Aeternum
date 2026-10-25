# CraftTweaker-coded mixins

Java-level bug and performance fixes written as CraftTweaker scripts through the ZenUtils mixin loader.

## Writing one

- One file per mod: `scripts/mods/mixins/<modid>.zs`, starting with `#modloaded <modid>` and `#loader mixin`, then `#mixin {targets: "fully.qualified.ClassName"} zenClass MixinName { ... }` blocks using `#mixin Overwrite`/`Redirect`/`Inject`/`ModifyConstant`.
- SRG names in the `target:`/`method:` bytecode descriptors; MCP/friendly names in the function body.
- Side-specific files: `#sideonly client` / `#sideonly server`, named `*_client.zs` / `*_server.zs`. They still must not name client-only classes (see *Server-safe code*).
- **Always decompile the installed jar with `javap`** to confirm field and method names first. A close copy of an E2EE mixin (`chisel.zs`, since reverted) broke because the field it assumed doesn't exist in this build.
- Existing fixes: EnderIO, Forestry, ThermalDynamics, VillageNames perf; Immersive Engineering's Arc Furnace recipe scan, villager trade gen and Engineer's Manual recipe-grid previews; Just Enough Drags' AE2 integration disabled so AE2UEL's own JEI ghost handler is used, and its XNet integration disabled on the dedicated server only (`justenoughdrags.zs`); a TheOneProbe (TheOneSmeagle fork) provider for Rustic's Apiary (`rustic.zs`); the server crash fixes `bedrockores_server.zs` and `nb_server.zs`.
- `logs/cleanmix.log` (`APPLY … -> target`) confirms whether a mixin applied. Grep it, it's ~1 MB.

## ZenScript class pitfalls

- **Interfaces use `extends`**, not `implements`: `zenClass MixinFoo extends SomeInterface { ... }`. The compiler sorts each `extends` type into superclass or interface via `isInterface()`.
- **One target class per `zenClass` block.** Shadowing a field on class A and merging an interface into class B needs two mixins.
- **Don't `#mixin Shadow` a private field and add a getter for another mixin to call.** The tempting case: a Block-level provider (TheOneProbe's `IProbeInfoAccessor` is detected on the `Block`, not the TileEntity) needing the TE's private inventory. It crashes with `VerifyError: Bad type on operand stack`: the script compiles before Sponge Mixin transforms the real class, so ZenUtils can't see the injected getter, falls back to dynamic typing and emits broken bytecode for the later cast. Instead, check with `javap` whether the TileEntity exposes the data through `hasCapability`/`getCapability` (common for `IItemHandler` inventories, keyed by `EnumFacing`). The Rustic Apiary provider does `tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN) as IItemHandler` rather than shadowing `honeyCombItemStackHandler`.
- **`val x as Type = expr;` does not pin the static type** in this ZenUtils build. Cast the expression: `val x = expr as Type;`.
- Native locals from a generic/erased call (e.g. `IItemHandler.getStackInSlot(int)`) can still misresolve **overloaded** calls ("matched method isEmpty, but the argument types do not match"). Use an unambiguous call instead: `stack.getCount() > 0`, not `!stack.isEmpty()`.

## Server-safe code

Vanilla strips `@SideOnly(Side.CLIENT)` methods from the dedicated server, and Cleanroom's class loader (Foundation, `top.outlands.foundation.boot.ActualClassLoader`) **permanently blacklists** a class whose first load fails ("Found X in invalid classes"), hiding the original error. Both crashes below were invisible in singleplayer.

- **Never name a client-only class in a `#loader mixin` script**, even a `#sideonly server` one. Mixin scripts compile very early: resolving `import native.net.minecraft.util.BlockRenderLayer` failed, Foundation blacklisted `BlockRenderLayer`, and Ender IO later crashed in construction (`NoClassDefFoundError` from `BlockDarkSteelAnvil` via `Class.getMethods`). `bedrockores_server.zs` avoids it with an `@Inject` at HEAD of `getActualState` whose signature uses only server-safe types, instead of a typed `WrapOperation`.
- **Mods calling client-only vanilla methods in common code** crash with `NoSuchMethodError` only on servers. Nether Backport's `WorldGenNetherStructures.getGroundFromAbove` calls `Block.func_149751_l` (`isTranslucent`); `nb_server.zs` redirects it to `!state.func_185904_a().func_76228_b()` (what vanilla initialises the `translucent` field to), as a `#sideonly server` static `@Redirect`.
- **Other mods' preInit touching client-only classes:** Just Enough Drags' `XNetReflectionHelper.init` (JED preInit) loads XNet's client-only `GuiController` and throws `NoClassDefFoundError` on a dedicated server. Only JED's `JEIPlugin` and `XNetControllerGhostHandler` use the XNet compat, so it is GUI-only. `justenoughdrags.zs` makes `Enables` report `xnet` as not loaded when `FMLLaunchHandler.side().isServer()` (the same mixin that skips AE2 everywhere). `FMLLaunchHandler` is safe to import in a mixin script; it is not a client-only class.
- **Debugging:** add `-Dfoundation.verbose=true` (optionally `-Dfoundation.target=<class>`) to the server JVM args; `logs/debug.log` then shows `Failed to load class X, caused by …` with the real stack. `-Dfoundation.dump=true` also exists.
- **GroovyScript can't reflect a class with client-only members on the server:** `groovy/sideOnly.json` lists the real client-only members; details in `.claude/skills/aeternum-recipes/references/alloying.md` → *New Mekanism infuse types* and `serverpack/CLAUDE.md` → *Testing a build*.
