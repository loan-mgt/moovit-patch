package app.revanced.patches.moovit.misc.fix.location

import app.revanced.patcher.accessFlags
import app.revanced.patcher.gettingFirstMethodDeclaratively
import app.revanced.patcher.patch.BytecodePatchContext
import com.android.tools.smali.dexlib2.AccessFlags

/**
 * Matches the location source factory method that chooses between
 * FusedLocationSources (GMS) and AndroidLocationSources (native).
 *
 * Identified by the unique string combination "MoovitLocationSources" and
 * "Unable to create Google Play Services location source", which appear
 * in the catch block when the GMS check fails.
 *
 * This method is obfuscated per-release, but the string constants
 * are stable since they're log tags and error messages.
 */
internal val BytecodePatchContext.locationSourceFactoryMethod by gettingFirstMethodDeclaratively(
    "MoovitLocationSources",
    "Unable to create Google Play Services location source",
) {
    accessFlags(AccessFlags.PUBLIC, AccessFlags.STATIC)
}
