package com.booking.ProjectISS.e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GuestPage {

    @FindBy(css = "h1.title")
    private WebElement heading;
    @FindBy(xpath = "//span[contains(text(), 'Reservations')]")
    private WebElement reservationsButton;

    private WebDriver driver;

    public GuestPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isPageOpened(String title){
        boolean isOpened = (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.textToBePresentInElement(heading, title));
        return isOpened;
    }

    public void ReservationsButton() {
        (new WebDriverWait(driver, Duration.ofSeconds(10))).until(ExpectedConditions.elementToBeClickable(reservationsButton)).click();
    }
}
