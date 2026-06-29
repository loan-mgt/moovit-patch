package app.revanced.patches.moovit.ads

import app.revanced.patcher.Fingerprint
import app.revanced.patcher.extensions.InstructionExtensions.addInstructions
import app.revanced.patcher.fingerprint
import app.revanced.patcher.patch.bytecodePatch
import com.android.tools.smali.dexlib2.AccessFlags

val adUnitResolverFingerprint: Fingerprint = fingerprint {
    accessFlags(AccessFlags.PUBLIC, AccessFlags.FINAL)
    returns("Ljava/lang/String;")
    parameters("Lcom/moovit/app/ads/AdSource;")
    strings("is_ads_free_version")
}

@Suppress("unused")
val removeAdsPatch = bytecodePatch(
    name = "Remove ads",
    description = "Removes the ads Moovit shows around the map and search and between screens.",
) {
    compatibleWith("com.tranzmate"("5.194.0.1785"))

    apply {
        adUnitResolverFingerprint.method.addInstructions(
            0,
            """
                const-string v0, ""
                return-object v0
            """,
        )
    }
}
