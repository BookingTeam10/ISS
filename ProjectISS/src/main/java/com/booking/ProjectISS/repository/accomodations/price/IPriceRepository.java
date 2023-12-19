package com.booking.ProjectISS.repository.accomodations.price;

import com.booking.ProjectISS.model.accomodations.Price;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPriceRepository extends JpaRepository<Price, Long> {
}
