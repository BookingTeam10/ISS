package com.booking.ProjectISS.e2e.tests.student3;

import com.booking.ProjectISS.e2e.pages.LoginPage;
import com.booking.ProjectISS.e2e.pages.MainPage;
import com.booking.ProjectISS.e2e.pages.OwnerPage;
import com.booking.ProjectISS.e2e.pages.student1.DefinitionAccommodationPage;
import com.booking.ProjectISS.e2e.pages.student1.EditAccommodationPage;
import com.booking.ProjectISS.e2e.pages.student1.MyAccommodationPage;
import com.booking.ProjectISS.e2e.tests.TestBase;
import com.booking.ProjectISS.model.accomodations.Amenity;
import com.booking.ProjectISS.service.accommodation.DatesPrice;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchAccommodationTest extends TestBase {

    @DataProvider(name = "filter")
    private Object[][] searchFilter() {
        return new Object[][] {
                { "Novi Sad", "Apartment","2000","4000","03/03/2025","04/04/2025", Arrays.asList("WIFI","Parking"),"3"},
                { "Belgrade", "","","","","", List.of(),""},
                { "", "","","","","", List.of(),""},
                { "", "","","","","", Arrays.asList("WIFI"),""}
        };
    }

    @Test(dataProvider = "filter")
    public void SearchAndFilterAccommodations(String city, String type, String minPrice, String maxPrice, String startDate, String endDate, List<String> amenities,String numberPeople) throws InterruptedException {
        MainPage mainPage=new MainPage(driver);
        Assert.assertTrue(mainPage.isPageOpened("Booking"));
        mainPage.inputNumberGuests(numberPeople);
        mainPage.inputCity(city);
        mainPage.inputType(type);
        mainPage.inputMinPrice(minPrice);
        mainPage.inputMaxPrice(maxPrice);
        mainPage.inputDates(startDate,endDate);
        mainPage.clickMultiplyCheckbox(amenities);
        mainPage.clickSubmitButton();
        boolean validation=mainPage.searchFilter(city,numberPeople,type,amenities,minPrice,maxPrice);
        Assert.assertTrue(validation);
    }

}
