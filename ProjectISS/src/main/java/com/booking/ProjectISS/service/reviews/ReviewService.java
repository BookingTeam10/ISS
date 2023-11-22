package com.booking.ProjectISS.service.reviews;

import com.booking.ProjectISS.dto.reviews.ReviewDTO;
import com.booking.ProjectISS.model.reviews.Review;
import com.booking.ProjectISS.repository.reviews.IReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class ReviewService implements IReviewService {

    @Autowired
    private IReviewRepository reviewRepository;

    @Override
    public ReviewDTO findOneDTO(Long id) {
        return new ReviewDTO(reviewRepository.findOne(id));
    }

    @Override
    public Review findOne(Long id) {
        return reviewRepository.findOne(id);
    }

    @Override
    public Collection<ReviewDTO> findAllDTO() {
        Collection<Review> reviews = reviewRepository.findAll();
        Collection<ReviewDTO> reviewDTOs = new ArrayList<>();

        for (Review r : reviews) {
            reviewDTOs.add(new ReviewDTO(r));
        }

        return reviewDTOs;
    }

    @Override
    public Collection<Review> findAll() {
        return reviewRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        reviewRepository.delete(id);
    }

    @Override
    public ReviewDTO create(Review review) throws Exception {
        Review savedReview = reviewRepository.create(review);
        return new ReviewDTO(savedReview);
    }

    @Override
    public ReviewDTO update(Review review) throws Exception {
        // Implement your update logic if needed
        return null;
    }
}
