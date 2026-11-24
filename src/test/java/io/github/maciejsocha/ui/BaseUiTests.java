package io.github.maciejsocha.ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.net.URL;
import java.time.Duration;

public class BaseUiTests {
    protected WebDriver driver;

    protected final String USERNAME_TEXT = "login";
    protected final String PASSWORD_TEXT = "pass";

    protected void startBrowserAndNavigateToPage() throws InterruptedException {
        startBrowserAndNavigateToPage("automation-demo.html");
    }

    protected void startBrowserAndNavigateToPage(String localPageName) throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");
        driver = new ChromeDriver(options);

        URL resource = getClass()
                .getClassLoader()
                .getResource(localPageName);
        assert resource != null;
        driver.get(resource.toExternalForm());

        Thread.sleep(Duration.ofSeconds(2));
    }
}
