package dao;

import models.Foodtype;
import models.Restaurant;
import java.util.List;

public interface RestaurantDao {

    //CREATE
    void add(Restaurant restaurant);
    void addRestaurantToFoodtype(Restaurant restaurant, Foodtype foodtype);

    //READ
    Restaurant findById(int id);
    List<Restaurant> getAll();
    List<Foodtype> getAllFoodtypesByRestaurant(int restaurantId);

    //UPDATE
    void update(int id, String name, String address, String zipcode, String phone, String website, String email);

    //DELETE
    void deleteById(int id);
    void clearAllRestaurants();
}
