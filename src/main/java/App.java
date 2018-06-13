import com.google.gson.Gson;
import dao.Sql2oFoodtypeDao;
import dao.Sql2oRestaurantDao;
import dao.Sql2oReviewDao;
import exceptions.ApiException;
import models.Foodtype;
import models.Restaurant;
import models.Review;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;
//import exceptions.ApiException;

public class App {
    public static void main(String[] args) {
        Sql2oFoodtypeDao foodtypeDao;
        Sql2oRestaurantDao restaurantDao;
        Sql2oReviewDao reviewDao;
        Connection conn;
        Gson gson = new Gson();

        String connectionString = "jdbc:h2:~/jadle.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");

        restaurantDao = new Sql2oRestaurantDao(sql2o);
        foodtypeDao = new Sql2oFoodtypeDao(sql2o);
        reviewDao = new Sql2oReviewDao(sql2o);
        conn = sql2o.open();


        //post a restaurant
        post("/restaurants/new", "application/json", (req, res) -> {
            Restaurant restaurant = gson.fromJson(req.body(), Restaurant.class);
            restaurantDao.add(restaurant);
            res.status(201);
            return gson.toJson(restaurant);
        });

        //get all restaurants
        get("/restaurants", "application/json", (request, response) -> {
            return gson.toJson(restaurantDao.getAll());
        });

        //get individual restaurant
        get("/restaurants/:id", "application/json", (request, response) -> {
            int restaurantId = Integer.parseInt(request.params("id"));
            return gson.toJson(restaurantDao.findById(restaurantId));
        });

        //get all foodtypes for a restaurant
        get("/restaurants/:id/foodtype", "application/json", (request, response) -> {
            int restaurantId = Integer.parseInt(request.params("id"));
            return gson.toJson(foodtypeDao.getAllRestaurantsByFoodType(restaurantId));
        });

        //get all restaurants for a foodtype
        get("foodtype/:id/restaurants", "application/json", (request, response) -> {
            int foodypeId = Integer.parseInt(request.params("id"));
            return gson.toJson(restaurantDao.getAllFoodtypesByRestaurant(foodypeId));
        });

        //post foodtype to a restaurant
        post("restaurant/:restaurantId/foodtype/:foodtypeId", "application/json", (request, response) -> {

            int restaurantId = Integer.parseInt(request.params("restaurantId"));
            int foodtypeId = Integer.parseInt(request.params("foodtypeId"));
            Restaurant restaurant = restaurantDao.findById(restaurantId);
            Foodtype foodtype = foodtypeDao.findById(foodtypeId);

            if (restaurant != null && foodtype != null) {
                foodtypeDao.addFoodtypeToRestaurant(foodtype, restaurant);
                response.status(201);
                return gson.toJson(String.format("Restaurant '%s' and Foodtype '%s' have been associated", foodtype.getName(), restaurant.getName()));
            } else {
//                throw new ApiException(404, String.format("Restaurant or foodtype does not exist"));
                return null;
            }
        });

        //post new foodtype
        post("/foodtypes/add", "application/json", (request, response) -> {
            Foodtype foodtype = gson.fromJson(request.body(), Foodtype.class);
            foodtypeDao.add(foodtype);
            response.status(201);
            return gson.toJson(foodtype);
        });

        //get all reviews for a restaurant
        get("/restaurant/:restaurantId/reviews", "application/json", (request, response) -> {
            int restaurantId = Integer.parseInt(request.params("restaurantId"));

            if (restaurantDao.findById(restaurantId) == null) {
                response.status(404);
                return gson.toJson(String.format("This restaurant does not exist."));
            } else {
                List<Review> reviews = reviewDao.getAllReviewsForARestaurant(restaurantId);
                if (reviews.size() == 0) {
                    response.status(404);
                    return gson.toJson(String.format("Sorry, there are no reviews for this restaurant."));
                } else {
                    response.status(200);
                    return gson.toJson(reviews);
                }
            }

        });

        //post review to restaurant
        post("/restaurant/:restaurantId/reviews/add", "application/json", (request, response) -> {
            int restaurantId = Integer.parseInt(request.params("restaurantId"));

            if (restaurantDao.findById(restaurantId) == null) {
                response.status(404);
                return gson.toJson(String.format("This restaurant does not exist. Please submit review to valid restaurant."));
            } else {
                response.status(201);
                Review review = gson.fromJson(request.body(), Review.class);
                reviewDao.add(review);
                return gson.toJson(review);
            }
        });


        //exception handling
        get("/restaurants/:id", "application/json", (request, response) -> {
           int restaurantId = Integer.parseInt(request.params("id"));

           Restaurant restaurantToFind = restaurantDao.findById(restaurantId);

           if (restaurantToFind == null) {
               throw new ApiException(404, String.format("No restaurant with the id: \"%s\" exists", request.params("id")));
           }

           return gson.toJson(restaurantToFind);
        });

        exception(ApiException.class, (exc, request, response) -> {
            ApiException err = (ApiException) exc;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("Status", err.getStatusCode());
            jsonMap.put("errorMessage", err.getMessage());
            response.type("application/json");
            response.status(err.getStatusCode());
            response.body(gson.toJson(jsonMap));
        });

        after((request, response) -> {
            response.type("application/json");
        });
    }
}
