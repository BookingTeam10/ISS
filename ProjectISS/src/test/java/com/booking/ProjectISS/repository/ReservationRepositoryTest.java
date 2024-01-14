package com.booking.ProjectISS.repository;

import com.booking.ProjectISS.model.reservations.Reservation;
import com.booking.ProjectISS.repository.reservations.IReservationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testng.annotations.DataProvider;

import static org.assertj.core.api.Assertions.assertThat;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@DataJpaTest
@ActiveProfiles("test")
public class ReservationRepositoryTest {

    private static final Long VALID_ACCOMMODATION_ID = 1L;

    private static final Long INVALID_ACCOMMODATION_ID = 999L;

    @Autowired
    private IReservationRepository reservationRepository;

//    @DataProvider(name = "findReservationByAccommodationId")
//    private Object[][] findReservationByAccommodationIdExamples() {
//
//        Collection<Reservation> collections1 = Arrays.asList(new Reservation(1),new Reservation(2),new Reservation(5));
//        Collection<Reservation> collections2 = Arrays.asList(new Reservation(3),new Reservation(4));
//        Collection<Reservation> collections3 = new ArrayList<Reservation>();
//
//        return new Object[][] {
//                { 1L, collections1 },
//                { 2L, collections2 },
//                { 999L , collections3 }
//        };
//    }
//    @Test(dataProvider = "findReservationByAccommodationId")
//    public void findReservationByAccommodationIdTest(Long accommodationId, Collection<Reservation> expectedReservations) {
//
//        Collection<Reservation> reservations = reservationRepository.findByAccommodation(accommodationId);
//
//        System.out.println(reservations);
//
//        System.out.println(expectedReservations);
//
//        assertThat(reservations).hasSize(expectedReservations.size());
//        //assertThat(reservations).extracting(Reservation::getId).containsExactlyElementsOf(expectedReservations.stream().map(Reservation::getId).collect(Collectors.toList()));
//    }

    static Stream<Arguments> findReservationByAccommodationIdExamples() {
        Collection<Reservation> collections1 = Arrays.asList(new Reservation(1), new Reservation(2), new Reservation(5));
        Collection<Reservation> collections2 = Arrays.asList(new Reservation(3), new Reservation(4));
        Collection<Reservation> collections3 = new ArrayList<>();

        return Stream.of(
                Arguments.of(1L, collections1),
                Arguments.of(2L, collections2),
                Arguments.of(999L, collections3)
        );
    }

    @ParameterizedTest
    @MethodSource("findReservationByAccommodationIdExamples")
    void findReservationByAccommodationIdTest(Long accommodationId, Collection<Reservation> expectedReservations) {

        Collection<Reservation> reservations = reservationRepository.findByAccommodation(accommodationId);

        assertThat(reservations).hasSize(expectedReservations.size());
        assertThat(reservations).extracting(Reservation::getId).containsExactlyInAnyOrderElementsOf(expectedReservations.stream().map(Reservation::getId).collect(Collectors.toList()));
    }

    @Test
    public void findReservationByAccommodationIdSizeTest() {
        Collection<Reservation> reservations = reservationRepository.findByAccommodation(VALID_ACCOMMODATION_ID);
        assertThat(reservations).isNotEmpty();
    }

    @Test
    public void findReservationByAccommodationIdEmptyTest() {
        Collection<Reservation> reservations = reservationRepository.findByAccommodation(INVALID_ACCOMMODATION_ID);
        assertThat(reservations).isEmpty();
    }

}
