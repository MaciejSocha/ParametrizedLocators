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

import java.net.URL;
import java.time.Duration;

public class ParamXpathTests {
    private WebDriver driver;

    private final String USERNAME_TEXT = "login";
    private final String PASSWORD_TEXT = "asdf";

    @FindBy(xpath = "//input[@id='username']")
    private WebElement xpathUsernameInput;
    @FindBy(xpath = "//input[@id='password']")
    private WebElement xpathPasswordInput;
    @ParamFindBy(xpath = "//input[@id='{0}']")
    private ParametrizedLocator paramXpathInput;
    @FindBy(xpath = "//button[text()='Sign in']")
    private WebElement xpathSubmitButton;
    @ParamFindBy(xpath = "//button[text()='{0}']")
    private ParametrizedLocator paramXpathButton;
    @FindBy(xpath = "//p[@data-status='error']")
    private WebElement loginErrorMessage;

    @BeforeEach
    public void startBrowser() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");
        driver = new ChromeDriver(options);

        PageFactory.initElements(driver, this);
        ParametrizedLocatorFactory.initElements(driver, this);

        URL resource = getClass()
                .getClassLoader()
                .getResource("automation-demo.html");
        assert resource != null;
        driver.get(resource.toExternalForm());

        Thread.sleep(Duration.ofSeconds(2));
    }

    @Test
    public void testSeleniumXpathLocatorInputText() {
        xpathUsernameInput.sendKeys(USERNAME_TEXT);
        xpathPasswordInput.sendKeys(PASSWORD_TEXT);

        Assertions.assertEquals(USERNAME_TEXT, xpathUsernameInput.getDomProperty("value"));
        Assertions.assertEquals(PASSWORD_TEXT, xpathPasswordInput.getDomProperty("value"));
    }

    @Test
    public void testParametrizedXpathLocatorInputText() {
        paramXpathInput.resolve("username").sendKeys(USERNAME_TEXT);
        paramXpathInput.resolve("password").sendKeys(PASSWORD_TEXT);

        Assertions.assertEquals(USERNAME_TEXT, xpathUsernameInput.getDomProperty("value"));
        Assertions.assertEquals(PASSWORD_TEXT, xpathPasswordInput.getDomProperty("value"));
    }

    @Test
    public void testXpathLocatorClickButton() {
        xpathUsernameInput.sendKeys(USERNAME_TEXT);
        xpathPasswordInput.sendKeys(PASSWORD_TEXT);

        Assertions.assertFalse(loginErrorMessage.isDisplayed());
        xpathSubmitButton.click();
        Assertions.assertTrue(loginErrorMessage.isDisplayed());
    }

    @Test
    public void testParametrizedXpathLocatorClickButton() {
        xpathUsernameInput.sendKeys(USERNAME_TEXT);
        xpathPasswordInput.sendKeys(PASSWORD_TEXT);

        Assertions.assertFalse(loginErrorMessage.isDisplayed());
        paramXpathButton.resolve("Sign in").click();
        Assertions.assertTrue(loginErrorMessage.isDisplayed());
    }

    @AfterEach
    public void stopBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }
}
