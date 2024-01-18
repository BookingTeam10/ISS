package com.booking.ProjectISS.repository.s3;

import com.booking.ProjectISS.enums.ReservationStatus;
import com.booking.ProjectISS.model.reservations.Reservation;
import com.booking.ProjectISS.model.users.User;
import com.booking.ProjectISS.repository.reservations.IReservationRepository;
import com.booking.ProjectISS.repository.users.user.IUserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.*;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DataJpaTest
@ActiveProfiles("test3")
public class ReservationRepositoryTest {

    private static final Long VALID_ACCOMMODATION_ID = 1L;

    private static final Long INVALID_ACCOMMODATION_ID = 999L;

    @Autowired
    private IReservationRepository reservationRepository;


    static Stream<Arguments> findReservationByAccIdAndStatus() {
        Collection<Reservation> collections1 = List.of(new Reservation(1));
        Collection<Reservation> collections2 = Arrays.asList(new Reservation(3), new Reservation(4));
        Collection<Reservation> collections3 = new ArrayList<>();

        return Stream.of(
                Arguments.of(1L,ReservationStatus.ACCEPTED,ReservationStatus.WAITING, collections1),
                Arguments.of(999L,ReservationStatus.CANCELLED,ReservationStatus.REJECTED, collections3)
        );
    }

    @ParameterizedTest
    @MethodSource("findReservationByAccIdAndStatus")
    void findReservationByAccommodationIdTest(Long accommodationId,ReservationStatus status1,ReservationStatus status2, Collection<Reservation> expectedReservations) {

        Collection<Reservation> reservations = reservationRepository.findAllByAccommodationId(accommodationId,status1,status2);

        Assertions.assertThat(reservations).hasSize(expectedReservations.size());
        Assertions.assertThat(reservations).extracting(Reservation::getId).containsExactlyInAnyOrder(expectedReservations.stream().map(Reservation::getId).toArray(Long[]::new));
    }


    @Test
    public void findReservationByAccommodationIdAndNotStatusSizeTest() {
        Collection<Reservation> reservations = reservationRepository.findAllByAccommodationId(VALID_ACCOMMODATION_ID, ReservationStatus.CANCELLED,ReservationStatus.REJECTED);
        Assertions.assertThat(reservations).isNotEmpty();
    }

    @Test
    public void findReservationByAccommodationIdAndNotStatusEmptyTest() {   //2 slucaja, za invalid i za valid al nema je
        Collection<Reservation> reservations = reservationRepository.findAllByAccommodationId(INVALID_ACCOMMODATION_ID,ReservationStatus.REJECTED,ReservationStatus.ACCEPTED);
        Assertions.assertThat(reservations).isEmpty();
    }
}
