package com.booking.ProjectISS.controller;

import com.booking.ProjectISS.dto.reservations.ReservationDTO;
import com.booking.ProjectISS.dto.users.LoginDTO;
import com.booking.ProjectISS.model.accomodations.Accommodation;
import com.booking.ProjectISS.model.accomodations.Price;
import com.booking.ProjectISS.model.reservations.Reservation;
import com.booking.ProjectISS.model.users.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test3")
public class ReservationControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private String jwt;

    @BeforeEach
    public void login(){
        HttpHeaders headers=new HttpHeaders();
        LoginDTO loginDTO=new LoginDTO("aleksa@gmail.com","abc");
        HttpEntity<LoginDTO> requestEntity = new HttpEntity<LoginDTO>(loginDTO);
        Token responseEntity = restTemplate.exchange(
                "/api/users/login",
                HttpMethod.POST,
                requestEntity,
                Token.class).getBody();

        this.jwt=responseEntity.getJwt();
    }

    static Stream<Arguments> reservationsCreated() {    //dodati u accommodationu ono automatski prihvaceno
        Reservation reservation1=new Reservation(1L,new Date(126,0,1),new Date(126,0,2),new Accommodation(1L));
        Reservation reservation2=new Reservation(1L,new Date(126,0,1),new Date(126,0,2),new Accommodation(3L));
        return Stream.of(
                Arguments.of(reservation1),
                Arguments.of(reservation2)
        );
    }

    @ParameterizedTest
    @MethodSource("reservationsCreated")
    @DisplayName("Should create new reservation because doesnt exist overlapping termins")
    public void createNewReservation(Reservation reservation) {

        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization","Bearer"+jwt);
        HttpEntity<Reservation> requestEntity = new HttpEntity<Reservation>(reservation,headers);
        ResponseEntity<ReservationDTO> responseEntity = restTemplate.exchange(
                "/api/reservations",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<ReservationDTO>() {
                });

        ReservationDTO reservationDTO = responseEntity.getBody();

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(reservation.getStartDate().getDate(),reservationDTO.getStartDate().getDate());
        assertEquals(reservation.getEndDate().getDate(),reservationDTO.getEndDate().getDate());
        assertEquals(reservation.getAccommodation().getId(),reservationDTO.getAccommodation().getId());
    }

    static Stream<Arguments> reservationsNotCreated() {
        Reservation reservation1=new Reservation(1L,new Date(125,0,1),new Date(125,0,2),new Accommodation(1L));
        return Stream.of(
                Arguments.of(reservation1)
        );
    }

    @ParameterizedTest
    @MethodSource("reservationsNotCreated")
    @DisplayName("this reservation has overlapping and doesnt create")
    public void newReservationOverlapping(Reservation reservation) {
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization","Bearer"+jwt);
        HttpEntity<Reservation> requestEntity = new HttpEntity<Reservation>(reservation,headers);
        ResponseEntity<ReservationDTO> responseEntity = restTemplate.exchange(
                "/api/reservations",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<ReservationDTO>() {
                });

        ReservationDTO reservationDTO = responseEntity.getBody();

        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertNull(reservationDTO);
    }
}
