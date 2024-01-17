package com.booking.ProjectISS.controller;

import com.booking.ProjectISS.enums.AccommodationStatus;
import com.booking.ProjectISS.enums.TypeAccommodation;
import com.booking.ProjectISS.model.accomodations.Accommodation;
import com.booking.ProjectISS.model.accomodations.Amenity;
import com.booking.ProjectISS.model.accomodations.Price;
import com.booking.ProjectISS.model.accomodations.TakenDate;
import com.booking.ProjectISS.model.reservations.Reservation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AccommodationControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;


    static Stream<Arguments> accommodationsExceptedMessage() {
        Accommodation accommodation1=new Accommodation(0L);
        Accommodation accommodation2=new Accommodation(1L,5, Arrays.asList(new Price(1L,1000, new Date(125,1,1),new Date(126,1,1)),new Price(2L,1000, new Date(127,1,1),new Date(128,1,1))),100,200,100);
        Accommodation accommodation3=new Accommodation(2L,10, List.of(new Price(1L, 1000, new Date(125, 0, 5), new Date(125, 0, 20))));
        Accommodation accommodation4=new Accommodation(2L,6, List.of(new Price(1L, 1000, new Date(125, 0, 5), new Date(125, 0, 20))));
        return Stream.of(
                Arguments.of(accommodation1, "Doesn't exist this accommodation"),
                Arguments.of(accommodation2, "Successful edit"),
                Arguments.of(accommodation3, "You can only change the cancelled deadline, due to reservations"),
                Arguments.of(accommodation4, "Changed only cancelled deadline, due to reservations")
        );
    }

    @ParameterizedTest
    @MethodSource("accommodationsExceptedMessage")
    @DisplayName("Should update accommodation if accommodation exist or accommodation does not have reservation for that interval - /api/accommodations/{idAccommodation}")
    public void updateAccommodationREST(Accommodation accommodation,String expectedMessage) {
        HttpEntity<Accommodation> requestEntity = new HttpEntity<Accommodation>(accommodation);
        ResponseEntity<Map<String, String>> responseEntity = restTemplate.exchange(
                "/api/accommodations/{id}",
                HttpMethod.PUT,
                requestEntity,
                new ParameterizedTypeReference<Map<String,String>>() {
                },accommodation.getId());

        Map<String,String> message = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedMessage,message.get("message"));
    }
}
