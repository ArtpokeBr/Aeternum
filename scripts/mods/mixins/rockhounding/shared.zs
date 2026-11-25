/*
Cache for the Rockhounding tooltip mixin (tooltip_client.zs): recipe -> its output list.
Lives here so the mixin needs no @Unique field on GlobbyEventHandler. Keys are recipe
objects; the map is an IdentityHashMap, synchronized in case HEI
builds tooltips off the client thread. (java.util.concurrent is not
natively accessible from ZenScript.)
*/
#modloaded rockhounding_chemistry
#loader mixin
#sideonly client
#priority 3000

import native.java.util.Map;

zenClass Op {
    static outputs as Map;
}
