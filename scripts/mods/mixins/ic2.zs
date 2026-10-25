#modloaded ic2
#loader mixin

import native.java.text.DateFormat;
import native.java.util.Date;

/*

IC2 writes "; created <date>" into IC2.ini on every save (every boot),
so the config always shows up as modified in git. Write a fixed string instead.

*/
#mixin Mixin {targets: "ic2.core.util.Config"}
zenClass IC2ConfigMixin {
    #mixin Redirect {method: "save(Ljava/io/OutputStream;)V", at: {value: "INVOKE", target: "Ljava/text/DateFormat;format(Ljava/util/Date;)Ljava/lang/String;"}}
    function fixedTimestamp(instance as DateFormat, date as Date) as string {
        return "(timestamp disabled)";
    }
}
