package com.booking.ProjectISS.e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {

//    @FindBy(xpath = "//span[contains(text(), 'Booking')]")
//    private WebElement heading;
      @FindBy(css = "h1.title")
      private WebElement heading;

//    @FindBy(xpath = "//span[contains(text(), 'Login')]")
//    private WebElement loginButton;

    @FindBy(id = "login")
    private WebElement loginButton;


    private static String PAGE_URL="http://localhost:4200/accommodations";
    private WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        driver.get(PAGE_URL);
        PageFactory.initElements(driver, this);
    }

    public boolean isPageOpened(String title){
        boolean isOpened = (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.textToBePresentInElement(heading, title));
        return isOpened;
    }

    public void login() {
        (new WebDriverWait(driver, Duration.ofSeconds(10))).until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }
}
