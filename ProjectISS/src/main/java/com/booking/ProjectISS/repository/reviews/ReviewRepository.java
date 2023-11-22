package com.booking.ProjectISS.repository.reviews;

import com.booking.ProjectISS.enums.ReservationStatus;
import com.booking.ProjectISS.enums.ReviewStatus;
import com.booking.ProjectISS.enums.TypeAccommodation;
import com.booking.ProjectISS.model.accomodations.Accommodation;
import com.booking.ProjectISS.model.reservations.Reservation;
import com.booking.ProjectISS.model.reviews.Review;
import com.booking.ProjectISS.model.users.Guest;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ReviewRepository implements IReviewRepository {
        private List<Review> reviews;
        private static AtomicLong counter = new AtomicLong();

        public ReviewRepository() {
            this.reviews = loadAll();
        }

        @Override
        public Review findOne(long id) {
            for (Review review : reviews) {
                if (Objects.equals(review.getId(), id)) {
                    return review;
                }
            }
            return null;
        }

        @Override
        public Collection<Review> findAll() {
            return reviews;
        }

        @Override
        public void delete(long id) {
            reviews.removeIf(review -> review.getId() == id);
        }

        @Override
        public Review create(Review review) {
            long id = review.getId();

        if (id == 0) {
            id = counter.incrementAndGet();
            review.setId(id);
        }

            this.reviews.add(review);
            return review;
        }

        private List<Review> loadAll() {
            this.reviews = new ArrayList<>();

            Accommodation a1 = new Accommodation(1L,false,false,"dadasda",3,3,"dasasd",
                    TypeAccommodation.Apartment,2,2,null,null,null,null,null,null);

            Guest g = new Guest();
            g.setId(1L);
            Reservation r1 = new Reservation(200, ReservationStatus.ACCEPTED, new Date(), 5);
            r1.setAccommodation(a1);
            r1.setId(1);
            r1.setGuest(g);

            Review review1 = new Review(1, "Great experience!", ReviewStatus.ACTIVE);
            review1.setId(counter.incrementAndGet());
            review1.setReservation(r1);
            Review review2 = new Review(2, "Could be better.",ReviewStatus.DELETED);
            review2.setReservation(r1);
            review2.setId(counter.incrementAndGet());
            Review review3 = new Review(3, "Amazing service!", ReviewStatus.REPORTED);
            review3.setReservation(r1);
            review3.setId(counter.incrementAndGet());

            reviews.add(review1);
            reviews.add(review2);
            reviews.add(review3);

            return reviews;
        }

    }