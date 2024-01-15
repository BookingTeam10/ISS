package com.booking.ProjectISS.e2e.tests.student3;

import com.booking.ProjectISS.e2e.pages.LoginPage;
import com.booking.ProjectISS.e2e.pages.MainPage;
import com.booking.ProjectISS.e2e.pages.OwnerPage;
import com.booking.ProjectISS.e2e.pages.student1.DefinitionAccommodationPage;
import com.booking.ProjectISS.e2e.pages.student1.EditAccommodationPage;
import com.booking.ProjectISS.e2e.pages.student1.MyAccommodationPage;
import com.booking.ProjectISS.e2e.tests.TestBase;
import com.booking.ProjectISS.service.accommodation.DatesPrice;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class SearchAccommodationTest extends TestBase {
    @Test
    public void SearchAndFilterAccommodations() throws InterruptedException {
        MainPage mainPage=new MainPage(driver);
        Assert.assertTrue(mainPage.isPageOpened("Booking"));
        mainPage.inputNumberGuests("3");
        mainPage.inputCity("Novi Sad");
        mainPage.inputType("Apartment");
        mainPage.inputMinPrice("2000");
        mainPage.inputMaxPrice("4000");
        //samo da vidim je l radi da ne moraju da se pomeraju datumi
        mainPage.inputDates("03/03/2025","04/04/2025");
        //ovo obrisati samo nek stoji da vidim je l radi kada se klikne
        //mainPage.clickCheckboxs();
        mainPage.clickSubmitButton();
        Thread.sleep(10000);
    }

}
