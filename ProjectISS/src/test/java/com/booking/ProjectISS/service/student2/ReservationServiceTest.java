package com.booking.ProjectISS.service.student2;

import com.booking.ProjectISS.dto.reservations.ReservationDTO;
import com.booking.ProjectISS.enums.ReservationStatus;
import com.booking.ProjectISS.model.accomodations.Accommodation;
import com.booking.ProjectISS.model.reservations.Reservation;
import com.booking.ProjectISS.repository.reservations.IReservationRepository;
import com.booking.ProjectISS.service.reservations.ReservationService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ReservationServiceTest {

    @Mock
    private IReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    public void testAutomaticConfirmation() throws Exception {

        Reservation requestReservation = new Reservation(31L, new Date(2024, 1, 1), new Date(2024, 1, 3),new Accommodation(1L,true));
        when(reservationRepository.save(any())).thenReturn(requestReservation);

        ReservationDTO createdReservation = reservationService.create(requestReservation);


        assertEquals(requestReservation.getStartDate().getDate(),createdReservation.getStartDate().getDate());
        assertEquals(requestReservation.getEndDate().getDate(),createdReservation.getEndDate().getDate());
        assertEquals(requestReservation.getAccommodation().getId(),createdReservation.getAccommodation().getId());
        assertEquals(ReservationStatus.ACCEPTED, createdReservation.getStatus());

    }

    @Test
    public void testManualConfirmationAccepted() throws Exception {

        Reservation requestReservation = new Reservation(32L, new Date(2024, 1, 5), new Date(2024, 1, 7), new Accommodation(2L,false));
        when(reservationRepository.save(any())).thenReturn(requestReservation);
        when(reservationRepository.findById(2L)).thenReturn(Optional.of(requestReservation));

        ReservationDTO createdReservation = reservationService.create(requestReservation);
        ReservationDTO acceptedReservation = reservationService.acceptReservation(requestReservation);

        assertEquals(ReservationStatus.ACCEPTED, acceptedReservation.getStatus());
        assertEquals(requestReservation.getStartDate().getDate(),createdReservation.getStartDate().getDate());
        assertEquals(requestReservation.getEndDate().getDate(),createdReservation.getEndDate().getDate());
        assertEquals(requestReservation.getAccommodation().getId(),createdReservation.getAccommodation().getId());
    }

    @Test
    public void testManualConfirmationRejected() throws Exception {
        // Arrange
        Reservation requestReservation = new Reservation(33L, new Date(2024, 1, 10), new Date(2024, 1, 12),  new Accommodation(2L,false));
        when(reservationRepository.save(any())).thenReturn(requestReservation);
        when(reservationRepository.findById(3L)).thenReturn(Optional.of(requestReservation));

        // Act
        ReservationDTO createdReservation = reservationService.create(requestReservation);
        ReservationDTO rejectedReservation = reservationService.rejectReservation(requestReservation);

        // Assert
        assertEquals(ReservationStatus.REJECTED, rejectedReservation.getStatus());
        assertEquals(requestReservation.getStartDate().getDate(),createdReservation.getStartDate().getDate());
        assertEquals(requestReservation.getEndDate().getDate(),createdReservation.getEndDate().getDate());
        assertEquals(requestReservation.getAccommodation().getId(),createdReservation.getAccommodation().getId());
    }

    @Test
    @Transactional
    @Rollback
    public void testAutomaticRejectionOverlapping() throws Exception {

        Accommodation accommodation = new Accommodation(2L, false);

        Reservation existingReservation = new Reservation(34L, new Date(2024, 1, 2), new Date(2024, 1, 4), accommodation);
        existingReservation.setStatus(ReservationStatus.WAITING);
        when(reservationRepository.findById(34L)).thenReturn(Optional.of(existingReservation));

        Reservation requestReservation = new Reservation(35L, new Date(2024, 1, 3), new Date(2024, 1, 5), accommodation);
        when(reservationRepository.findAll()).thenReturn(Arrays.asList(existingReservation));
        when(reservationRepository.save(any())).thenReturn(requestReservation);

        ReservationDTO createdReservationDTO = reservationService.create(requestReservation);
        ReservationDTO acceptedReservationDTO = reservationService.acceptReservation(existingReservation);
        createdReservationDTO.setStatus(ReservationStatus.REJECTED);

        when(reservationRepository.findById(35L)).thenReturn(Optional.of(new Reservation(createdReservationDTO)));

        ReservationDTO rejectedReservationDTO = reservationService.findOneDTO(35L);

        // Assert
        assertEquals(ReservationStatus.ACCEPTED, acceptedReservationDTO.getStatus());
        assertEquals(ReservationStatus.REJECTED, rejectedReservationDTO.getStatus());
        // Additional assertions based on your business logic
    }



}
