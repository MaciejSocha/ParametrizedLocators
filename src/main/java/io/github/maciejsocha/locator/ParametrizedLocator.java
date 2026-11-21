package io.github.maciejsocha.locator;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.How;

public class ParametrizedLocator {
    private final WebDriver driver;
    private final String pattern;
    private final How how;

    public ParametrizedLocator(WebDriver driver, String pattern, How how) {
        this.driver = driver;
        this.pattern = pattern;
        this.how = how;
    }

    public WebElement resolve(String paramName) {
        String locator = Resolver.resolveLocator(pattern, paramName);
        return driver.findElement(GetBy(locator, how));
    }

    private static @NotNull By GetBy(String locator, How how) {
        By by;
        switch (how) {
            case ID -> by = By.id(locator);
            case NAME -> by = By.name(locator);
            case CLASS_NAME -> by = By.className(locator);
            case CSS -> by = By.cssSelector(locator);
            case TAG_NAME -> by = By.tagName(locator);
            case LINK_TEXT -> by = By.linkText(locator);
            case XPATH -> by = By.xpath(locator);
            default -> throw new RuntimeException();
        }

        return by;
    }
}
