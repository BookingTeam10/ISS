package com.booking.ProjectISS.repository.s2;

import com.booking.ProjectISS.model.reservations.Reservation;
import com.booking.ProjectISS.repository.reservations.IReservationRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test2")
public class ReservationRepositoryTest {

    @Autowired
    private IReservationRepository reservationRepository;

    @Test
    public void testFindOne(){
        Reservation validReservation = reservationRepository.findById(1L).get();
        Reservation invalidReservation = reservationRepository.findById(999L).orElse(null);

        assertNotNull(validReservation);
        assertEquals(1L, validReservation.getId());
        assertNull(invalidReservation);
    }

    private static Stream<Arguments> provideReservationData() {
        Collection<Reservation> reservations = Arrays.asList(new Reservation(1L), new Reservation(2L)
                , new Reservation(3L), new Reservation(4L), new Reservation(5L), new Reservation(6L), new Reservation(7L));
        return Stream.of(
                Arguments.of(reservations)

        );
    }

    @ParameterizedTest
    @MethodSource("provideReservationData")
    public void testFindAll(List<Reservation> expectedReservations){
        List<Reservation> allReservations = reservationRepository.findAll();

        List<Long> actualIds = allReservations.stream().map(Reservation::getId).collect(Collectors.toList());

        List<Long> expectedIds = expectedReservations.stream().map(Reservation::getId).collect(Collectors.toList());

        Assertions.assertThat(allReservations).hasSize(expectedReservations.size());
        Assertions.assertThat(actualIds).containsExactlyElementsOf(expectedIds);

    }

    @Test
    public void testUpdateReservation() {

        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        calendar.add(Calendar.DAY_OF_MONTH, 2);
        Date endDate = calendar.getTime();

        calendar.add(Calendar.DAY_OF_MONTH, 5);
        Date newDate = calendar.getTime();

        Reservation initialReservation = new Reservation(2L, currentDate, endDate);

        Long reservationId = initialReservation.getId();

        Reservation updatedReservation = new Reservation(2L, currentDate, newDate);

        reservationRepository.save(updatedReservation);

        Optional<Reservation> updatedFromDb = reservationRepository.findById(reservationId);

        Assertions.assertThat(updatedFromDb).isPresent();
        Assertions.assertThat(updatedFromDb.get().getId()).isEqualTo(updatedReservation.getId());
        Assertions.assertThat(updatedFromDb.get().getStartDate()).isEqualTo(updatedReservation.getStartDate());
        Assertions.assertThat(updatedFromDb.get().getEndDate()).isEqualTo(updatedReservation.getEndDate());
    }

}
