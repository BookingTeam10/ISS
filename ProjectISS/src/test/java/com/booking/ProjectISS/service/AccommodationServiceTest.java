package com.booking.ProjectISS.service;


import com.booking.ProjectISS.enums.AccommodationStatus;
import com.booking.ProjectISS.enums.TypeAccommodation;
import com.booking.ProjectISS.model.accomodations.Accommodation;
import com.booking.ProjectISS.model.accomodations.Amenity;
import com.booking.ProjectISS.model.accomodations.Price;
import com.booking.ProjectISS.model.accomodations.TakenDate;
import com.booking.ProjectISS.model.reservations.Reservation;
import com.booking.ProjectISS.repository.accomodations.IAccommodationRepository;
import com.booking.ProjectISS.repository.reservations.IReservationRepository;
import com.booking.ProjectISS.service.accommodation.AccommodationService;
import com.booking.ProjectISS.service.accommodation.price.PriceService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testng.annotations.DataProvider;

import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertFalse;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class AccommodationServiceTest {

    @Autowired
    private AccommodationService accommodationService;

    @MockBean
    private IAccommodationRepository accommodationRepository;

    @MockBean
    private IReservationRepository reservationRepository;

    @MockBean
    private PriceService priceService;


    @Test
    public void changeDatesTrue() {
        Accommodation accommodation=getAccommodation();
        when(accommodationRepository.findById(1L)).thenReturn(Optional.of(accommodation));
        when(reservationRepository.findByAccommodation(1L)).thenReturn(accommodation.getReservations());
        when(priceService.update(any(Price.class))).thenReturn(null);
        String message = accommodationService.updateAccommodation(accommodation);
        verify(accommodationRepository).findById(1L);
        verify(reservationRepository).findByAccommodation(1L);
        verify(priceService, times(accommodation.getPrices().size())).update(any(Price.class));
        assertEquals(message,"Successful edit");
        verify(accommodationRepository).save(accommodation);
    }

    private Accommodation getAccommodation(){
        List<String> photos=new ArrayList<String>();
        List<TakenDate> takenDates=new ArrayList<TakenDate>();
        List<Amenity> amenities=new ArrayList<Amenity>();
        List<Price> prices=new ArrayList<Price>();
        List<Reservation> reservations=new ArrayList<Reservation>();
        Reservation reservation1 = new Reservation(1L,new Date(124, 0, 22),new Date(124, 0, 25));
        Reservation reservation2 = new Reservation(2L,new Date(124, 0, 26),new Date(124, 0, 30));
        reservations.add(reservation1);
        reservations.add(reservation2);
        Accommodation accommodation=new Accommodation(1L,true,true,"111",0,0,"Naziv1",photos, TypeAccommodation.Apartment,0,0,prices,takenDates,amenities,null,null,reservations,0,0,0,true, AccommodationStatus.CREATED);
        return accommodation;
    }


    @DataProvider(name = "change_date_false_edit_deadline")
    private Object[][] dataProviderForFalseEditDeadlineResults() {
        String message="You can only change the cancelled deadline, due to reservations";
        return new Object[][] {
                {2L,message},
                {3L,message},
                {4L,message}
        };
    }

    @DataProvider(name = "change_date_false_no_edit_deadline")
    private Object[][] dataProviderForFalseNoEditDeadlineResults() {
        String message="Changed only cancelled deadline, due to reservations";
        return new Object[][] {
                {5L,message}
        };
    }

    @DataProvider(name = "change_date_true")
    private Object[][] dataProviderForTrueResults() {
        String message="Successful edit";
        return new Object[][] {
                {1L,message}
        };
    }

    @org.testng.annotations.Test(dataProvider = "change_date_true")
    public void changeDatesTrue(Long accommodationId,String expectedMessage) {
        List<String> photos=new ArrayList<String>();
        List<TakenDate> takenDates=new ArrayList<TakenDate>();
        List<Amenity> amenities=new ArrayList<Amenity>();
        List<Price> prices=new ArrayList<Price>();
        List<Reservation> reservations=new ArrayList<Reservation>();
        Reservation reservation1 = new Reservation(1L,new Date(124, 0, 22),new Date(124, 0, 25));
        Reservation reservation2 = new Reservation(2L,new Date(124, 0, 26),new Date(124, 0, 30));
        reservations.add(reservation1);
        reservations.add(reservation2);
        Accommodation accommodation=new Accommodation(accommodationId,true,true,"111",0,0,"Naziv1",photos, TypeAccommodation.Apartment,0,0,prices,takenDates,amenities,null,null,reservations,0,0,0,true, AccommodationStatus.CREATED);

        System.out.println(accommodation);

        Mockito.when(accommodationRepository.findById(accommodationId)).thenReturn(Optional.of(accommodation));
        Mockito.when(reservationRepository.findByAccommodation(accommodationId)).thenReturn(accommodation.getReservations());
        Mockito.when(priceService.update(any(Price.class))).thenReturn(null);

        String message = accommodationService.updateAccommodation(accommodation);

        verify(accommodationRepository).findById(accommodationId);
        verify(reservationRepository).findByAccommodation(accommodationId);
        verify(priceService, times(accommodation.getPrices().size())).update(any(Price.class));
        assertEquals(message,expectedMessage);
        verify(accommodationRepository).save(accommodation);
    }
}
