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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AccommodationControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void updateAccommodationREST() {
        Accommodation accommodation=new Accommodation(1L);
        HttpEntity<Accommodation> requestEntity = new HttpEntity<Accommodation>(accommodation);
        ResponseEntity<Map<String, String>> responseEntity = restTemplate.exchange("/api/accommodation/{id}",
                HttpMethod.PUT,
                requestEntity,
                new ParameterizedTypeReference<Map<String,String>>() {
                },accommodation.getId());

        Map<String,String> message = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
