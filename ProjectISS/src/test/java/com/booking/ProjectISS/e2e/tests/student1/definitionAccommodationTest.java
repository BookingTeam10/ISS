package com.booking.ProjectISS.e2e.tests.student1;


import com.booking.ProjectISS.e2e.pages.*;
import com.booking.ProjectISS.e2e.pages.student1.DefinitionAccommodationPage;
import com.booking.ProjectISS.e2e.pages.student1.EditAccommodationPage;
import com.booking.ProjectISS.e2e.pages.student1.MyAccommodationPage;
import com.booking.ProjectISS.e2e.tests.TestBase;
import com.booking.ProjectISS.service.accommodation.DatesPrice;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class definitionAccommodationTest extends TestBase {

    static final String EMAIL = "popovic.sv4.2021@uns.ac.rs";
    static final String PASSWORD = "abc";

    static final String FIRST_ACCOMMODATION_NAME = "Naziv1";

    static final String SECOND_ACCOMMODATION_NAME = "Naziv2";

    static final String THIRD_ACCOMMODATION_NAME = "Naziv3";

    @Test
    public void canChange() throws InterruptedException {
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
        myAccommodationPage.chooseAccommodation(FIRST_ACCOMMODATION_NAME);
        EditAccommodationPage editAccommodationPage=new EditAccommodationPage(driver);
        Assert.assertTrue(editAccommodationPage.isPageOpenedValid(FIRST_ACCOMMODATION_NAME));
        editAccommodationPage.changePrices();
        DefinitionAccommodationPage definitionAccommodationPage=new DefinitionAccommodationPage(driver);
        definitionAccommodationPage.setInputButtons(500,1000,1500,0);
        definitionAccommodationPage.removeOldDates();
        List<DatesPrice> datesPriceList=new ArrayList<DatesPrice>();
        datesPriceList.add(new DatesPrice("12-12-2024","12-12-2025",1000));
        datesPriceList.add(new DatesPrice("12-12-2026","12-12-2027",500));
        definitionAccommodationPage.addDates(datesPriceList);
        definitionAccommodationPage.save();

        String textAlert=editAccommodationPage.closeAlert();
        Assert.assertEquals(textAlert,"Successful edit");
        editAccommodationPage.changePrices();
        boolean validation=definitionAccommodationPage.isChanged(500,1000,1500,0,datesPriceList);
        Assert.assertTrue(validation);
    }

    @Test
    public void canChangeOnlyLimitDay() throws InterruptedException {
    }

    @Test
    public void canNotChange() throws InterruptedException {
        //December 10, 2023
        //aria-label="January 1, 2024"
    }
}
