package com.booking.ProjectISS.repository.reviews;

import com.booking.ProjectISS.model.reviews.Review;

import java.util.Collection;

public interface IReviewRepository {
    Review findOne(long id);
    Collection<Review> findAll();
    void delete(long id);
    Review create(Review review);
}
