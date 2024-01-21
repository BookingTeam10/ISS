package com.booking.ProjectISS.service;

import com.booking.ProjectISS.dto.reservations.ReservationDTO;
import com.booking.ProjectISS.enums.ReservationStatus;
import com.booking.ProjectISS.model.accomodations.Accommodation;
import com.booking.ProjectISS.model.accomodations.Price;
import com.booking.ProjectISS.model.reservations.Reservation;
import com.booking.ProjectISS.model.users.Guest;
import com.booking.ProjectISS.repository.reservations.IReservationRepository;
import com.booking.ProjectISS.service.accommodation.AccommodationService;
import com.booking.ProjectISS.service.reservations.ReservationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;
import java.util.stream.Stream;

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

    private static final Long INVALID_ACCOMMODATION_ID = 0L;

    static Stream<Arguments> overlapping() {    //4 slucaja

        Collection<Reservation> reservations= List.of(new Reservation(1L, new Date(126, 0, 1), new Date(126, 10, 2), new Accommodation(VALID_ACCOMMODATION_ID)));
        Reservation reservation=new Reservation(2L,new Date(127,0,1),new Date(127,0,2),new Accommodation(VALID_ACCOMMODATION_ID));
        Reservation reservation1=new Reservation(2L,new Date(129,0,15),new Date(129,0,2),new Accommodation(VALID_ACCOMMODATION_ID));
        Reservation reservation2=new Reservation(2L,new Date(125,0,1),new Date(125,10,1),new Accommodation(VALID_ACCOMMODATION_ID));
        Reservation reservation3=new Reservation(2L,new Date(125,0,1),new Date(129,0,2),new Accommodation(VALID_ACCOMMODATION_ID));


        return Stream.of(
                Arguments.of(reservation,reservations, false),
                Arguments.of(reservation1,reservations, false),
                Arguments.of(reservation2,reservations, false),
                Arguments.of(reservation3,reservations, true)
        );
    }

    @ParameterizedTest
    @MethodSource("overlapping")
    public void hasOverlapping(Reservation reservation,Collection<Reservation> reservations,boolean expectedResult){

        when(reservationRepository.findAllByAccommodationId(reservation.getAccommodation().getId(), ReservationStatus.CANCELLED,ReservationStatus.REJECTED)).thenReturn(reservations);

        boolean result=reservationService.hasOverlappingRequests(reservation);

        verify(reservationRepository).findAllByAccommodationId(reservation.getAccommodation().getId(), ReservationStatus.CANCELLED,ReservationStatus.REJECTED);
        assertEquals(expectedResult,result);
    }

    static Stream<Arguments> noAutomatic() {

        Reservation reservation=new Reservation(100L,0,ReservationStatus.WAITING,new Date(127,0,1),new Date(127,0,2),5,new Accommodation(VALID_ACCOMMODATION_ID,false),new Guest(1L),List.of());

        return Stream.of(
                Arguments.of(reservation)
        );
    }

    @ParameterizedTest
    @MethodSource("noAutomatic")
    public void noAutomaticConfirmation(Reservation reservation) throws Exception {

        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);
        ReservationDTO reservationDTO=reservationService.create(reservation);

        verify(reservationRepository).save(reservation);
        assertEquals(reservation.getStartDate().getDate(),reservationDTO.getStartDate().getDate());
        assertEquals(reservation.getEndDate().getDate(),reservationDTO.getEndDate().getDate());
        assertEquals(reservation.getAccommodation().getId(),reservationDTO.getAccommodation().getId());
    }

    static Stream<Arguments> automatic() {

        Reservation reservation1=new Reservation(100L,new Date(127,0,1),new Date(127,0,2),new Accommodation(VALID_ACCOMMODATION_ID,true));
        List<Reservation>reservations1 = List.of();

        Reservation reservation2=new Reservation(100L,new Date(127,0,1),new Date(127,0,2),new Accommodation(VALID_ACCOMMODATION_ID,true));
        List<Reservation>reservations2 = Arrays.asList(new Reservation(1L,ReservationStatus.WAITING,new Date(127,0,1),new Date(127,0,2),new Accommodation(VALID_ACCOMMODATION_ID)),new Reservation(2L,ReservationStatus.ACCEPTED,new Date(127,0,1),new Date(127,0,2),new Accommodation(VALID_ACCOMMODATION_ID)),new Reservation(3L,ReservationStatus.WAITING,new Date(127,0,1),new Date(127,0,2),new Accommodation(INVALID_ACCOMMODATION_ID)),new Reservation(4L,ReservationStatus.WAITING,new Date(127,0,1),new Date(127,0,2),new Accommodation(VALID_ACCOMMODATION_ID)),new Reservation(5L,ReservationStatus.WAITING,new Date(127,0,1),new Date(127,0,2),new Accommodation(VALID_ACCOMMODATION_ID)),new Reservation(6L,ReservationStatus.WAITING,new Date(127,0,1),new Date(127,0,2),new Accommodation(VALID_ACCOMMODATION_ID)),new Reservation(7L,ReservationStatus.WAITING,new Date(127,0,1),new Date(127,0,2),new Accommodation(VALID_ACCOMMODATION_ID)));


        return Stream.of(
                Arguments.of(reservation1,reservations1),
                Arguments.of(reservation2,reservations2)
        );
    }

    @ParameterizedTest
    @MethodSource("automatic")
    public void automaticConfirmation(Reservation reservation,List<Reservation> reservations) throws Exception {

        when(reservationRepository.findAll()).thenReturn(reservations);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);
        ReservationDTO reservationDTO=reservationService.create(reservation);

        verify(reservationRepository).findAll();
        verify(reservationRepository).save(reservation);
        assertEquals(reservation.getStartDate().getDate(),reservationDTO.getStartDate().getDate());
        assertEquals(reservation.getEndDate().getDate(),reservationDTO.getEndDate().getDate());
        assertEquals(reservation.getAccommodation().getId(),reservationDTO.getAccommodation().getId());
        assertEquals(ReservationStatus.ACCEPTED,reservationDTO.getStatus());
    }
}
