package dao;

import models.Foodtype;
import models.Restaurant;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.*;

import static org.junit.Assert.*;

public class Sql2oFoodtypeDaoTest {
    private Sql2oRestaurantDao restaurantDao;
    private Sql2oFoodtypeDao foodtypeDao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        restaurantDao = new Sql2oRestaurantDao(sql2o);
        foodtypeDao = new Sql2oFoodtypeDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }


    @Test
    public void addingFoodSetsId() throws Exception {
        Foodtype testFoodtype = setUpFoodType();
        int originalFoodtypeId = testFoodtype.getId();
        foodtypeDao.add(testFoodtype);
        assertNotEquals(originalFoodtypeId,testFoodtype.getId());
    }

    @Test
    public void addFoodTypeToRestaurantAddsFoodTypeCorrectly() throws Exception {

        Restaurant restaurant = setUpNewRestaurant();
        Restaurant restaurant1 = setUpAltRestaurant();

        restaurantDao.add(restaurant);
        restaurantDao.add(restaurant1);

        Foodtype testFoodType = setUpFoodType();

        foodtypeDao.add(testFoodType);

        foodtypeDao.addFoodtypeToRestaurant(testFoodType, restaurant);
        foodtypeDao.addFoodtypeToRestaurant(testFoodType, restaurant1);

        assertEquals(2, foodtypeDao.getAllFoodtypesByRestaurant(testFoodType.getId()).size());
    }

    public Restaurant setUpNewRestaurant() {
        Restaurant restaurant = new Restaurant("name", "123 Main St", "90001", "238-378-7134");
        return restaurant;
    }

    public Restaurant setUpAltRestaurant() {
        Restaurant restaurant = new Restaurant("name2", "12334 Main St", "90002", "238-378-7135");
        return restaurant;
    }

    public Foodtype setUpFoodType() {
        Foodtype foodtype = new Foodtype("Jamaican");
        return foodtype;
    }
}