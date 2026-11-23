package io.github.maciejsocha.ui;

import io.github.maciejsocha.annotation.ParamFindBy;
import io.github.maciejsocha.factory.ParametrizedLocatorFactory;
import io.github.maciejsocha.locator.ParametrizedLocator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class ParamXpathTests {
    private WebDriver driver;

    private final String PAGE_URL = "https://practicetestautomation.com/practice-test-login/";
    private final String USERNAME_TEXT = "login";
    private final String PASSWORD_TEXT = "asdf";

    @FindBy(xpath = "//input[@id='username']")
    private WebElement xpathUsername;
    @FindBy(xpath = "//input[@id='password']")
    private WebElement xpathPassword;
    @ParamFindBy(xpath = "//input[@id='{0}']")
    private ParametrizedLocator paramXpathInput;

    @BeforeEach
    public void startBrowser() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");
        driver = new ChromeDriver(options);

        PageFactory.initElements(driver, this);
        ParametrizedLocatorFactory.initElements(driver, this);

        driver.get(PAGE_URL);
        Thread.sleep(Duration.ofSeconds(3));
    }

    @Test
    public void testSeleniumXpathLocator() {
        Assertions.assertEquals(PAGE_URL, driver.getCurrentUrl());

        xpathUsername.sendKeys(USERNAME_TEXT);
        xpathPassword.sendKeys(PASSWORD_TEXT);

        Assertions.assertEquals(USERNAME_TEXT, xpathUsername.getDomProperty("value"));
        Assertions.assertEquals(PASSWORD_TEXT, xpathPassword.getDomProperty("value"));
    }

    @Test
    public void testParametrizedXpathLocator() {
        Assertions.assertEquals(PAGE_URL, driver.getCurrentUrl());

        paramXpathInput.resolve("username").sendKeys(USERNAME_TEXT);
        paramXpathInput.resolve("password").sendKeys(PASSWORD_TEXT);

        Assertions.assertEquals(USERNAME_TEXT, xpathUsername.getDomProperty("value"));
        Assertions.assertEquals(PASSWORD_TEXT, xpathPassword.getDomProperty("value"));
    }

    @AfterEach
    public void stopBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }
}
