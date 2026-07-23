package app.revanced.patches.moovit.misc.extension.hooks

import app.revanced.patcher.definingClass
import app.revanced.patcher.name
import app.revanced.patcher.parameterTypes
import app.revanced.patcher.returnType
import app.revanced.patches.shared.misc.extension.extensionHook

internal val moovitActivityInitHook = extensionHook {
    name("onReady")
    definingClass("Lcom/moovit/app/home/HomeActivity;")
    returnType("V")
    parameterTypes("Landroid/os/Bundle;")
}
