package dao;

import models.Review;
import models.Restaurant;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oReviewDaoTest {
    private Sql2oRestaurantDao restaurantDao;
    private Sql2oReviewDao reviewDao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        restaurantDao = new Sql2oRestaurantDao(sql2o);
        reviewDao = new Sql2oReviewDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingReviewSetsId() {
        Review review = setUpNewReview();
        assertEquals(1, review.getId());
    }

    @Test
    public void getAll() throws Exception {
        Review review = setUpNewReview();
        Review review1 = setUpNewReview();
        assertEquals(2, reviewDao.getAll().size());
    }

    @Test
    public void getAllReviewsForARestaurant() throws Exception {
        Restaurant restaurant = setUpNewRestaurant();
        Restaurant otherRestaurant = setUpNewRestaurant();
        Review review = setUpReviewForARestaurant(restaurant);
        Review review1 = setUpReviewForARestaurant(restaurant);
        Review review2 = setUpReviewForARestaurant(otherRestaurant);
        assertEquals(2, reviewDao.getAllReviewsForARestaurant(restaurant.getId()).size());
    }

    public Review setUpNewReview() {
        Review review = new Review("Thats a lot of nuts", "Betty", 10, 555);
        reviewDao.add(review);
        return review;
    }

    public Review setUpReviewForARestaurant(Restaurant restaurant) {
        Review review = new Review("great", "Kim", 4, restaurant.getId());
        reviewDao.add(review);
        return review;
    }

    public Restaurant setUpNewRestaurant() {
        Restaurant restaurant = new Restaurant("Fish Witch", "214 NE Broadway", "97232", "503-402-9874", "http://fishwitch.com", "hellofishy@fishwitch.com");
        restaurantDao.add(restaurant);
        return restaurant;
    }
}