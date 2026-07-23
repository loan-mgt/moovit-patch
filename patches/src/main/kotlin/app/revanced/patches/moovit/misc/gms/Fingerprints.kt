package app.revanced.patches.moovit.misc.gms

import app.revanced.patcher.gettingFirstMethodDeclaratively
import app.revanced.patcher.name
import app.revanced.patcher.parameterTypes
import app.revanced.patcher.patch.BytecodePatchContext
import app.revanced.patcher.returnType

val BytecodePatchContext.moovitActivityOnReadyMethod by gettingFirstMethodDeclaratively(
    "onReady() savedInstanceState=",
) {
    name("onReady")
    returnType("V")
    parameterTypes("Landroid/os/Bundle;")
}
