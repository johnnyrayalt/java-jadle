package dao;

import models.Restaurant;
import models.Review;

import java.util.List;

public interface ReviewDao {
    //CREATE
    void add(Review review);
//  void addReviewToRestaurant(Review review, Restaurant restaurant);
    //READ
    List<Review> getAll();
//    List<Review> getAllReviewsForARestaurant(int restaurantId);

    //UPDATE

    //DELETE
    void deleteById(int id);
    void clearAllReviews();
}
