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

public class ParamCssUiTests extends BaseUiTests {
    private final String USERNAME_TEXT = "login";
    private final String PASSWORD_TEXT = "asdf";

    @FindBy(css = "input#username")
    private WebElement cssUsernameInput;
    @FindBy(css = "input#password")
    private WebElement cssPasswordInput;
    @ParamFindBy(css = "input#{0}")
    private ParametrizedLocator paramCssInput;
    @FindBy(css = "button[data-i18n='login']")
    private WebElement cssSignInButton;
    @ParamFindBy(css = "button[data-i18n='{0}']")
    private ParametrizedLocator paramCssButton;
    @FindBy(css = "p[data-status='error']")
    private WebElement cssLoginErrorMessage;

    @BeforeEach
    public void startBrowser() throws InterruptedException {
        startBrowserAndNavigateToPage("automation-demo.html");

        PageFactory.initElements(driver, this);
        ParametrizedLocatorFactory.initElements(driver, this);
    }

    @Test
    public void testSeleniumCssLocatorInputText() {
        cssUsernameInput.sendKeys(USERNAME_TEXT);
        cssPasswordInput.sendKeys(PASSWORD_TEXT);

        Assertions.assertEquals(USERNAME_TEXT, cssUsernameInput.getDomProperty("value"));
        Assertions.assertEquals(PASSWORD_TEXT, cssPasswordInput.getDomProperty("value"));
    }

    @Test
    public void testParametrizedCssLocatorInputText() {
        paramCssInput.resolve("username").sendKeys(USERNAME_TEXT);
        paramCssInput.resolve("password").sendKeys(PASSWORD_TEXT);

        Assertions.assertEquals(USERNAME_TEXT, cssUsernameInput.getDomProperty("value"));
        Assertions.assertEquals(PASSWORD_TEXT, cssPasswordInput.getDomProperty("value"));
    }

    @Test
    public void testCssLocatorClickButton() {
        cssUsernameInput.sendKeys(USERNAME_TEXT);
        cssPasswordInput.sendKeys(PASSWORD_TEXT);

        cssSignInButton.click();
        Assertions.assertTrue(cssLoginErrorMessage.isDisplayed());
    }

    @Test
    public void testParametrizedCssLocatorClickButton() {
        cssUsernameInput.sendKeys(USERNAME_TEXT);
        cssPasswordInput.sendKeys(PASSWORD_TEXT);

        paramCssButton.resolve("login").click();
        Assertions.assertTrue(cssLoginErrorMessage.isDisplayed());
    }

    @AfterEach
    public void stopBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }
}
