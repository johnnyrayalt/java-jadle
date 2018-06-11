package models;

import models.Restaurant;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.geom.RectangularShape;

import static org.junit.Assert.*;

public class RestaurantTest {

    @Before
    public void setUp() throws Exception {
        
    }

    @After
    public void tearDown() throws Exception {
        
    }

    @Test
    public void getAndSetNameReturnsCorrectName() throws Exception {
        Restaurant testRestaurant = setupRestaurant();
        testRestaurant.setName("New");
        assertEquals("New", testRestaurant.getName());
    }

    @Test
    public void getAndSetAddressReturnsCorrectAddress() throws Exception {
        Restaurant testRestaurant = setupRestaurant();
        testRestaurant.setAddress("1234 Main St");
        assertEquals("1234 Main St", testRestaurant.getAddress());
    }

    @Test
    public void getAndSetZipcodeReturnsCorrectZipcode() throws Exception {
        Restaurant testRestaurant = setupRestaurant();
        testRestaurant.setZipcode("90001");
        assertEquals("90001", testRestaurant.getZipcode());
    }

    @Test
    public void getAndSetPhoneReturnsCorrectPhone() throws Exception {
        Restaurant testRestaurant = setupRestaurant();
        testRestaurant.setPhone("661-317-1913");
        assertEquals("661-317-1913", testRestaurant.getPhone());

    }

    @Test
    public void getAndSetWebsiteReturnsCorrectWebsite() throws Exception {
        Restaurant testRestaurant = setupRestaurant();
        testRestaurant.setWebsite("www.test.com");
        assertEquals("www.test.com", testRestaurant.getWebsite());
    }

    @Test
    public void getAndSetEmailReturnsCorrectEmail() throws Exception {
        Restaurant testRestaurant = setupRestaurant();
        testRestaurant.setEmail("test@test.com");
        assertEquals("test@test.com", testRestaurant.getEmail());
    }

    @Test
    public void getAndSetIdReturnsCorrectId() throws Exception {
        Restaurant testRestaurant = setupRestaurant();
        testRestaurant.setId(1);
        assertEquals(1, testRestaurant.getId());
    }

    // helper(s)
    public Restaurant setupRestaurant() {
        return new Restaurant("Fish Witch", "214 NE Broadway", "97232", "503-402-9874", "http://fishwitch.com", "hellofishy@fishwitch.com");
    }
}