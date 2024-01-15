package com.booking.ProjectISS.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MyAccommodationPage {

    @FindBy(css=".mat-mdc-card.mdc-card")
    List<WebElement> accommodations;
    private WebDriver driver;

    public MyAccommodationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void chooseAccommodation(String name){
        for(WebElement accommodation:accommodations){
            WebElement nameElement=accommodation.findElement(By.cssSelector(".b-description"));
            String nameText = nameElement.getText().trim();
            if(nameText.equals(name)){
                WebElement editButton = (new WebDriverWait(driver, Duration.ofSeconds(10))).until(ExpectedConditions.elementToBeClickable(accommodation.findElement(By.xpath(".//button[contains(@class,'btn-stilizovano')]"))));
                editButton.click();
                break;
            }
        }
    }
}
