package app.revanced.patches.moovit.misc.gms

internal object Constants {
    const val MOOVIT_PACKAGE_NAME = "com.tranzmate"
    const val REVANCED_MOOVIT_PACKAGE_NAME = "com.tranzmate"
    // Package rename intentionally disabled: Moovit ships as a split-APK set
    // (base + 27 config splits). Renaming the package would orphan all splits
    // and break resource resolution. Signature spoofing (the meta-data added
    // by gmsCoreSupportResourcePatch) works independently of the package name,
    // so the GmsCore check still authenticates correctly.
    // If a future build drops split support, set this to "app.revanced.tranzmate".

    const val SPOOFED_PACKAGE_SIGNATURE = "290c27c2d822646f027a403ed609f78673948e85"
    // SHA-1 of ReVanced GmsCore (app.revanced.android.gms) V3 signing cert.
    // Verified against installed build on 2026-07-21.
    // If rebuilding against a different GmsCore build/fork, re-verify this hash.
}
