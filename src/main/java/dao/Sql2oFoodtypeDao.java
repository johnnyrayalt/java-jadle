package dao;

import models.Foodtype;
import models.Restaurant;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oFoodtypeDao implements FoodtypeDao{

    private final Sql2o sql2o;

    public Sql2oFoodtypeDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Foodtype foodtype) {
        String sql = "INSERT INTO foodtypes (name) VALUES (:name)";
        try(Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql, true)
                    .bind(foodtype)
                    .executeUpdate()
                    .getKey();
            foodtype.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void addFoodtypeToRestaurant(Foodtype foodtype, Restaurant restaurant) {
        String sql = "INSERT INTO restaurants_foodtypes (restaurantid, foodtypeid) VALUES (:restaurantid, :foodtypeid)";
        try(Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("foodtypeid", foodtype.getId())
                    .addParameter("restaurantid", restaurant.getId())
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Foodtype> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM foodtypes")
                    .executeAndFetch(Foodtype.class);
        }
    }

    @Override
    public List<Restaurant> getAllFoodtypesByRestaurant(int foodTypeId) {
        ArrayList<Restaurant> restaurants = new ArrayList<>(); //create a new array list that holds all Restaurant objects

        String joinQuery = "SELECT restaurantid FROM restaurants_foodtypes WHERE foodtypeid = :foodtypeid"; //looks for ID associated with a restaurant based on a query for foodtypes

        try(Connection con = sql2o.open()){
            List<Integer> allRestaurantIds = con.createQuery(joinQuery) //create a new list to hold all of the restaurant IDs and set it to the sql query results
                    .addParameter("foodtypeid", foodTypeId)
                    .executeAndFetch(Integer.class); //returns all restaurant IDs as integers
            for (Integer restaurantId : allRestaurantIds){ //takes previously acquired list of restaurant ids and runs it through a for loop
                String restuarantQuery = "SELECT * FROM restaurants where id = :restaurantid"; //looks for all restaurants in restaurants table whose ids match corresponding resturantids in join table
                restaurants.add(
                        con.createQuery(restuarantQuery)
                        .addParameter("restaurantid", restaurantId)
                        .executeAndFetchFirst(Restaurant.class)
                );
            }
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
        return restaurants;
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void clearAll() {

    }
}
