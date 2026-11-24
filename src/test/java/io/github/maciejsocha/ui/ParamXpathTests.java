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

public class ParamXpathTests extends BaseUiTests {
    @FindBy(xpath = "//input[@id='username']")
    private WebElement xpathUsernameInput;
    @FindBy(xpath = "//input[@id='password']")
    private WebElement xpathPasswordInput;
    @ParamFindBy(xpath = "//input[@id='{0}']")
    private ParametrizedLocator paramXpathInput;

    @FindBy(xpath = "//button[text()='Sign in']")
    private WebElement xpathSignInButton;
    @ParamFindBy(xpath = "//button[text()='{0}']")
    private ParametrizedLocator paramXpathButton;

    @FindBy(xpath = "//p[@data-status='error']")
    private WebElement loginErrorMessage;

    @FindBy(xpath = "//select[@name='country']")
    private WebElement xpathCountryDropdown;
    @ParamFindBy(xpath = "//select[@name='{0}']")
    private ParametrizedLocator paramXpathDropdown;
    @FindBy(xpath = "//input[@type='checkbox' and @id='terms']")
    private WebElement acceptTermsCheckbox;
    @ParamFindBy(xpath = "//input[@type='checkbox' and @id='{0}']")
    private ParametrizedLocator paramAcceptTermsCheckbox;
    @FindBy(xpath = "//button[text()='Submit']")
    private WebElement xpathSubmitButton;
    @FindBy(xpath = "//p[@class='msg' and @id='form-msg']")
    private WebElement submissionMessage;

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

    @Test
    public void testXpathLocatorDropdownSelection() {
        Select countrySelect = new Select(xpathCountryDropdown);
        countrySelect.selectByVisibleText("Czechia");

        Assertions.assertEquals("Czechia", countrySelect.getFirstSelectedOption().getText());

        acceptTermsCheckbox.click();
        xpathSubmitButton.click();

        Assertions.assertTrue(submissionMessage.isDisplayed());
    }

    @Test
    public void testParametrizedXpathLocatorDropdownSelection() {
        Select countrySelect = new Select(paramXpathDropdown.resolve("country"));
        countrySelect.selectByVisibleText("Czechia");

        Assertions.assertEquals("Czechia", countrySelect.getFirstSelectedOption().getText());

        acceptTermsCheckbox.click();
        xpathSubmitButton.click();

        Assertions.assertTrue(submissionMessage.isDisplayed());
    }

    @Test
    public void testParametrizedXpathLocatorCheckboxSelection() {
        Select countrySelect = new Select(paramXpathDropdown.resolve("country"));
        countrySelect.selectByVisibleText("Czechia");

        Assertions.assertEquals("Czechia", countrySelect.getFirstSelectedOption().getText());

        paramAcceptTermsCheckbox.resolve("terms").click();
        xpathSubmitButton.click();

        Assertions.assertTrue(submissionMessage.isDisplayed());
    }
}
