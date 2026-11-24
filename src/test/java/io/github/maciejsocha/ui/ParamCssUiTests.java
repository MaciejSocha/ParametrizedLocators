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
import org.openqa.selenium.support.ui.Select;

public class ParamCssUiTests extends BaseUiTests {
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

    @FindBy(css = "select[name='country']")
    private WebElement cssCountryDropdown;
    @ParamFindBy(css = "select[name='{0}']")
    private ParametrizedLocator paramCssDropdown;
    @FindBy(css = "input[type='checkbox'][id='terms']")
    private WebElement acceptTermsCheckbox;
    @ParamFindBy(css = "input[type='checkbox'][id='{0}']")
    private ParametrizedLocator paramCssCheckbox;
    @FindBy(css = "button[data-i18n='submit']")
    private WebElement cssSubmitButton;
    @FindBy(css = "p[class='msg'][id='form-msg']")
    private WebElement cssSubmissionMessage;

    @BeforeEach
    public void startBrowser() throws InterruptedException {
        startBrowserAndNavigateToPage();

        PageFactory.initElements(driver, this);
        ParametrizedLocatorFactory.initElements(driver, this);
    }

    @AfterEach
    public void stopBrowser() {
        if (driver != null) {
            driver.quit();
        }
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

    @Test
    public void testCssLocatorDropdownSelection() {
        Select countrySelect = new Select(cssCountryDropdown);
        countrySelect.selectByVisibleText("Czechia");

        Assertions.assertEquals("Czechia", countrySelect.getFirstSelectedOption().getText());

        acceptTermsCheckbox.click();
        cssSubmitButton.click();

        Assertions.assertTrue(cssSubmissionMessage.isDisplayed());
    }

    @Test
    public void testParametrizedXpathLocatorDropdownSelection() {
        Select countrySelect = new Select(paramCssDropdown.resolve("country"));
        countrySelect.selectByVisibleText("Czechia");

        Assertions.assertEquals("Czechia", countrySelect.getFirstSelectedOption().getText());

        acceptTermsCheckbox.click();
        cssSubmitButton.click();

        Assertions.assertTrue(cssSubmissionMessage.isDisplayed());
    }

    @Test
    public void testParametrizedXpathLocatorCheckboxSelection() {
        Select countrySelect = new Select(paramCssDropdown.resolve("country"));
        countrySelect.selectByVisibleText("Czechia");

        Assertions.assertEquals("Czechia", countrySelect.getFirstSelectedOption().getText());

        paramCssCheckbox.resolve("terms").click();
        cssSubmitButton.click();

        Assertions.assertTrue(cssSubmissionMessage.isDisplayed());
    }
}
