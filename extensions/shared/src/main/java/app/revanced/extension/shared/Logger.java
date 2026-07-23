package app.revanced.extension.shared;

import android.util.Log;

/**
 * Minimal logger for extension code.
 *
 * Routes to android.util.Log so messages appear in logcat
 * under the "RevancedExt" tag.
 *
 * TODO: Port the full Logger from
 *   revanced-patches/extensions/shared/library/.../Logger.java
 *   once consumer-side settings/UI infrastructure is in place.
 */
public class Logger {
    private static final String TAG = "RevancedExt";

    public static void printInfo(Supplier<String> messageSupplier) {
        Log.i(TAG, messageSupplier.get());
    }

    public static void printException(Supplier<String> messageSupplier, Throwable ex) {
        Log.e(TAG, messageSupplier.get(), ex);
    }

    public static void printDebug(Supplier<String> messageSupplier) {
        Log.d(TAG, messageSupplier.get());
    }

    @FunctionalInterface
    public interface Supplier<T> { T get(); }
}
