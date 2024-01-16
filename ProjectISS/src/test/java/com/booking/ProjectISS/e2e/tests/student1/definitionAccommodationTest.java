package com.booking.ProjectISS.e2e.tests.student1;


import com.booking.ProjectISS.e2e.pages.*;
import com.booking.ProjectISS.e2e.pages.student1.DefinitionAccommodationPage;
import com.booking.ProjectISS.e2e.pages.student1.EditAccommodationPage;
import com.booking.ProjectISS.e2e.pages.student1.MyAccommodationPage;
import com.booking.ProjectISS.e2e.tests.TestBase;
import com.booking.ProjectISS.service.accommodation.DatesPrice;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class definitionAccommodationTest extends TestBase {

    static final String EMAIL = "popovic.sv4.2021@uns.ac.rs";
    static final String PASSWORD = "abc";


    @DataProvider(name = "returnTrue")
    private Object[][] returnTrue() {
        List<DatesPrice> datesPriceList=new ArrayList<DatesPrice>();
        datesPriceList.add(new DatesPrice("12-12-2024","12-12-2025",1000));
        datesPriceList.add(new DatesPrice("12-12-2026","12-12-2027",500));
        return new Object[][] {
                { "Naziv1", 500,1000,1500,0,datesPriceList,"Successful edit"}
        };
    }

    @Test(dataProvider = "returnTrue")
    public void canChange(String accommodationTitle,double weekendPrice,double holidayPrice,double summerPrice,int limitDays,List<DatesPrice> datesPriceList,String expectedMessage) throws InterruptedException {
        MainPage mainPage=new MainPage(driver);
        Assert.assertTrue(mainPage.isPageOpened("Booking"));
        mainPage.login();
        LoginPage loginPage=new LoginPage(driver);
        Assert.assertTrue(loginPage.isPageOpened("Login"));
        loginPage.inputLogin(EMAIL,PASSWORD);
        OwnerPage ownerPage=new OwnerPage(driver);
        Assert.assertTrue(ownerPage.isPageOpened("Booking Owner"));
        ownerPage.myAccommodationButton();
        //Thread.sleep(1000);
        MyAccommodationPage myAccommodationPage=new MyAccommodationPage(driver);
        myAccommodationPage.chooseAccommodation(accommodationTitle);
        EditAccommodationPage editAccommodationPage=new EditAccommodationPage(driver);
        //Assert.assertTrue(editAccommodationPage.isPageOpenedValid(accommodationTitle));
        editAccommodationPage.changePrices();
        DefinitionAccommodationPage definitionAccommodationPage=new DefinitionAccommodationPage(driver);
        definitionAccommodationPage.setInputButtons(weekendPrice,holidayPrice,summerPrice,limitDays);
        definitionAccommodationPage.removeOldDates();
        definitionAccommodationPage.addDates(datesPriceList);
        definitionAccommodationPage.save();
        String textAlert=editAccommodationPage.closeAlert();
        Assert.assertEquals(textAlert,expectedMessage);
        editAccommodationPage.changePrices();
        boolean validation=definitionAccommodationPage.isChanged(weekendPrice,holidayPrice,summerPrice,limitDays,datesPriceList);
        Assert.assertTrue(validation);
    }

    @DataProvider(name = "returnFalseCanChangeDayLimit")
    private Object[][] returnFalseCanChangeDayLimit() {
        List<DatesPrice> datesPriceList=new ArrayList<DatesPrice>();
        datesPriceList.add(new DatesPrice("01-03-2025","01-08-2025",1000));
        return new Object[][] {
                { "Naziv2", 500,1000,1500,5,datesPriceList,"Changed only cancelled deadline, due to reservations"}
        };
    }

    @Test(dataProvider = "returnFalseCanChangeDayLimit")
    public void canChangeOnlyLimitDay(String accommodationTitle,double weekendPrice,double holidayPrice,double summerPrice,int limitDays,List<DatesPrice> datesPriceList,String expectedMessage) throws InterruptedException {
        MainPage mainPage=new MainPage(driver);
        Assert.assertTrue(mainPage.isPageOpened("Booking"));
        mainPage.login();
        LoginPage loginPage=new LoginPage(driver);
        Assert.assertTrue(loginPage.isPageOpened("Login"));
        loginPage.inputLogin(EMAIL,PASSWORD);
        OwnerPage ownerPage=new OwnerPage(driver);
        Assert.assertTrue(ownerPage.isPageOpened("Booking Owner"));
        ownerPage.myAccommodationButton();
        Thread.sleep(1000);
        MyAccommodationPage myAccommodationPage=new MyAccommodationPage(driver);
        myAccommodationPage.chooseAccommodation(accommodationTitle);
        EditAccommodationPage editAccommodationPage=new EditAccommodationPage(driver);
        //Assert.assertTrue(editAccommodationPage.isPageOpenedValid(accommodationTitle));
        editAccommodationPage.changePrices();
        DefinitionAccommodationPage definitionAccommodationPage=new DefinitionAccommodationPage(driver);
        definitionAccommodationPage.setInputButtons(weekendPrice,holidayPrice,summerPrice,limitDays);
        definitionAccommodationPage.removeOldDates();
        definitionAccommodationPage.addDates(datesPriceList);
        definitionAccommodationPage.save();
        String textAlert=editAccommodationPage.closeAlert();
        Assert.assertEquals(textAlert,expectedMessage);
        editAccommodationPage.changePrices();
        boolean validation=definitionAccommodationPage.isChangedOnlyLimitDay(weekendPrice,holidayPrice,summerPrice,limitDays,datesPriceList);
        Assert.assertTrue(validation);
    }

    @DataProvider(name = "returnFalseCanNotChangeDayLimit")
    private Object[][] returnFalseCanNotChangeDayLimit() {
        List<DatesPrice> datesPriceList=new ArrayList<DatesPrice>();
        datesPriceList.add(new DatesPrice("01-03-2025","01-08-2025",1000));
        return new Object[][] {
                { "Naziv3", 500,1000,1500,3,datesPriceList,"You can only change the cancelled deadline, due to reservations"}
        };
    }

    @Test(dataProvider = "returnFalseCanNotChangeDayLimit")
    public void canChangeNothing(String accommodationTitle,double weekendPrice,double holidayPrice,double summerPrice,int limitDays,List<DatesPrice> datesPriceList,String expectedMessage) throws InterruptedException {
        MainPage mainPage=new MainPage(driver);
        Assert.assertTrue(mainPage.isPageOpened("Booking"));
        mainPage.login();
        LoginPage loginPage=new LoginPage(driver);
        Assert.assertTrue(loginPage.isPageOpened("Login"));
        loginPage.inputLogin(EMAIL,PASSWORD);
        OwnerPage ownerPage=new OwnerPage(driver);
        Assert.assertTrue(ownerPage.isPageOpened("Booking Owner"));
        ownerPage.myAccommodationButton();
        Thread.sleep(1000);
        MyAccommodationPage myAccommodationPage=new MyAccommodationPage(driver);
        myAccommodationPage.chooseAccommodation(accommodationTitle);
        EditAccommodationPage editAccommodationPage=new EditAccommodationPage(driver);
        //Assert.assertTrue(editAccommodationPage.isPageOpenedValid(accommodationTitle));
        editAccommodationPage.changePrices();
        DefinitionAccommodationPage definitionAccommodationPage=new DefinitionAccommodationPage(driver);
        definitionAccommodationPage.setInputButtons(weekendPrice,holidayPrice,summerPrice,limitDays);
        definitionAccommodationPage.removeOldDates();
        definitionAccommodationPage.addDates(datesPriceList);
        definitionAccommodationPage.save();
        String textAlert=editAccommodationPage.closeAlert();
        Assert.assertEquals(textAlert,expectedMessage);
        editAccommodationPage.changePrices();
        boolean validation=definitionAccommodationPage.isChangedNothing(weekendPrice,holidayPrice,summerPrice,limitDays,datesPriceList);
        Assert.assertTrue(validation);
    }


    //preklapanje datuma
}
