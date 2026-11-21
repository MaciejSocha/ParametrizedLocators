package io.github.maciejsocha.locator;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ParametrizedLocator {
    private final WebDriver driver;
    private final String pattern;

    public ParametrizedLocator(WebDriver driver, String pattern) {
        this.driver = driver;
        this.pattern = pattern;
    }

    public WebElement resolve(String paramName) {
        String locator = Resolver.resolveXpath(pattern, paramName);
        By by = By.xpath(locator);
        return driver.findElement(by);
    }
}
