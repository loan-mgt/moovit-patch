package app.revanced.extension.shared;

import android.annotation.SuppressLint;
import android.content.Context;

@SuppressLint("StaticFieldLeak")
public class Utils {
    static volatile Context context;
    public static Context getContext() { return context; }
    public static void setContext(Context appContext) { context = appContext; }
    @SuppressWarnings("SameReturnValue")
    public static String getPatchesReleaseVersion() { return ""; }
}
