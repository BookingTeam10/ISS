package com.booking.ProjectISS.e2e.pages.student1;


import com.booking.ProjectISS.service.accommodation.DatesPrice;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class DefinitionAccommodationPage {

    @FindBy(id = "weekendPrice")
    private WebElement weekendPriceInput;

    @FindBy(id = "holidayPrice")
    private WebElement holidayPriceInput;
    @FindBy(id = "summerPrice")
    private WebElement summerPriceInput;
    @FindBy(id = "cancelLimit")
    private WebElement cancelLimitInput;

    @FindBy(id = "priceInput")
    private WebElement priceInput;

    //.cdk-row.ng-star-inserted
    ////tr[contains(@class, 'cdk-row ng-star-inserted')]
    @FindBy(xpath = "//tr[contains(@class, 'cdk-row ng-star-inserted')]")
    List<WebElement> rowsDates;

    @FindBy(xpath = "//tr[contains(@class, 'cdk-row ng-star-inserted')]/td[contains(@class, 'cdk-column-firstDate')]")
    List<WebElement> startDates;

    @FindBy(xpath = "//tr[contains(@class, 'cdk-row ng-star-inserted')]//button")
    List<WebElement> deleteDates;
    @FindBy(xpath = "//div[@class='mat-mdc-form-field-flex ng-tns-c1205077789-0']//button")
    WebElement calendarButton;

    @FindBy(xpath = "//button[@aria-label='Next month']")
    WebElement nextMonthButton;
    @FindBy(xpath = "//button[@aria-label='Next month']")
    WebElement dateButton;

    @FindBy(xpath ="//table[@class='mat-calendar-table']//button")
    List<WebElement> calendarButtons;

    @FindBy(id="startDateInput")
    WebElement startDateInput;
    @FindBy(id="endDateInput")
    WebElement endDateInput;
    @FindBy(id="addDate")
    WebElement addDateButton;
    @FindBy(id="definitionSaveButton")
    WebElement definitionSaveButton;

    private WebDriver driver;
    public DefinitionAccommodationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    public void setInputButtons(double weekendPriceDouble, double holidayPriceDouble, double summerPriceDouble, int cancelledDay) {
        (new WebDriverWait(driver, Duration.ofSeconds(10))).until(ExpectedConditions.elementToBeClickable(weekendPriceInput)).click();
        weekendPriceInput.clear();
        weekendPriceInput.sendKeys(String.valueOf(weekendPriceDouble));
        (new WebDriverWait(driver,  Duration.ofSeconds(10))).until(ExpectedConditions.elementToBeClickable(holidayPriceInput)).click();
        holidayPriceInput.clear();
        holidayPriceInput.sendKeys(String.valueOf(holidayPriceDouble));
        (new WebDriverWait(driver,  Duration.ofSeconds(10))).until(ExpectedConditions.elementToBeClickable(summerPriceInput)).click();
        summerPriceInput.clear();
        summerPriceInput.sendKeys(String.valueOf(summerPriceDouble));
        (new WebDriverWait(driver,  Duration.ofSeconds(10))).until(ExpectedConditions.elementToBeClickable(cancelLimitInput)).click();
        cancelLimitInput.clear();
        cancelLimitInput.sendKeys(String.valueOf(cancelledDay));
    }

    public void removeOldDates() {
        for(WebElement w:deleteDates){
            (new WebDriverWait(driver,  Duration.ofSeconds(10))).until(ExpectedConditions.elementToBeClickable(w)).click();
        }
    }

    public void addDates(List<DatesPrice> datesPriceList){

        for(DatesPrice datesPrice:datesPriceList){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
            LocalDate startDate = LocalDate.parse(datesPrice.getStartDate(), formatter);
            LocalDate newStartDate = startDate.plusDays(1);
            String newStartDateStr = newStartDate.format(formatter);
            LocalDate endDate = LocalDate.parse(datesPrice.getEndDate(), formatter);
            LocalDate newEndDate = endDate.plusDays(1);
            String newEndDateStr = newEndDate.format(formatter);
            (new WebDriverWait(driver,  Duration.ofSeconds(10))).until(ExpectedConditions.elementToBeClickable(startDateInput)).click();
            startDateInput.clear();
            startDateInput.sendKeys(newStartDateStr);
            (new WebDriverWait(driver,  Duration.ofSeconds(10))).until(ExpectedConditions.elementToBeClickable(endDateInput)).click();
            endDateInput.clear();
            endDateInput.sendKeys(newEndDateStr);
            (new WebDriverWait(driver,  Duration.ofSeconds(10))).until(ExpectedConditions.elementToBeClickable(priceInput)).click();
            priceInput.clear();
            priceInput.sendKeys(String.valueOf(datesPrice.getPrice()));
            (new WebDriverWait(driver,  Duration.ofSeconds(10))).until(ExpectedConditions.elementToBeClickable(addDateButton)).click();
        }
    }

    public void save() {
        (new WebDriverWait(driver,  Duration.ofSeconds(10))).until(ExpectedConditions.elementToBeClickable(definitionSaveButton)).click();
    }

    public boolean isChanged(double weekendPriceDouble, double holidayPriceDouble, double summerPriceDouble, int cancelledDay, List<DatesPrice> datesPriceList) {
        double setWeekendPriceInput=Double.parseDouble(weekendPriceInput.getAttribute("value"));
        if(setWeekendPriceInput!=weekendPriceDouble){
            return false;
        }
        double setHolidayPriceInput=Double.parseDouble(holidayPriceInput.getAttribute("value"));
        if(setHolidayPriceInput!=holidayPriceDouble){
            return false;
        }
        double setSummerPriceInput=Double.parseDouble(summerPriceInput.getAttribute("value"));
        if(setSummerPriceInput!=summerPriceDouble){
            return false;
        }
        int cancelledDayInput=Integer.parseInt(cancelLimitInput.getAttribute("value"));
        if(cancelledDayInput!=cancelledDay){
            return false;
        }
        int counter=0;
        for(WebElement row:rowsDates){
            WebElement startDate=row.findElement(By.cssSelector(".cdk-column-firstDate"));
            WebElement endDate=row.findElement(By.cssSelector(".cdk-column-endDate"));
            WebElement price=row.findElement(By.cssSelector(".cdk-column-price"));
            String startDateText = startDate.getText().trim();
            String endDateText = endDate.getText().trim();
            double priceDouble = Double.parseDouble(price.getText().trim());
            DatesPrice datesPrice=datesPriceList.get(counter);
            String startDateValid=changeForm(datesPrice.getStartDate());
            String endDateValid=changeForm(datesPrice.getEndDate());
            if(!startDateText.equals(startDateValid)){
                return false;
            }
            if(!endDateText.equals(endDateValid)){
                return false;
            }
            if(priceDouble!=datesPrice.getPrice()){
                return false;
            }
            counter++;
        }
        return true;
    }

    private String changeForm(String startDate) {
        String[] parts = startDate.split("-");

        if (parts.length == 3) {
            String newDateStr = parts[2] + "-" + parts[0] + "-" + parts[1];
            return newDateStr;
        }
        return "";
    }

    public boolean isChangedOnlyLimitDay(double weekendPriceDouble, double holidayPriceDouble, double summerPriceDouble, int cancelledDay, List<DatesPrice> datesPriceList) {
        double setWeekendPriceInput=Double.parseDouble(weekendPriceInput.getAttribute("value"));
        if(setWeekendPriceInput==weekendPriceDouble){
            return false;
        }
        double setHolidayPriceInput=Double.parseDouble(holidayPriceInput.getAttribute("value"));
        if(setHolidayPriceInput==holidayPriceDouble){
            return false;
        }
        double setSummerPriceInput=Double.parseDouble(summerPriceInput.getAttribute("value"));
        if(setSummerPriceInput==summerPriceDouble){
            return false;
        }
        int cancelledDayInput=Integer.parseInt(cancelLimitInput.getAttribute("value"));
        if(cancelledDayInput!=cancelledDay){
            return false;
        }
        return true;
    }

    public boolean isChangedNothing(double weekendPriceDouble, double holidayPriceDouble, double summerPriceDouble, int cancelledDay, List<DatesPrice> datesPriceList) {
        double setWeekendPriceInput=Double.parseDouble(weekendPriceInput.getAttribute("value"));
        if(setWeekendPriceInput==weekendPriceDouble){
            return false;
        }
        double setHolidayPriceInput=Double.parseDouble(holidayPriceInput.getAttribute("value"));
        if(setHolidayPriceInput==holidayPriceDouble){
            return false;
        }
        double setSummerPriceInput=Double.parseDouble(summerPriceInput.getAttribute("value"));
        if(setSummerPriceInput==summerPriceDouble){
            return false;
        }
        int cancelledDayInput=Integer.parseInt(cancelLimitInput.getAttribute("value"));
        if(cancelledDayInput!=cancelledDay){
            return false;
        }
        return true;
    }
}
