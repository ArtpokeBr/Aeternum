# Log filtering (Stuff A Sock In It)

`config/sasit.cfg` → `BasicFilter` drops any log event whose `"[logger]: message"` text contains one of its lines; stack traces attached to the event go with it. Scope each filter to a mod or namespace (e.g. `Parsing error loading built-in advancement farmersdelight:`) so new mods' errors stay visible.

- The filter is installed at sasit's own `FMLConstructionEvent`, so anything logged earlier can't be filtered: coremod/CleanMix messages and FML's "expecting signature … no signature matching" warnings always show.
- Universal Tweaks' `Mute Advancement Errors` does **nothing** under Cleanroom: UT's `UTForgeHooksMixin` wraps `Logger.error(String, Throwable)`, but Cleanroom's `ForgeHooks.lambda$loadAdvancements$0` calls `error(String, Object, Object)`. Use sasit filters instead.
- The client log looks clean for an unrelated reason (the relauncher's log setup), so hunt noise in the server log.
- Stuff A Sock In It must stay on the server (it is not a sound mod).
