package dao;

import models.Foodtype;
import models.Restaurant;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.lang.reflect.Array;
import java.util.Arrays;

import static org.junit.Assert.*;

public class Sql2oRestaurantDaoTest {
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
    public void RestaurantReturnsFoodtypesCorrectly() throws Exception {
        Foodtype testFoodtype  = new Foodtype("Seafood");
        foodtypeDao.add(testFoodtype);

        Foodtype otherFoodtype  = new Foodtype("Bar Food");
        foodtypeDao.add(otherFoodtype);

        Restaurant testRestaurant = setUpNewRestaurant();
        restaurantDao.add(testRestaurant);
        System.out.println(restaurantDao.findById(testRestaurant.getId()));

        restaurantDao.addRestaurantToFoodtype(testRestaurant, testFoodtype);
        restaurantDao.addRestaurantToFoodtype(testRestaurant, otherFoodtype);

        Foodtype[] foodtypes = {testFoodtype, otherFoodtype};

        assertEquals(Arrays.asList(foodtypes), restaurantDao.getAllFoodtypesByRestaurant(testRestaurant.getId()));
    }

    public Restaurant setUpNewRestaurant() {
        Restaurant restaurant = new Restaurant("name", "123 Main St", "90001", "238-378-7134");
        return restaurant;
    }
}