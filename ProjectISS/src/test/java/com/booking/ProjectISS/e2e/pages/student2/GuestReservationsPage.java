package com.booking.ProjectISS.e2e.pages.student2;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class GuestReservationsPage {

    @FindBy(how = How.XPATH, using = "//button[contains(text(), 'Cancel')]")
    private WebElement cancelButton;

    @FindBy(how = How.XPATH, using = "//tbody/tr[2]//button[contains(text(), 'Cancel')]")
    private WebElement cancelButtonDeadlinePassed;


    @FindBy(how = How.XPATH, using = "//tbody/tr")
    private List<WebElement> tableRows;
    private int rowCount = 0;

    private WebDriver driver;

    public GuestReservationsPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void cancelReservation(){
        (new WebDriverWait(driver, Duration.ofSeconds(10))).until(ExpectedConditions.elementToBeClickable(cancelButton)).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.alertIsPresent());

        Alert alert = driver.switchTo().alert();

        alert.accept();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void cancelReservationDeadlinePassed(){
        (new WebDriverWait(driver, Duration.ofSeconds(10))).until(ExpectedConditions.elementToBeClickable(cancelButtonDeadlinePassed)).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.alertIsPresent());

        Alert alert = driver.switchTo().alert();

        alert.accept();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void refreshTableRows(){
        tableRows = driver.findElements(By.xpath("//tbody/tr"));
    }

    public boolean checkTableRows() {
        this.refreshTableRows();
        if((this.rowCount - 1) == tableRows.size()){
            return true;
        }
        return false;
    }

    public void loadTableRows() {
        tableRows = driver.findElements(By.xpath("//tbody/tr"));
        rowCount = tableRows.size();
    }

    public boolean checkTableRowsDeadlinePassed() {
        this.refreshTableRows();
        return this.rowCount == tableRows.size();
    }
}
