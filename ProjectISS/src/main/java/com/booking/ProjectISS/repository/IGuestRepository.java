package com.booking.ProjectISS.repository;

import com.booking.ProjectISS.model.Guest;

import java.util.Collection;

public interface IGuestRepository{
    Guest findOne(Long id);

    Collection<Guest> findAll();

    void delete(Long id);
}
