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

public class MainPage {

    @FindBy(css = "h1.title")
    private WebElement heading;
    @FindBy(id = "login")
    private WebElement loginButton;
    @FindBy(css = "#number-guests")
    private WebElement numberGuestsBox;
    @FindBy(css = "#city-input")
    private WebElement city;
    @FindBy(css = "#mat-input-2")
    private WebElement type;
    @FindBy(css = "#city-input")
    private WebElement date;
    @FindBy(css = "#mat-input-3")
    private WebElement minPrice;
    @FindBy(css = "#mat-input-4")
    private WebElement maxPrice;
    @FindBy(css = "#wifi div.mdc-checkbox__background")
    private WebElement wifi;
    @FindBy(css = "#mat-mdc-checkbox-2 div.mdc-checkbox__background")
    private WebElement parking;
    @FindBy(css = "#mat-mdc-checkbox-3 div.mdc-checkbox__background")
    private WebElement air;
    @FindBy(css = "#mat-mdc-checkbox-4 div.mdc-checkbox__background")
    private WebElement kitchen;
    @FindBy(css = "button[color='primary'] > span.mdc-button__label")
    private WebElement buttonSubmit;
    @FindBy(css = "input[placeholder='Start date']")
    private WebElement startDate;
    @FindBy(css = "input[placeholder='End date']")
    private WebElement endDate;
    @FindBy(xpath = "//section[@class='example-section']/mat-checkbox")
    List<WebElement> amenities;

    @FindBy(xpath = "//section[@class='example-section']//label")
    List<WebElement> amenitiesText;

    @FindBy(css=".mat-mdc-card.mdc-card")
    List<WebElement> accommodations;
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
    public void inputNumberGuests(String numberGuestsText) {
        (new WebDriverWait(driver, Duration.ofSeconds(10))).until(ExpectedConditions.elementToBeClickable(numberGuestsBox)).click();
        numberGuestsBox.clear();
        numberGuestsBox.sendKeys(numberGuestsText);
    }
    public void inputCity(String cityText) {
        (new WebDriverWait(driver, Duration.ofSeconds(10))).until(ExpectedConditions.elementToBeClickable(city)).click();
        city.clear();
        city.sendKeys(cityText);
    }
    public void inputType(String typeText) {
        (new WebDriverWait(driver, Duration.ofSeconds(10))).until(ExpectedConditions.elementToBeClickable(type)).click();
        type.clear();
        type.sendKeys(typeText);
    }
    public void inputMinPrice(String priceText)  {
        (new WebDriverWait(driver, Duration.ofSeconds(10))).until(ExpectedConditions.elementToBeClickable(minPrice)).click();
        minPrice.clear();
        minPrice.sendKeys(priceText);
    }
    public void inputMaxPrice(String priceText) {
        (new WebDriverWait(driver, Duration.ofSeconds(10))).until(ExpectedConditions.elementToBeClickable(maxPrice)).click();
        maxPrice.clear();
        maxPrice.sendKeys(priceText);
    }
    public void inputDates(String startDateText,String endDateText) {
        (new WebDriverWait(driver, Duration.ofSeconds(10))).until(ExpectedConditions.elementToBeClickable(startDate)).click();
        startDate.clear();
        startDate.sendKeys(startDateText);
        (new WebDriverWait(driver, Duration.ofSeconds(10))).until(ExpectedConditions.elementToBeClickable(endDate)).click();
        endDate.clear();
        endDate.sendKeys(endDateText);
    }

    public void clickSubmitButton() throws InterruptedException {
        (new WebDriverWait(driver, Duration.ofSeconds(10))).until(ExpectedConditions.elementToBeClickable(buttonSubmit)).click();
        Thread.sleep(100);
    }

    public void clickMultiplyCheckbox(List<String> allAmenities) {
        for(WebElement amenity:amenities){
            WebElement amenityElement=amenity.findElement(By.cssSelector(".mdc-label"));
            String amenityText = amenityElement.getText().trim();
            for(String singleAmenity:allAmenities){
                if(amenityText.equals(singleAmenity)){
                    WebElement check=amenity.findElement(By.cssSelector(".mdc-label"));
                    check.click();
                }
            }
        }
    }

    public boolean searchFilter(String city,String numberPerson,String type,List<String> amenities,String minPrice,String maxPrice) { //dodati validaciju ako je nesto "" da se skipuje to
        for(WebElement accommodation:accommodations){
            WebElement cityElement=accommodation.findElement(By.id("city"));
            WebElement minPeopleElement=accommodation.findElement(By.id("minPeople"));
            WebElement maxPeopleElement=accommodation.findElement(By.id("maxPeople"));
            WebElement priceElement=accommodation.findElement(By.id("price"));
            WebElement amenitiesElement=accommodation.findElement(By.id("amenities"));
            if(!cityElement.getText().trim().contains(city)){
                return false;
            }
            if(!numberPerson.equals("")){
                if(Integer.parseInt(minPeopleElement.getText().split(":")[1].trim())>Integer.parseInt(numberPerson) || Integer.parseInt(maxPeopleElement.getText().split(":")[1].trim())<Integer.parseInt(numberPerson)){
                    return false;
                }
            }
            if(!minPrice.equals("")){
                if(Integer.parseInt(priceElement.getText().split(":")[1].trim())<Integer.parseInt(minPrice)){
                    return false;
                }
            }
            if(!maxPrice.equals("")){
                if(Integer.parseInt(priceElement.getText().split(":")[1].trim())>Integer.parseInt(maxPrice)){
                    return false;
                }
            }
            for(String a:amenities){
                if(!amenitiesElement.getText().contains(a)){
                    return false;
                }
            }
        }
        return true;
    }
}
