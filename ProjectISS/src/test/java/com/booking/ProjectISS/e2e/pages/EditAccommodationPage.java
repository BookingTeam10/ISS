package com.booking.ProjectISS.e2e.pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class EditAccommodationPage {

    @FindBy(name = "name")
    private WebElement nameAccommodation;

    @FindBy(id = "changePrices")
    private WebElement changePricesButton;
    private WebDriver driver;
    public EditAccommodationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

//    public boolean isPageOpened(String title){
//        String title1=" "+title+" ";
//        boolean isOpened = (new WebDriverWait(driver, 10))
//                .until(ExpectedConditions.textToBePresentInElement(nameAccommodation, title1));
//        return isOpened;
//    }

    public boolean isPageOpenedValid(String title){
        boolean isOpened=false;
        if(title.equals(nameAccommodation.getAttribute("value"))){
            return true;
        }
        return isOpened;
    }

    public void changePrices(){
        (new WebDriverWait(driver, Duration.ofSeconds(10))).until(ExpectedConditions.elementToBeClickable(changePricesButton)).click();
    }

    public String closeAlert() {
        Alert alert = (new WebDriverWait(driver, Duration.ofSeconds(10))).until(ExpectedConditions.alertIsPresent());

        String alertText = alert.getText();

        alert.accept();
        return alertText;
    }
}
