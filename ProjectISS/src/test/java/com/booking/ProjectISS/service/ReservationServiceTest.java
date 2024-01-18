package com.booking.ProjectISS.service;

import com.booking.ProjectISS.dto.reservations.ReservationDTO;
import com.booking.ProjectISS.enums.ReservationStatus;
import com.booking.ProjectISS.model.accomodations.Accommodation;
import com.booking.ProjectISS.model.accomodations.Price;
import com.booking.ProjectISS.model.reservations.Reservation;
import com.booking.ProjectISS.repository.reservations.IReservationRepository;
import com.booking.ProjectISS.service.accommodation.AccommodationService;
import com.booking.ProjectISS.service.reservations.ReservationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoInteractions;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test3")
public class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @MockBean
    private IReservationRepository reservationRepository;

    private static final Long VALID_ACCOMMODATION_ID = 1L;

    @Test
    public void prviSlucajhasOverlapping(){ //diskutovati svaki slucaj

        Collection<Reservation> reservations= Arrays.asList(new Reservation(1L,new Date(126,0,1),new Date(126,0,2),new Accommodation(VALID_ACCOMMODATION_ID))); //dodati reservationStatus

        Reservation reservation=new Reservation(2L,new Date(127,0,1),new Date(127,0,2),new Accommodation(VALID_ACCOMMODATION_ID));

        when(reservationRepository.findAllByAccommodationId(VALID_ACCOMMODATION_ID, ReservationStatus.CANCELLED,ReservationStatus.REJECTED)).thenReturn(reservations);

        boolean result=reservationService.hasOverlappingRequests(reservation);

        verify(reservationRepository).findAllByAccommodationId(VALID_ACCOMMODATION_ID, ReservationStatus.CANCELLED,ReservationStatus.REJECTED);
        assertEquals(false,result);
    }

    @Test       //pokusati izbaciti exception
    public void prviSlucajcreateReservationNijeAutomatskiPrih() throws Exception { //diskutovati svaki slucaj, u ovom slucaju moram u accommodationu imati boolean da li je automatski prihvaceno

        Reservation reservation=new Reservation(1L,new Date(127,0,1),new Date(127,0,2),new Accommodation(VALID_ACCOMMODATION_ID));

        ReservationDTO reservationDTO=reservationService.create(reservation);

        verify(reservationRepository).save(reservation);
        //assertEquals(reservation.getStartDate().getDate(),reservationDTO.getStartDate().getDate());
        //assertEquals(reservation.getEndDate().getDate(),reservationDTO.getEndDate().getDate());
        //assertEquals(reservation.getAccommodation().getId(),reservationDTO.getAccommodation().getId());
    }

    @Test       //pokusati izbaciti exception
    public void prviSlucajcreateReservationAutomatskiPrih() throws Exception { //diskutovati svaki slucaj, u ovom slucaju moram u accommodationu imati boolean da li je automatski prihvaceno i rezervacija mora imati status

        Reservation reservation=new Reservation(1L,new Date(127,0,1),new Date(127,0,2),new Accommodation(VALID_ACCOMMODATION_ID));

        List<Reservation>reservations = List.of();

        when(reservationRepository.findAll()).thenReturn(reservations);

        ReservationDTO reservationDTO=reservationService.create(reservation);

        verify(reservationRepository).findAll();

        verify(reservationRepository).save(reservation);
        assertEquals(reservation.getStartDate().getDate(),reservationDTO.getStartDate().getDate());
        assertEquals(reservation.getEndDate().getDate(),reservationDTO.getEndDate().getDate());
        assertEquals(reservation.getAccommodation().getId(),reservationDTO.getAccommodation().getId());
    }
}
