package com.booking.ProjectISS.service.accommodation.price;

import com.booking.ProjectISS.model.accomodations.Price;
import com.booking.ProjectISS.repository.accomodations.price.IPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PriceService implements IPriceService{
    @Autowired
    private IPriceRepository priceRepository;
    @Override
    public Price update(Price price) {
        return this.priceRepository.save(price);
    }
}
