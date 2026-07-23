package app.revanced.extension.shared;

public class Logger {
    public static void printInfo(Supplier<String> messageSupplier) {}
    public static void printException(Supplier<String> messageSupplier, Throwable ex) {}
    public static void printDebug(Supplier<String> messageSupplier) {}

    @FunctionalInterface
    public interface Supplier<T> { T get(); }
}
