package com.booking.ProjectISS.service;

import com.beust.ah.A;
import com.booking.ProjectISS.enums.AccommodationStatus;
import com.booking.ProjectISS.enums.TypeAccommodation;
import com.booking.ProjectISS.model.accomodations.Accommodation;
import com.booking.ProjectISS.model.accomodations.Amenity;
import com.booking.ProjectISS.model.accomodations.Price;
import com.booking.ProjectISS.model.accomodations.TakenDate;
import com.booking.ProjectISS.model.reservations.Reservation;
import com.booking.ProjectISS.repository.accomodations.IAccommodationRepository;
import com.booking.ProjectISS.service.accommodation.AccommodationService;
import com.booking.ProjectISS.repository.reservations.IReservationRepository;
import com.booking.ProjectISS.service.accommodation.price.PriceService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

import java.util.*;

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

    @DataProvider(name = "change_date_true")
    private Object[][] dataProviderForTrueResults() {
        String message="Successful edit";
        return new Object[][] {
                {1L,message}
        };
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

    @Test(dataProvider = "change_date_true")
    public void changeDatesTrue(Long accommodationId,String expectedMessage) {
        System.out.println(accommodationId);
        List<String> photos=new ArrayList<String>();
        List<TakenDate> takenDates=new ArrayList<TakenDate>();
        List<Amenity> amenities=new ArrayList<Amenity>();
        List<Price> prices=new ArrayList<Price>();
        List<Reservation> reservations=new ArrayList<Reservation>();
        Accommodation accommodation=new Accommodation(accommodationId,true,true,"111",0,0,"Naziv1",photos, TypeAccommodation.Apartment,0,0,prices,takenDates,amenities,null,null,reservations,0,0,0,true, AccommodationStatus.CREATED);

        System.out.println(accommodation);

//        Reservation reservation1 = new Reservation(1L,new Date(124, 0, 22),new Date(124, 0, 25));
//        Reservation reservation2 = new Reservation(2L,new Date(124, 0, 26),new Date(124, 0, 30));
//        List<Reservation> reservations1 = Arrays.asList(reservation1, reservation2);

        Mockito.when(accommodationRepository.findById(accommodationId)).thenReturn(Optional.of(accommodation));
        Mockito.when(reservationRepository.findByAccommodation(accommodationId)).thenReturn(reservations);
        Mockito.when(priceService.update(any(Price.class))).thenReturn(null);

        String message = accommodationService.updateAccommodation(accommodation);

        verify(accommodationRepository).findById(accommodationId);
        verify(reservationRepository).findByAccommodation(accommodationId);
        verify(priceService, times(accommodation.getPrices().size())).update(any(Price.class));
        assertEquals(message,expectedMessage);
        verify(accommodationRepository).save(accommodation);
    }

    @Test
    public void a(){
        String a="a";
        String b="a";
        assertEquals(a,b);
    }

}
