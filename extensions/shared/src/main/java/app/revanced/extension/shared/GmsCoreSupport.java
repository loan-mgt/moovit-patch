package app.revanced.extension.shared;

import android.app.Activity;

@SuppressWarnings("unused")
public class GmsCoreSupport {
    public static void checkGmsCore(Activity context) {
        Logger.printInfo(() -> "GmsCore check triggered");
    }
    private static String getOriginalPackageName() { return null; }
    private static String getGmsCoreVendorGroupId() { return "app.revanced"; }
}
