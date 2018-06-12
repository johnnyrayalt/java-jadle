import com.google.gson.Gson;
import dao.Sql2oFoodtypeDao;
import dao.Sql2oRestaurantDao;
import models.Restaurant;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        Sql2oFoodtypeDao foodtypeDao;
        Sql2oRestaurantDao restaurantDao;
//        Sql2oReviewDao reviewDao;
        Connection conn;
        Gson gson = new Gson();

        String connectionString = "jdbc:h2:~/jadle.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");

        restaurantDao = new Sql2oRestaurantDao(sql2o);
        foodtypeDao = new Sql2oFoodtypeDao(sql2o);
//        reviewDao = new Sql2oReviewDao(sql2o);
        conn = sql2o.open();


        post("/restaurants/new", "application/json", (req, res) -> {
            Restaurant restaurant = gson.fromJson(req.body(), Restaurant.class);
            restaurantDao.add(restaurant);
            res.status(201);
            return gson.toJson(restaurant);
        });

        get("/restaurants", "application/json", (request, response) -> {
            return gson.toJson(restaurantDao.getAll());
        });

        get("/restaurants/:id", "application/json", (request, response) -> {
            int restaurantId = Integer.parseInt(request.params("id"));
            return gson.toJson(restaurantDao.findById(restaurantId));
        });

        after((request, response) -> {
            response.type("application/json");
        });
    }
}
