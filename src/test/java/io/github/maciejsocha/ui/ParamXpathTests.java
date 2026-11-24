package io.github.maciejsocha.ui;

import io.github.maciejsocha.annotation.ParamFindBy;
import io.github.maciejsocha.factory.ParametrizedLocatorFactory;
import io.github.maciejsocha.locator.ParametrizedLocator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ParamXpathTests extends BaseUiTests {
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
        startBrowserAndNavigateToPage("automation-demo.html");

        PageFactory.initElements(driver, this);
        ParametrizedLocatorFactory.initElements(driver, this);
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

        xpathSubmitButton.click();
        Assertions.assertTrue(loginErrorMessage.isDisplayed());
    }

    @Test
    public void testParametrizedXpathLocatorClickButton() {
        xpathUsernameInput.sendKeys(USERNAME_TEXT);
        xpathPasswordInput.sendKeys(PASSWORD_TEXT);

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
