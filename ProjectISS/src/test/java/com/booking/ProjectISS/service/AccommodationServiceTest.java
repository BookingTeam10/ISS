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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.util.*;
import java.util.stream.Stream;

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

    private static final Long VALID_ACCOMMODATION_ID = 1L;
    private static final Long INVALID_ACCOMMODATION_ID = 0L;

    @Test
    public void accommodationDoesnotExist(){

        when(accommodationRepository.findById(INVALID_ACCOMMODATION_ID)).thenReturn(Optional.empty());

        Accommodation accommodation=new Accommodation(INVALID_ACCOMMODATION_ID);

        String message=accommodationService.updateAccommodation(accommodation);

        verify(accommodationRepository).findById(INVALID_ACCOMMODATION_ID);
        verifyNoInteractions(reservationRepository);
        verifyNoInteractions(priceService);
        assertEquals("Doesn't exist this accommodation",message);
    }

    @Test
    public void changeReturnTrue(){     //dodati jos slucajeva i ostale atribute

        //List<Price> prices = Arrays.asList()      //obavezno ostali atributi

        List<Reservation> reservations = List.of();

        //List<Price> prices = Arrays.asList();

        List<Price> prices = List.of();

        Collection<Reservation> reservationsCollections = reservations;

        Accommodation accommodation=new Accommodation(VALID_ACCOMMODATION_ID,reservations,prices);

        when(accommodationRepository.findById(VALID_ACCOMMODATION_ID)).thenReturn(Optional.of(accommodation));

        when(reservationRepository.findByAccommodation(VALID_ACCOMMODATION_ID)).thenReturn(reservationsCollections);

        for(Price price : prices) {
            doNothing().when(priceService).update(price);
        }

        String message=accommodationService.updateAccommodation(accommodation);

        verify(accommodationRepository).findById(VALID_ACCOMMODATION_ID);
        verify(reservationRepository).findByAccommodation(VALID_ACCOMMODATION_ID);
        for(Price price : prices) {
            verify(priceService).update(price);
        }
        verify(accommodationRepository).save(accommodation);
        assertEquals("Successful edit",message);
    }

    @Test
    public void changeReturnFalseChange(){     //dodati jos slucajeva

        List<Reservation> reservations = Arrays.asList(new Reservation(1L,new Date(125, 0, 12),new Date(125, 0, 15)),new Reservation(2L,new Date(125,1,12),new Date(125,1,15)));

        List<Price> prices = Arrays.asList(new Price(1L,1000, new Date(125,1,1),new Date(126,1,1)),new Price(1L,1000, new Date(127,1,1),new Date(128,1,1)));

        Collection<Reservation> reservationsCollections = reservations;

        Accommodation accommodation=new Accommodation(VALID_ACCOMMODATION_ID,reservations,prices,1);

        Accommodation oldAccommodation=new Accommodation(VALID_ACCOMMODATION_ID,reservations,prices,2);

        when(accommodationRepository.findById(VALID_ACCOMMODATION_ID)).thenReturn(Optional.of(oldAccommodation));

        when(reservationRepository.findByAccommodation(VALID_ACCOMMODATION_ID)).thenReturn(reservationsCollections);

        String message=accommodationService.updateAccommodation(accommodation);

        verify(accommodationRepository).findById(VALID_ACCOMMODATION_ID);
        verify(reservationRepository).findByAccommodation(VALID_ACCOMMODATION_ID);
        verifyNoInteractions(priceService);
        verify(accommodationRepository).save(accommodation);
        assertEquals("Changed only cancelled deadline, due to reservations",message);
    }

    @Test
    public void changeReturnFalseNoChange(){     //dodati jos slucajeva

        List<Reservation> reservations = Arrays.asList(new Reservation(1L,new Date(125, 0, 12),new Date(125, 0, 15)),new Reservation(2L,new Date(125,1,12),new Date(125,1,15)));

        List<Price> prices = Arrays.asList(new Price(1L,1000, new Date(125,1,1),new Date(126,1,1)),new Price(1L,1000, new Date(127,1,1),new Date(128,1,1)));

        Collection<Reservation> reservationsCollections = reservations;

        Accommodation accommodation=new Accommodation(VALID_ACCOMMODATION_ID,reservations,prices,1);

        when(accommodationRepository.findById(VALID_ACCOMMODATION_ID)).thenReturn(Optional.of(accommodation));

        when(reservationRepository.findByAccommodation(VALID_ACCOMMODATION_ID)).thenReturn(reservationsCollections);

        String message=accommodationService.updateAccommodation(accommodation);

        verify(accommodationRepository).findById(VALID_ACCOMMODATION_ID);
        verify(reservationRepository).findByAccommodation(VALID_ACCOMMODATION_ID);
        verifyNoInteractions(priceService);
        assertEquals("You can only change the cancelled deadline, due to reservations",message);

    }

}
