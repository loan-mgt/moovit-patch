package app.revanced.patches.moovit.misc.fix.location

import app.revanced.patcher.extensions.addInstructions
import app.revanced.patcher.patch.bytecodePatch
import app.revanced.patches.moovit.misc.gms.Constants.MOOVIT_PACKAGE_NAME

/**
 * Forces Moovit to use its built-in AndroidLocationSources instead of
 * FusedLocationSources (GMS) for location.
 *
 * Moovit has a dual-engine location architecture:
 * - wk9.a(Context) is the factory method that checks
 *   GoogleApiAvailability.isGooglePlayServicesAvailable().
 *   If GMS is available (result 0, 2, or 3), it returns a
 *   FusedLocationSources instance.
 *   Otherwise it returns null.
 * - The caller falls back to AndroidLocationSources if the factory returns null.
 *
 * We patch the factory to always create an AndroidLocationSources directly
 * instead of checking for Play Services availability and creating
 * FusedLocationSources. This avoids all GmsCore FLP bugs without needing
 * the Maps-style 5-patch approach (no checkLocationSettings bypass, no
 * FLP redirect, no content provider fix needed).
 *
 * Note: patching to return null instead would break the singleton
 * initializer which doesn't have the fallback logic — that's in a
 * different method.
 */
@Suppress("unused")
val fixLocationPatch = bytecodePatch(
    name = "Fix location for GmsCore",
    description = "Forces Moovit to use Android native location instead of " +
        "GmsCore's unreliable FusedLocationProvider.",
) {
    compatibleWith(
        MOOVIT_PACKAGE_NAME("5.194.0.1785"),
    )

    execute {
        locationSourceFactoryMethod.apply {
            addInstructions(
                0,
                """
                    new-instance v0, Lcom/moovit/location/AndroidLocationSources;
                    invoke-direct {v0, p0}, Lcom/moovit/location/AndroidLocationSources;-><init>(Landroid/content/Context;)V
                    return-object v0
                """,
            )
        }
    }
}
