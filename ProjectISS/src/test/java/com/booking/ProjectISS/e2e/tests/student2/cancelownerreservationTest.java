package com.booking.ProjectISS.e2e.tests.student2;

import com.booking.ProjectISS.e2e.pages.GuestPage;
import com.booking.ProjectISS.e2e.pages.LoginPage;
import com.booking.ProjectISS.e2e.pages.MainPage;
import com.booking.ProjectISS.e2e.pages.OwnerPage;
import com.booking.ProjectISS.e2e.pages.student2.GuestReservationsPage;
import com.booking.ProjectISS.e2e.pages.student2.OwnerReservationsPage;
import com.booking.ProjectISS.e2e.tests.TestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

public class cancelownerreservationTest extends TestBase {

    static final String EMAIL = "popovic.sv4.2021@uns.ac.rs";
    static final String PASSWORD = "abc";

    static final String EMAIL_GUEST = "aleksa@gmail.com";


    @Test
    public void rejectReservation(){
        MainPage mainPage=new MainPage(driver);
        Assert.assertTrue(mainPage.isPageOpened("Booking"));
        mainPage.login();
        LoginPage loginPage=new LoginPage(driver);
        Assert.assertTrue(loginPage.isPageOpened("Login"));
        loginPage.inputLogin(EMAIL,PASSWORD);
        OwnerPage ownerPage=new OwnerPage(driver);
        Assert.assertTrue(ownerPage.isPageOpened("Booking Owner"));
        ownerPage.ReservationsButton();
        OwnerReservationsPage ownerReservationsPage = new OwnerReservationsPage(driver);
        ownerReservationsPage.loadTableRows();
        ownerReservationsPage.rejectReservation();
        boolean passed = ownerReservationsPage.checkTableRows();
        Assert.assertTrue(passed);
    }

    @Test
    public void approveReservation(){
        MainPage mainPage=new MainPage(driver);
        Assert.assertTrue(mainPage.isPageOpened("Booking"));
        mainPage.login();
        LoginPage loginPage=new LoginPage(driver);
        Assert.assertTrue(loginPage.isPageOpened("Login"));
        loginPage.inputLogin(EMAIL,PASSWORD);
        OwnerPage ownerPage=new OwnerPage(driver);
        Assert.assertTrue(ownerPage.isPageOpened("Booking Owner"));
        ownerPage.ReservationsButton();
        OwnerReservationsPage ownerReservationsPage = new OwnerReservationsPage(driver);
        ownerReservationsPage.loadTableRows();
        ownerReservationsPage.approveReservation();
        boolean passed = ownerReservationsPage.checkTableRows();
        Assert.assertTrue(passed);
    }

    @Test
    public void cancelReservations(){
        MainPage mainPage=new MainPage(driver);
        Assert.assertTrue(mainPage.isPageOpened("Booking"));
        mainPage.login();
        LoginPage loginPage=new LoginPage(driver);
        Assert.assertTrue(loginPage.isPageOpened("Login"));
        loginPage.inputLogin(EMAIL_GUEST,PASSWORD);
        GuestPage guestPage=new GuestPage(driver);
        Assert.assertTrue(guestPage.isPageOpened("Booking Guest"));
        guestPage.ReservationsButton();
        GuestReservationsPage guestReservationsPage = new GuestReservationsPage(driver);
        guestReservationsPage.loadTableRows();
        guestReservationsPage.cancelReservation();
        boolean passed = guestReservationsPage.checkTableRows();
        Assert.assertTrue(passed);
    }

    @Test
    public void cancelReservationsDeadlinePassed(){
        MainPage mainPage=new MainPage(driver);
        Assert.assertTrue(mainPage.isPageOpened("Booking"));
        mainPage.login();
        LoginPage loginPage=new LoginPage(driver);
        Assert.assertTrue(loginPage.isPageOpened("Login"));
        loginPage.inputLogin(EMAIL_GUEST,PASSWORD);
        GuestPage guestPage=new GuestPage(driver);
        Assert.assertTrue(guestPage.isPageOpened("Booking Guest"));
        guestPage.ReservationsButton();
        GuestReservationsPage guestReservationsPage = new GuestReservationsPage(driver);
        guestReservationsPage.loadTableRows();
        guestReservationsPage.cancelReservationDeadlinePassed();
        boolean passed = guestReservationsPage.checkTableRowsDeadlinePassed();
        Assert.assertTrue(passed);
    }
}
