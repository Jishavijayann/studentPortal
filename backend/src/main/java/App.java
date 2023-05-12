package main.java;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;


import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.User;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.PoolOptions;

import io.vertx.core.http.HttpMethod;
import java.io.PrintWriter;
import java.sql.*;
import io.vertx.ext.web.handler.CorsHandler;

/**
 * Hello world!
 *
 */

public class App {

    public static void main(String[] args) {


        Vertx vertx = Vertx.vertx();

        HttpServer httpServer = vertx.createHttpServer();

        Router router = Router.router(vertx);
        router.route().handler(CorsHandler.create("http://localhost:4200")
                .allowedMethod(HttpMethod.GET)
                .allowedMethod(HttpMethod.POST)
                .allowedMethod(HttpMethod.PUT)
                .allowedMethod(HttpMethod.DELETE)
                .allowedHeader("*"));
        Route handler1 = router.get("/hello")
                .handler(routingContext -> {
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        String url = "jdbc:mysql://localhost:3306/jisha_db";
                        Connection connection = DriverManager.getConnection(url,"root" ,"");
                        Statement statement = connection.createStatement();
                        ResultSet rs = statement.executeQuery("SELECT * FROM user_details");
                        JsonArray jsonArray = new JsonArray();
                        while (rs.next()) {
                            JsonObject jsonObject = new JsonObject();
                            jsonObject.put("first_name", rs.getString("first_name"));
                            jsonObject.put("last_name", rs.getString("last_name"));
                            jsonObject.put("phone", rs.getString("phone"));
                            jsonObject.put("email", rs.getString("email"));
                            jsonArray.add(jsonObject);
                        }
                        routingContext.response()
                                .putHeader("content-type", "application/json")
                                .putHeader("Access-Control-Allow-Origin", "http://localhost:4200/")
                                .putHeader("Access-Control-Allow-Methods","GET, POST, OPTIONS")
                                .putHeader("Access-Control-Allow-Credentials", "true")

                                .end(jsonArray.encodePrettily());
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        routingContext.response().setStatusCode(500).end("Internal Server Error");
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
        Route handler6 = router.get("/hello/:id")
                .handler(routingContext -> {
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        String url = "jdbc:mysql://localhost:3306/jisha_db";
                        Connection connection = DriverManager.getConnection(url,"root" ,"");
                        Statement statement = connection.createStatement();
                        int id = Integer.parseInt(routingContext.request().getParam("id"));
                        ResultSet rs = statement.executeQuery("SELECT * FROM user_details WHERE id=" + id);
                        JsonArray jsonArray = new JsonArray();
                        while (rs.next()) {
                            JsonObject jsonObject = new JsonObject();
                            jsonObject.put("id", rs.getString("id"));
                            jsonObject.put("first_name", rs.getString("first_name"));
                            jsonObject.put("last_name", rs.getString("last_name"));
                            jsonObject.put("phone", rs.getString("phone"));
                            jsonObject.put("email", rs.getString("email"));
                            jsonArray.add(jsonObject);
                        }
                        routingContext.response()
                                .putHeader("content-type", "application/json")
                                .putHeader("Access-Control-Allow-Origin", "http://localhost:4200/")
                                .putHeader("Access-Control-Allow-Methods","GET, POST, OPTIONS")
                                .putHeader("Access-Control-Allow-Credentials", "true")

                                .end(jsonArray.encodePrettily());
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        routingContext.response().setStatusCode(500).end("Internal Server Error");
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
        Route  handler2 = router
                .post("/register")
//                .consumes("*/json")
                .handler(BodyHandler.create())
                .handler(routingContext -> {
                    JsonObject cust = routingContext.getBodyAsJson();
                    System.out.println("customer"+ cust.toString());

                    String name=cust.getString("first_name");
                    System.out.println("customer"+ name);
                    String lname=cust.getString("last_name");
                    System.out.println("customer"+ lname);
                    JsonObject res= new JsonObject();
                    res.put("message", "created sucessfully");
                    System.out.println("customer"+cust.getString("password"));

//                    HttpServerResponse response = routingContext.response();

//                    response.setChunked(true);


                    try{

//                        String firstname1 = String.valueOf(routingContext.request().body());
                        String First_name = cust.getString("first_name");
                        String last_name = cust.getString("last_name");
                        String phone = cust.getString("phone");
                        String email = cust.getString("email");
                        String password = cust.getString("password");
                        System.out.println(First_name);
                        System.out.println(last_name);

                        Class.forName("com.mysql.jdbc.Driver");
                        String url = "jdbc:mysql://localhost:3306/jisha_db";
                        Connection connection = DriverManager.getConnection(url,"root" ,"");
                        Statement statement = connection.createStatement();
                        PreparedStatement p=connection.prepareStatement("insert into user_details (first_name,last_name,phone,email,password)value(?,?,?,?,?)");
//                        JsonObject myREsponse =new JsonObject(response.toString());
                        p.setString(1,First_name);
                        p.setString(2,last_name);
                        p.setString(3,phone);
                        p.setString(4,email);
                        p.setString(5,password);
                        p.executeUpdate();

//                     response.write("firstname"+firstname+"\n");
//
//                        response.write("secondname"+secondname+"\n");

                    }




                    catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                    catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    catch (Exception e) {
                        throw new RuntimeException(e);
                    }





                    routingContext.response().end(res.encodePrettily());
                });

        Route handler4 = router
                .post("/login")
                .handler(BodyHandler.create())
                .handler(routingContext -> {
                    JsonObject loginData = routingContext.getBodyAsJson();
                    String firstName = loginData.getString("first_name");
                    String password = loginData.getString("password");

                    JsonObject response = new JsonObject();

                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        String url = "jdbc:mysql://localhost:3306/jisha_db";
                        Connection connection = DriverManager.getConnection(url, "root", "");
                        Statement statement = connection.createStatement();
                        ResultSet resultSet = statement.executeQuery("select * from user_details where first_name='" + firstName + "' and password='" + password + "'");

                        if (resultSet.next()) {
                            response.put("message", "login success");
                        } else {
                            response.put("message", "login failed");
                        }

                        resultSet.close();
                        statement.close();
                        connection.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        response.put("message", "login failed");
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        response.put("message", "login failed");
                    }

                    routingContext.response().end(response.encodePrettily());
                });



        Route handler5 = router
                .post("/update")
                .handler(BodyHandler.create())
                .handler(routingContext -> {
                    JsonObject updateData = routingContext.getBodyAsJson();
                    String firstName = updateData.getString("first_name");
                    String lastName = updateData.getString("last_name");
                    String password = updateData.getString("password");
                    String newEmail = updateData.getString("email");
                    String newPhoneNumber = updateData.getString("phone");

                    JsonObject response = new JsonObject();

                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        String url = "jdbc:mysql://localhost:3306/jisha_db";
                        Connection connection = DriverManager.getConnection(url, "root", "");
                        Statement statement = connection.createStatement();
                        int rowsUpdated = statement.executeUpdate("update user_details set email='" + newEmail + "',last_name='" +lastName+"', phone='" + newPhoneNumber + "' where first_name='" + firstName + "' and password='" + password + "'");

                        if (rowsUpdated > 0) {
                            response.put("message", "update success");
                        } else {
                            response.put("message", "update failed");
                        }

                        statement.close();
                        connection.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        response.put("message", "update failed");
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        response.put("message", "update failed");
                    }

                    routingContext.response().end(response.encodePrettily());
                });



        vertx.createHttpServer().requestHandler(router::accept).listen(3307);

    }
}