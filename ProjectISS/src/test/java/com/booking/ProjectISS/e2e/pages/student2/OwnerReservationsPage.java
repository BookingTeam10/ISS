package com.booking.ProjectISS.e2e.pages.student2;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeSuite;

import java.time.Duration;
import java.util.List;

public class OwnerReservationsPage {

    @FindBy(how = How.XPATH, using = "//button[contains(text(), 'Approve')]")
    private WebElement approveButton;


    @FindBy(how = How.XPATH, using = "//button[contains(text(), 'Reject')]")
    private WebElement rejectButton;

    private WebDriver driver;

    @FindBy(how = How.XPATH, using = "//tbody/tr")
    private List<WebElement> tableRows;
    private int rowCount = 0;

    public OwnerReservationsPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void rejectReservation(){
        (new WebDriverWait(driver, Duration.ofSeconds(10))).until(ExpectedConditions.elementToBeClickable(rejectButton)).click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void approveReservation(){
        (new WebDriverWait(driver, Duration.ofSeconds(10))).until(ExpectedConditions.elementToBeClickable(approveButton)).click();

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
}
