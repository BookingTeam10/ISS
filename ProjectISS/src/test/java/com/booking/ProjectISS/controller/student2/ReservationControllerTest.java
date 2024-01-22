package com.booking.ProjectISS.controller.student2;

import com.booking.ProjectISS.dto.reservations.ReservationDTO;
import com.booking.ProjectISS.dto.users.LoginDTO;
import com.booking.ProjectISS.enums.ReservationStatus;
import com.booking.ProjectISS.model.accomodations.Accommodation;
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

import java.util.Date;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test2")
public class ReservationControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private String jwt;

    @BeforeEach
    public void login(){
        HttpHeaders headers=new HttpHeaders();
        LoginDTO loginDTO=new LoginDTO("popovic.sv4.2021@uns.ac.rs","abc");
        HttpEntity<LoginDTO> requestEntity = new HttpEntity<LoginDTO>(loginDTO);
        Token responseEntity = restTemplate.exchange(
                "/api/users/login",
                HttpMethod.POST,
                requestEntity,
                Token.class).getBody();

        this.jwt=responseEntity.getJwt();
    }

    static Stream<Arguments> reservationsCreatedAutomaticConf() {
        Reservation reservation1=new Reservation(1L,new Date(126,0,1),new Date(126,0,2),new Accommodation(1L,true));
              return Stream.of(
                Arguments.of(reservation1)
        );
    }

    static Stream<Arguments> reservationsCreatedNoAutomaticConf() {
        Reservation reservation=new Reservation(15L,new Date(136,0,1),new Date(136,0,2),new Accommodation(2L,false));
        return Stream.of(
                Arguments.of(reservation)
        );
    }


    @ParameterizedTest
    @MethodSource("reservationsCreatedAutomaticConf")
    @DisplayName("Create reservation with automatic confirmation and no overlapping dates")
    public void createReservationAutomaticConf(Reservation reservation){
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
        assertEquals( ReservationStatus.ACCEPTED,reservationDTO.getStatus());

    }

    @ParameterizedTest
    @MethodSource("reservationsCreatedNoAutomaticConf")
    @DisplayName("Create reservation with no automatic confirmation and no overlapping dates")
    public void createReservationNoAutomaticConf(Reservation reservation){
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
        assertEquals(ReservationStatus.WAITING, reservationDTO.getStatus());


    }

    @Test
    @DisplayName("Get reservation with waiting status and accept it with no overlapping")
    public void acceptReservationNoOverlapping(){
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization","Bearer"+jwt);
        HttpEntity<Reservation> requestEntity = new HttpEntity<Reservation>(new Reservation(),headers);
        HttpEntity<Reservation> requestGetEntity = new HttpEntity<Reservation>(headers);

        ResponseEntity<ReservationDTO> responseGetEntity = restTemplate.exchange(
                "/api/reservations/7",
                HttpMethod.GET,
                requestGetEntity,
                new ParameterizedTypeReference<ReservationDTO>() {
                });

        ReservationDTO reservationBeforeAccept = responseGetEntity.getBody();
        assertEquals(HttpStatus.OK, responseGetEntity.getStatusCode());
        assertEquals(ReservationStatus.WAITING, reservationBeforeAccept.getStatus());


        ResponseEntity<ReservationDTO> responseEntity = restTemplate.exchange(
                "/api/reservations/accept/7",
                HttpMethod.PUT,
                requestEntity,
                new ParameterizedTypeReference<ReservationDTO>() {
                });


        ReservationDTO acceptedReservation = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(reservationBeforeAccept.getStartDate().getDate(),acceptedReservation.getStartDate().getDate());
        assertEquals(reservationBeforeAccept.getEndDate().getDate(),acceptedReservation.getEndDate().getDate());
        assertEquals(reservationBeforeAccept.getAccommodation().getId(),acceptedReservation.getAccommodation().getId());
        assertEquals(ReservationStatus.ACCEPTED, acceptedReservation.getStatus());
    }

    @Test
    @DisplayName("Get reservation with waiting status and reject it")
    public void rejectReservationNoOverlapping(){
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization","Bearer"+jwt);
        HttpEntity<Reservation> requestEntity = new HttpEntity<Reservation>(new Reservation(),headers);
        HttpEntity<Reservation> requestGetEntity = new HttpEntity<Reservation>(headers);

        ResponseEntity<ReservationDTO> responseGetEntity = restTemplate.exchange(
                "/api/reservations/6",
                HttpMethod.GET,
                requestGetEntity,
                new ParameterizedTypeReference<ReservationDTO>() {
                });

        ReservationDTO reservationBeforeAccept = responseGetEntity.getBody();
        assertEquals(HttpStatus.OK, responseGetEntity.getStatusCode());
        assertEquals(ReservationStatus.WAITING, reservationBeforeAccept.getStatus());


        ResponseEntity<ReservationDTO> responseEntity = restTemplate.exchange(
                "/api/reservations/cancel/6?canceledByHost=true",
                HttpMethod.PUT,
                requestEntity,
                new ParameterizedTypeReference<ReservationDTO>() {
                });


        ReservationDTO rejectedReservation = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(reservationBeforeAccept.getStartDate().getDate(),rejectedReservation.getStartDate().getDate());
        assertEquals(reservationBeforeAccept.getEndDate().getDate(),rejectedReservation.getEndDate().getDate());
        assertEquals(reservationBeforeAccept.getAccommodation().getId(),rejectedReservation.getAccommodation().getId());
        assertEquals(ReservationStatus.REJECTED, rejectedReservation.getStatus());
    }

    static Stream<Arguments> reservationsCreatedNoAutomaticConfOverlapping() {
        Reservation reservation1=new Reservation(20L,new Date(145,0,1),new Date(145,0,2),new Accommodation(1L,false));
        Reservation reservation2=new Reservation(21L,new Date(145,0,1),new Date(145,0,3),new Accommodation(1L,false));
        return Stream.of(
                Arguments.of(reservation1, reservation2)
        );
    }

    @ParameterizedTest
    @MethodSource("reservationsCreatedNoAutomaticConfOverlapping")
    @DisplayName("creating 2 overlapping reservations and accepting one")
    public void createReservationAutomaticConfirmationWithOverlapping(Reservation reservation1, Reservation reservation2){
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization","Bearer"+jwt);

        //CREATE RESERVATION1
        HttpEntity<Reservation> requestEntity = new HttpEntity<Reservation>(reservation1,headers);
        ResponseEntity<ReservationDTO> responseEntity = restTemplate.exchange(
                "/api/reservations",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<ReservationDTO>() {
                });

        ReservationDTO reservation1DTO = responseEntity.getBody();

        //CREATE RESERVATION2
        HttpEntity<Reservation> requestEntity2 = new HttpEntity<Reservation>(reservation2,headers);
        ResponseEntity<ReservationDTO> responseEntity2 = restTemplate.exchange(
                "/api/reservations",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<ReservationDTO>() {
                });

        ReservationDTO reservation2DTO = responseEntity2.getBody();


        //ACCEPT RESERVATION1
        ResponseEntity<ReservationDTO> responseAcceptEntity = restTemplate.exchange(
                "/api/reservations/accept/" + reservation1DTO.getId(),
                HttpMethod.PUT,
                requestEntity,
                new ParameterizedTypeReference<ReservationDTO>() {
                });




        HttpEntity<Reservation> requestGetEntity = new HttpEntity<Reservation>(headers);
        ResponseEntity<ReservationDTO> responseGetEntity = restTemplate.exchange(
                "/api/reservations/" + reservation2DTO.getId(),
                HttpMethod.GET,
                requestGetEntity,
                new ParameterizedTypeReference<ReservationDTO>() {
                });

        reservation2DTO = responseGetEntity.getBody();
        ReservationDTO acceptedReservation = responseAcceptEntity.getBody();
        assertEquals(HttpStatus.OK, responseAcceptEntity.getStatusCode());
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(HttpStatus.CREATED, responseEntity2.getStatusCode());
        assertEquals(HttpStatus.OK, responseGetEntity.getStatusCode());
        assertEquals(ReservationStatus.ACCEPTED, acceptedReservation.getStatus());
        assertEquals(ReservationStatus.REJECTED, reservation2DTO.getStatus());

    }


}
