package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReviewTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getAndSetContentCorrectlyReturnsContent() throws Exception{
        Review testReview = setUpNewReview();
        testReview.setContent("new content");
        assertEquals("new content", testReview.getContent());
    }

    @Test
    public void getAndSetWrittenByCorrectlyReturnsWrittenBy() throws Exception{
        Review testReview = setUpNewReview();
        testReview.setWrittenBy("new writer");
        assertEquals("new writer", testReview.getWrittenBy());
    }

    @Test
    public void getAndSetRatingCorrectlyReturnsRating() throws Exception{
        Review testReview = setUpNewReview();
        testReview.setRating(9);
        assertEquals(9, testReview.getRating());
    }

    @Test
    public void getAndSetIdCorrectlyReturnsId() throws Exception{
        Review testReview = setUpNewReview();
        testReview.setId(2);
        assertEquals(2, testReview.getId());
    }

    @Test
    public void getAndSetRestaurantIdCorrectlyReturnsRestaurantId() throws Exception{
        Review testReview = setUpNewReview();
        testReview.setRestaurantId(9);
        assertEquals(9, testReview.getRestaurantId());
    }

    // Helpers
    public Review setUpNewReview() {
        return new Review("Test Content","Test Name", 10,1);
    }
}