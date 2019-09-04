package rest;

import data.Animal;
import db.DbConnection;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

@Path("/as")
public class AnimalService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/animal/{id}")
    public Animal getAnimal(@PathParam("id") int id) {
        //creating connection to cloud/local DB
        Connection conn = DbConnection.getConnection();

        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                //searching for specific animal by id
                ResultSet RS = stmt.executeQuery("select * from animal where id=" + id);

                Animal animal = null;
                //check if animal was found in db
                if (RS.isBeforeFirst()) {
                    //setting pointer to row with animal
                    RS.next();
                    //creating animal object
                    animal = new Animal(
                            RS.getInt("id"),
                            RS.getString("breed"),
                            RS.getInt("weight"),
                            RS.getInt("age"),
                            RS.getString("name")
                    );
                }

                conn.close();

                //returning animal object or null if not found
                return animal;
            } else {
                System.out.println("No connection!!");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //return null if anything go wrong
        return null;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/animal/all")
    public ArrayList<Animal> getAnimal() {
        //creating list of Animal objects
        ArrayList<Animal> arrayList = new ArrayList<>();

        Connection conn = DbConnection.getConnection();

        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                //querying for all animals from db
                ResultSet RS = stmt.executeQuery("select * from animal");


                while (RS.next()) {
                    Animal animal = new Animal(
                            RS.getInt("id"),
                            RS.getString("breed"),
                            RS.getInt("weight"),
                            RS.getInt("age"),
                            RS.getString("name")
                    );
                    //adding animals to list
                    arrayList.add(animal);
                }

                conn.close();

                return arrayList;
            } else {
                System.out.println("No connection!!");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/animal/add")
    public Animal saveAnimal(Animal animal) {

        Connection conn = DbConnection.getConnection();

        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();

                //inserting new animal to DB
                stmt.executeUpdate(String.format(
                        "insert into animal(breed, weight, age, name) values ('%s', '%s', '%s', '%s')",
                        animal.getBreed(),
                        animal.getWeight(),
                        animal.getAge(),
                        animal.getName()),
                        Statement.RETURN_GENERATED_KEYS
                );
                //getting last inserted id
                ResultSet rs= stmt.getGeneratedKeys();
                if (rs.next())
                {
                    //setting id to Animal object + retyping long to int
                    animal.setId((int) rs.getLong(1));
                }

                conn.close();

                return animal;
            } else {
                System.out.println("No connection!!");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }
}
