package app.revanced.patches.moovit.misc.extension

import app.revanced.patches.moovit.misc.extension.hooks.moovitActivityInitHook
import app.revanced.patches.shared.misc.extension.sharedExtensionPatch

val extensionPatch = sharedExtensionPatch(moovitActivityInitHook)
