package app.revanced.patches.moovit.misc.gms

internal object Constants {
    const val MOOVIT_PACKAGE_NAME = "com.tranzmate"
    const val REVANCED_MOOVIT_PACKAGE_NAME = "com.tranzmate"
    const val SPOOFED_PACKAGE_SIGNATURE = "290c27c2d822646f027a403ed609f78673948e85"
    // SHA-1 of ReVanced GmsCore (app.revanced.android.gms) V3 signing cert.
    // Verified against installed build on 2026-07-21.
    // If rebuilding against a different GmsCore build/fork, re-verify this hash.
}
