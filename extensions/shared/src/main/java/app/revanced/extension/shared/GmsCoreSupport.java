package app.revanced.extension.shared;

import android.app.Activity;

/**
 * Injection point for the GmsCore presence check.
 *
 * Called from the patched activity's onCreate (via gmsCoreSupportPatch).
 * Currently a minimal stub — logs the call but performs no installation
 * or update verification.
 *
 * TODO: Port the full GmsCore enum from
 *   revanced-patches/extensions/shared/library/.../GmsCoreSupport.java
 *   including checkInstallation(), checkUpdates(), battery-optimization
 *   dialogs, and the update-from-GitHub flow.
 */
@SuppressWarnings("unused")
public class GmsCoreSupport {
    public static void checkGmsCore(Activity context) {
        Logger.printInfo(() -> "GmsCore check triggered");
    }

    // The following two methods are replaced at patch time
    // by gmsCoreSupportPatch via returnEarly().

    private static String getOriginalPackageName() {
        return null;
    }

    private static String getGmsCoreVendorGroupId() {
        return "app.revanced";
    }
}
