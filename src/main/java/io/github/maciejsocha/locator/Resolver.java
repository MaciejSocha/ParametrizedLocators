package io.github.maciejsocha.locator;

public class Resolver {
    public static String resolveLocator(String pattern, String paramName) {
        if (pattern == null) {
            return null;
        }
        return pattern.replace("{0}", paramName);
    }
}
