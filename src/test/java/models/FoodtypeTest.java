package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

public class FoodtypeTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getAndSetNameReturnsNameCorrectly() {
        Foodtype testFoodtype = setUpNewFoodType();
        testFoodtype.setName("New");
        assertEquals("New", testFoodtype.getName());
    }

    @Test
    public void getAndSetIdReturnsIdCorrectly() {
        Foodtype testFoodtype = setUpNewFoodType();
        testFoodtype.setId(1);
        assertEquals(1, testFoodtype.getId());
    }

    // Helpers
    public Foodtype setUpNewFoodType() {
        return new Foodtype("Mashed Potatoes");
    }
}