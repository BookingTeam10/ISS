package com.booking.ProjectISS.service;

import com.booking.ProjectISS.model.Guest;

import java.util.Collection;

public interface IGuestService {

    Guest findOne(Long id);

    Collection<Guest> findAll();

    void delete(Long id);
}
