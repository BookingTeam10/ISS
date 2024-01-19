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

    static Stream<Arguments> invalidIdAccommodation() {

        return Stream.of(
                Arguments.of(0L)
        );
    }

    @ParameterizedTest
    @MethodSource("invalidIdAccommodation")
    public void accommodationDoesnotExist(Long INVALID_ACCOMMODATION_ID){

        when(accommodationRepository.findById(INVALID_ACCOMMODATION_ID)).thenReturn(Optional.empty());

        Accommodation accommodation=new Accommodation(INVALID_ACCOMMODATION_ID);

        String message=accommodationService.updateAccommodation(accommodation);

        verify(accommodationRepository).findById(INVALID_ACCOMMODATION_ID);
        verifyNoInteractions(reservationRepository);
        verifyNoInteractions(priceService);
        assertEquals("Doesn't exist this accommodation",message);
    }


    static Stream<Arguments> editAccommodationSuccessful() {

        Long VALID_ACCOMMODATION_ID=1L;

        return Stream.of(
                Arguments.of(VALID_ACCOMMODATION_ID,new Accommodation(VALID_ACCOMMODATION_ID),new Accommodation(VALID_ACCOMMODATION_ID,5, Arrays.asList(new Price(1L,1000, new Date(125,1,1),new Date(126,1,1)),new Price(2L,1000, new Date(127,1,1),new Date(128,1,1))),100,200,100),new ArrayList<Reservation>(),"Successful edit")
        );
    }

    @ParameterizedTest
    @MethodSource("editAccommodationSuccessful")
    public void changeReturnTrue(Long VALID_ACCOMMODATION_ID,Accommodation oldAccommodation,Accommodation updatedAccommodation,Collection<Reservation>reservations,String exceptedMessage){

        when(accommodationRepository.findById(VALID_ACCOMMODATION_ID)).thenReturn(Optional.of(oldAccommodation));

        when(reservationRepository.findByAccommodation(VALID_ACCOMMODATION_ID)).thenReturn(reservations);

        for(Price price : updatedAccommodation.getPrices()) {
            when(priceService.update(price)).thenReturn(price);
        }

        String message=accommodationService.updateAccommodation(updatedAccommodation);

        verify(accommodationRepository).findById(VALID_ACCOMMODATION_ID);
        verify(reservationRepository).findByAccommodation(VALID_ACCOMMODATION_ID);
        for(Price price : updatedAccommodation.getPrices()) {
            verify(priceService).update(price);
        }
        verify(accommodationRepository).save(oldAccommodation);
        assertEquals(exceptedMessage,message);
    }

    static Stream<Arguments> editAccommodationNothing() {

        Long VALID_ACCOMMODATION_ID=1L;

        Collection<Reservation> reservations1 = Arrays.asList(new Reservation(1L,new Date(125, 0, 12),new Date(125, 0, 15)),new Reservation(2L,new Date(125,1,12),new Date(125,1,15)));

        List<Price> prices = Arrays.asList(new Price(1L,1000, new Date(125,1,1),new Date(126,1,1)),new Price(1L,1000, new Date(127,1,1),new Date(128,1,1)));

        Accommodation oldAccommodation1=new Accommodation(VALID_ACCOMMODATION_ID,3);

        Accommodation updatedAccommodation1=new Accommodation(VALID_ACCOMMODATION_ID,3,prices);


        return Stream.of(
                Arguments.of(VALID_ACCOMMODATION_ID,oldAccommodation1,updatedAccommodation1,reservations1,"You can only change the cancelled deadline, due to reservations")
        );
    }

    @ParameterizedTest
    @MethodSource("editAccommodationNothing")
    public void changeReturnFalseNoChange(Long VALID_ACCOMMODATION_ID,Accommodation oldAccommodation,Accommodation updatedAccommodation,Collection<Reservation> reservations, String exceptedMessage){

        when(accommodationRepository.findById(VALID_ACCOMMODATION_ID)).thenReturn(Optional.of(oldAccommodation));

        when(reservationRepository.findByAccommodation(VALID_ACCOMMODATION_ID)).thenReturn(reservations);

        String message=accommodationService.updateAccommodation(updatedAccommodation);

        verify(accommodationRepository).findById(VALID_ACCOMMODATION_ID);
        verify(reservationRepository).findByAccommodation(VALID_ACCOMMODATION_ID);
        verifyNoInteractions(priceService);
        assertEquals(exceptedMessage,message);
    }

    static Stream<Arguments> editAccommodationCancelledDay() {

        Long VALID_ACCOMMODATION_ID=1L;

        Collection<Reservation> reservations1 = Arrays.asList(new Reservation(1L,new Date(125, 0, 12),new Date(125, 0, 15)),new Reservation(2L,new Date(125,1,12),new Date(125,1,15)));

        List<Price> prices = Arrays.asList(new Price(1L,1000, new Date(125,1,1),new Date(126,1,1)),new Price(1L,1000, new Date(127,1,1),new Date(128,1,1)));

        Accommodation oldAccommodation1=new Accommodation(VALID_ACCOMMODATION_ID,3);

        Accommodation updatedAccommodation1=new Accommodation(VALID_ACCOMMODATION_ID,2,prices);


        return Stream.of(
                Arguments.of(VALID_ACCOMMODATION_ID,oldAccommodation1,updatedAccommodation1,reservations1,"Changed only cancelled deadline, due to reservations")
        );
    }

    @ParameterizedTest
    @MethodSource("editAccommodationCancelledDay")
    public void changeReturnFalseChange(Long VALID_ACCOMMODATION_ID, Accommodation oldAccommodation,Accommodation updatedAccommodation, Collection<Reservation> reservations, String exceptedMessage){     //dodati jos slucajeva


        when(accommodationRepository.findById(VALID_ACCOMMODATION_ID)).thenReturn(Optional.of(oldAccommodation));

        when(reservationRepository.findByAccommodation(VALID_ACCOMMODATION_ID)).thenReturn(reservations);

        String message=accommodationService.updateAccommodation(updatedAccommodation);

        verify(accommodationRepository).findById(VALID_ACCOMMODATION_ID);
        verify(reservationRepository).findByAccommodation(VALID_ACCOMMODATION_ID);
        verifyNoInteractions(priceService);
        verify(accommodationRepository).save(oldAccommodation);
        assertEquals(exceptedMessage,message);

    }

}
