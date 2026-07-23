package app.revanced.patches.moovit.misc.gms

import app.revanced.patcher.patch.BytecodePatchContext
import app.revanced.patcher.patch.Option
import app.revanced.patches.moovit.misc.extension.extensionPatch
import app.revanced.patches.moovit.misc.fix.location.fixLocationPatch
import app.revanced.patches.moovit.misc.gms.Constants.MOOVIT_PACKAGE_NAME
import app.revanced.patches.moovit.misc.gms.Constants.REVANCED_MOOVIT_PACKAGE_NAME
import app.revanced.patches.moovit.misc.gms.Constants.SPOOFED_PACKAGE_SIGNATURE
import app.revanced.patches.shared.misc.gms.gmsCoreSupportPatch
import app.revanced.patches.shared.misc.gms.gmsCoreSupportResourcePatch

@Suppress("unused")
val gmsCoreSupportPatch = gmsCoreSupportPatch(
    fromPackageName = MOOVIT_PACKAGE_NAME,
    toPackageName = REVANCED_MOOVIT_PACKAGE_NAME,
    getMainActivityOnCreateMethodToGetInsertIndex =
        BytecodePatchContext::moovitActivityOnReadyMethod::get to { 0 },
    extensionPatch = extensionPatch,
    gmsCoreSupportResourcePatchFactory = ::gmsCoreSupportResourcePatch,
) {
    dependsOn(fixLocationPatch)

    compatibleWith(
        MOOVIT_PACKAGE_NAME("5.194.0.1785"),
    )
}

private fun gmsCoreSupportResourcePatch(
    gmsCoreVendorGroupIdOption: Option<String>,
) = gmsCoreSupportResourcePatch(
    fromPackageName = MOOVIT_PACKAGE_NAME,
    toPackageName = REVANCED_MOOVIT_PACKAGE_NAME,
    spoofedPackageSignature = SPOOFED_PACKAGE_SIGNATURE,
    gmsCoreVendorGroupIdOption = gmsCoreVendorGroupIdOption,
)
